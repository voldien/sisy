package org.sisy;

import java.nio.file.Paths;

/**
 * Main program logic.
 *
 * @author Valdemar Lindberg
 */
public class Program {

    /**
     * Get version of program.
     *
     * @return non-null terminated string.
     */
    static String getVersion() {
        return "0.1a0";
    }

    /**
     * Main function of the program.
     *
     * @param argv user argument.
     */
    static public void main(String[] argv) {

        Config config = null;
        String[] files = null;
        String[] directory = null;
        Cryptographic.CIPHER cipher;

		/*	Read user option.	*/
        try {
            config = Config.createConfig(argv);
        } catch (Exception ex) {
            System.console().printf("Failed while reading user options: '%s'.\n", ex.getMessage());
            System.exit(1);
        }

		/*  Get absolute filepath for each file for encryption. */
        try {
            /*	Get all files.	*/
            files = config.getStrings("files");
            directory = FileUtility.getAllSubDirectory(config.getString("recursion"));

        } catch (Exception ex) {
            System.console().printf("Requires at least a single file.\n");
            System.exit(1);
        }

        /*	Get cipher enum for cryptographic.	*/
        cipher = Cryptographic.CIPHER.valueOf(config.getString("cipher").toUpperCase().concat("_").concat(
                Integer.toString(config.getInt("keysize"))
        ));


		/*	Encrypt/Decrypt file/s.	*/
        try {
            logicFunction(files, directory, config.getString("password"), cipher, config);
        } catch (Exception ex) {
            System.console().printf("Error occurred during encryption/decryption: '%s'.\n", ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }

		/*	Success.	*/
        System.exit(0);
    }

    /**
     * @param files
     * @param directories
     * @param password
     * @param cipher
     * @param config
     */
    /*  TODO rename.    */
    public static void logicFunction(String[] files, String[] directories, String password, Cryptographic.CIPHER cipher, Config config) throws Exception {

        boolean encrypt = config.getBoolean("encrypt");
        boolean compress = config.getBoolean("compression");
        String destpath;

        try {
            /*	Iterate through each file paths.	*/
            for (String path : files) {

                /*	Expand path.    */
                path = FileUtility.expandPath(path);

                /*  Solve if any regex pattern. */
                path = Paths.get(path).toAbsolutePath().toString();

                /*	Destination file path.  */
                if (config.getString("output").isEmpty())
                    destpath = path.concat(".enc");
                else
                    destpath = config.getString("output");

                /*	Encrypt file.	*/
                if (encrypt) {
                    if (compress) {
                        /*Compress.	*/
                        Cryptographic.encryptStream(Compression.createDeflateStream(path), destpath, cipher,
                                password.getBytes());
                    } else {
                        /*	Encrypt.	*/
                        Cryptographic.encryptFile(path, destpath, cipher,
                                password.getBytes());
                    }
                } else {
                    if (compress) {
                        /*	UnCompress. */
                        Cryptographic.decryptStream(Compression.createInflateStream(path), destpath, cipher,
                                password.getBytes());
                    } else {
                        /**/
                        Cryptographic.decryptFile(path, destpath, cipher,
                                password.getBytes());
                    }
                }
            }

            /*  Check directory.    */
            if (directories == null)
                return;

            /*  Iterate through each sub directory. */
            for (String subdir : directories) {
                logicFunction(FileUtility.getAllFiles(subdir), FileUtility.getAllSubDirectory(subdir), password, cipher, config);
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

}
