package org.sisy;

import java.security.MessageDigest;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Arrays;

/**
 * Responsible for using cryptographic
 * in order to encrypt and decrypt data
 * blocks and file streams.
 *
 * @author Valdemar Lidnberg
 */
public class Cryptographic {

    enum CIPHER {
        AES_128,		/*	Advance encryption standard.	*/
        AES_192,		/*	Advance encryption standard.	*/
        AES_256,		/*	Advance encryption standard.	*/
        DES,			/*	Data Encryption Standard    */
        DES3,			/*	Data Encryption Standard Triple.	*/
        BLOWFISH,		/*	BlowFish */
    }

    /**
     * Encrypt file.
     *
     * @param path    file path as relative or absolute path.
     * @param newfile file path to store encrypted file.
     * @param ciph    cipher used for encryption.
     * @param key     key used for creating the cipher object.
     * @return number of bytes encrypted file.
     * @throws Exception if any of the method throws an exception.
     */
    public static int encryptFile(String path, String newfile, CIPHER ciph, byte[] key) throws Exception {

        File f;
        FileInputStream instream;
        int nbytes;

		/*	*/
        f = new File(path);
        instream = new FileInputStream(f);

		/*	Encrypt stream. */
        nbytes = encryptStream(instream, newfile, ciph, key);
        instream.close();

        return nbytes;
    }

    /**
     * Encrypt stream.
     *
     * @param instream
     * @param newfile
     * @param ecipher
     * @param key
     * @return number of bytes written to file.
     * @throws Exception if any of the method throws an exception.
     */
    public static int encryptStream(InputStream instream, String newfile, CIPHER ecipher, byte[] key) throws Exception {

        File newf;
        FileOutputStream outstream;
        Cipher cipher;
        CipherOutputStream cipoutstream;
        CryptoMetaData meta;
        byte[] buffer;
        int len;
        int total = 0;
        int blocksize;
        int noffset = 0;

		/*	Create and open output file.	*/
        newf = new File(newfile);
        outstream = new FileOutputStream(newf);

		/*	Create cipher and cipher stream.	*/
        cipher = createEncryptionCipher(ecipher, key);
        cipoutstream = new CipherOutputStream(outstream, cipher);

		/*	Allocate space for meta data.	*/
        meta = new CryptoMetaData(0);
        cipoutstream.write(meta.toByteArray());

        /*	Get block size. */
        blocksize = getBlockSize(ecipher);
		
		/*	Write file.	*/
        buffer = new byte[blocksize];
        while (instream.available() > blocksize) {
            len = instream.read(buffer);
            cipoutstream.write(buffer);
            total += len;
        }
		
		/*	End part.	*/
        Arrays.fill(buffer, (byte) 0);
        len = instream.read(buffer);
        cipoutstream.write(buffer);
        total += blocksize;

        /*	Update meta data.	*/
        noffset = blocksize - len;
        meta = new CryptoMetaData(noffset);
        cipoutstream.write(meta.toByteArray(), 0, 12);

        /*  Close streams.  */
        cipoutstream.close();
        outstream.close();

        return total;
    }

    /**
     * Decrypt file.
     *
     * @param filename
     * @param newfile
     * @param ciph
     * @param key
     * @return number of bytes written to file.
     * @throws Exception if any of the method throws an exception.
     */
    public static int decryptFile(String filename, String newfile, CIPHER ciph, byte[] key) throws Exception {

        File f;
        FileInputStream instream;
        int nbytes;

		/*	Create and open file input stream.	*/
        f = new File(filename);
        instream = new FileInputStream(f);
		
		/*	*/
        nbytes = decryptStream(instream, newfile, ciph, key);
        instream.close();

        return nbytes;
    }

    /**
     * Decrypt stream.
     *
     * @param stream
     * @param newfile
     * @param ecipher
     * @param key
     * @return number of bytes written to file.
     * @throws Exception if any of the method throws an exception.
     */
    public static int decryptStream(InputStream stream, String newfile, CIPHER ecipher, byte[] key) throws Exception {

        File newf;
        FileOutputStream outstream;
        Cipher cipher;
        CipherInputStream cipinstream;
        CryptoMetaData meta;
        int blocksize;
        byte[] buffer;
        int len;
        int total = 0;


		
		/*	Create and open output file.	*/
        newf = new File(newfile);
        outstream = new FileOutputStream(newf);

		/*	Create cipher and cipher stream.	*/
        cipher = createDecryptCipher(ecipher, key);
        cipinstream = new CipherInputStream(stream, cipher);

		/*	Read meta data.	*/
        meta = CryptoMetaData.create(cipinstream);
        blocksize = getBlockSize(ecipher);
        if (meta.getNoffset() > blocksize)
            throw new Exception("metadata is invalid.");


		/*	Read file.	*/
        buffer = new byte[blocksize];
        while (cipinstream.available() > blocksize) {
            len = cipinstream.read(buffer);
            outstream.write(buffer);
            total += len;
        }
		
		/*	End part.	*/

        buffer = new byte[blocksize - meta.getNoffset()];
        cipinstream.read(buffer);
        outstream.write(buffer);
        total += blocksize - meta.getNoffset();

        /*  Release streams.    */
        cipinstream.close();
        outstream.close();

        return total;
    }

    /**
     * Create encryption cipher object.
     *
     * @param cipher
     * @param password
     * @return non-null.
     * @throws Exception if any of the method throws an exception.
     */
    public static Cipher createEncryptionCipher(CIPHER cipher, byte[] password) throws Exception {
        Cipher ciph;

        ciph = createCipherInstance(cipher);
        SecretKeySpec seckey = createPasswordKey(cipher, password);
        ciph.init(Cipher.ENCRYPT_MODE, seckey);

        return ciph;
    }

    /**
     * Create decryption cipher object.
     *
     * @param cipher
     * @param password
     * @return non-null object.
     * @throws Exception if any of the method throws an exception.
     */
    private static Cipher createDecryptCipher(CIPHER cipher, byte[] password) throws Exception {
        Cipher ciph;

        ciph = createCipherInstance(cipher);
        SecretKeySpec seckey = createPasswordKey(cipher, password);
        ciph.init(Cipher.DECRYPT_MODE, seckey);

        return ciph;
    }

    /**
     * Create
     *
     * @param cipher
     * @return non-null.
     * @throws Exception if any of the method throws an exception.
     */
    private static Cipher createCipherInstance(CIPHER cipher) throws Exception {
        return Cipher.getInstance(getAlgorithm(cipher));
    }

    /**
     * Create
     *
     * @param cipher
     * @return non-null terminated string.
     * @throws IllegalArgumentException
     */
    private static String getAlgorithm(CIPHER cipher) {
        switch (cipher) {
            case AES_128:
            case AES_192:
            case AES_256:
                return "AES";
            case DES:
                return "DES";
            case DES3:
                return "DES3";
            case BLOWFISH:
                return "BLOWFISH";
            default:
                throw new IllegalArgumentException(String.format("Non supported cipher %s", cipher.toString()));
        }
    }

    /**
     * Get block cipher size in bytes.
     *
     * @param cipher
     * @return non-zero.
     */
    private static int getBlockSize(CIPHER cipher) {
        switch (cipher) {
            case AES_128:
            case AES_192:
            case AES_256:
                return 16;
            case DES:
                return 8;
            case DES3:
                return 8;
            case BLOWFISH:
                return 8;
            default:
                throw new IllegalArgumentException(String.format("Non supported cipher %s", cipher.toString()));
        }
    }

    /**
     * @param cipher
     * @return
     * @throws IllegalArgumentException
     */
    private static int getCipherKeySpace(CIPHER cipher) {
        switch (cipher) {
            case AES_128:
                return 16;
            case AES_192:
                return 24;
            case AES_256:
                return 32;
            case DES:
                return 7;
            case DES3:
                return (7 * 3);
            case BLOWFISH:
                return 16;
            default:
                throw new IllegalArgumentException(String.format("Non supported cipher %s", cipher.toString()));
        }
    }

    /**
     * Create secret key object..
     *
     * @param cipher
     * @param password
     * @return non-null.
     */
    private static SecretKeySpec createPasswordKey(CIPHER cipher, byte[] password) throws Exception {
        return createKey(cipher, computeHash(password));
    }

    /**
     * Create secret key object..
     *
     * @param cipher
     * @param key
     * @return non-null.
     */
    private static SecretKeySpec createKey(CIPHER cipher, byte[] key) {

        byte[] y = new byte[getCipherKeySpace(cipher)];
        System.arraycopy(key, 0, y, 0, getCipherKeySpace(cipher));
        return new SecretKeySpec(y, getAlgorithm(cipher));
    }

    /**
     * Compute secure hashing.
     *
     * @param pbuf byte array used for computing the hash.
     * @return non-null byte array.
     * @throws Exception if any of the method throws an exception.
     */
    private static byte[] computeHash(byte[] pbuf) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(pbuf);

        return md.digest();
    }

    /**
     * Get multiple size.
     *
     * @param len
     * @param blocksize
     * @return final block size.
     */
    private static long computeBlockSize(long len, long blocksize) {
        if (len % blocksize == 0)
            return len;
        else
            return (long) (len + (blocksize - (len % blocksize)));
    }
}
