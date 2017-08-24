package org.sisy;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Contains data about encrypted file.
 *
 * @author Valdemar Lindberg
 */
public class CryptoMetaData {

    /**
     *
     */
    private static final int metasize = 3 * 4 + 5;
    private static final String signature = ".sisy";
    private int compression;
    private int noffset;

    /**
     * Create instance.
     *
     * @param noffset
     */
    public CryptoMetaData(int noffset) {
        this.noffset = noffset;
    }

    /**
     * Create crypto meta data from input stream.
     *
     * @param stream non-null input stream object.
     * @return non-null object.
     * @throws IllegalAccessException
     */
    public static CryptoMetaData create(InputStream stream) throws Exception {

        byte sign[];
        String metasignature;
        CryptoMetaData crypto;
        int size;
        int nbytes = 0;

        /*  Check error.    */
        if (stream == null)
            throw new IllegalArgumentException("invalid stream object");

		/*  Read signature. */
        sign = new byte[5];
        nbytes += stream.read(sign);
        metasignature = new String(sign, "UTF-8");

        /*	Check signature.    */
        if (metasignature.compareTo(signature) != 0)
            throw new IllegalAccessException(String.format("invalid signature: %s", metasignature));

		/*  Read size.   */
        size = stream.read();
        nbytes += 4;

        /*  Check meta size attribute.  */
        if(size > metasize || size < 1 )
            throw new Exception("Invalid meta size");

        /*  */
        crypto = new CryptoMetaData(0);

        crypto.noffset = stream.read();
        nbytes += 8;

        /*  Skip.   */
        stream.skip(size - nbytes);

        return crypto;
    }

    /**
     * Check if compression is used.
     *
     * @return true if compression is used, false otherwise.
     */
    public boolean getCompression() {
        return compression != 0;
    }

    /**
     * Get negative offset.
     *
     * @return negative offset as non-negative number.
     */
    public int getNoffset() {
        return noffset;
    }

    /**
     * Convert meta object to byte array.
     *
     * @return non-null byte array.
     */
    public byte[] toByteArray() {

		/*  */
        ByteBuffer buffer = ByteBuffer.allocate(metasize);

		/*  Create byte representation. */
		buffer.put(signature.getBytes());
        buffer.putInt(metasize);
        buffer.putInt(compression);
        buffer.putInt(noffset);

        return buffer.array();
    }

}
