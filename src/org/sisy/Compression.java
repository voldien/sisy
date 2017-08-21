package org.sisy;

import java.io.*;
import java.io.File;
import java.util.zip.*;

/**
 * Responsible for creating inflate
 * and deflate file stream.
 *
 * @author Valdemar Lidnberg
 */
public class Compression {

    /**
     * Create deflate file stream.
     *
     * @param path filepath.
     * @return non-null inputstream object.
     * @throws Exception relay exception down the stack.
     */
    public static InputStream createDeflateStream(String path) throws Exception {

        File f;
        DeflaterInputStream deflate;
        FileInputStream instream;

		/*	Open and create deflate stream.	*/
        f = new File(path);
        instream = new FileInputStream(f);
        deflate = new DeflaterInputStream(instream);

        return deflate;
    }

    /**
     * Create inflate file stream.
     *
     * @param path file path.
     * @return non-null stream.
     * @throws Exception relay exception down the stack.
     */
    public static InputStream createInflateStream(String path) throws Exception {

        File f;
        InflaterInputStream inflate;
        FileInputStream instream;

		/*	Open and create deflate stream.	*/
        f = new File(path);
        instream = new FileInputStream(f);
        inflate = new InflaterInputStream(instream);

        return inflate;
    }

}
