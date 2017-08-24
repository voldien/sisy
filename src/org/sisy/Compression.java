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
     * Create deflate input stream.
     *
     * @param path filepath.
     * @return non-null InputStream object.
     * @throws Exception relay exception down the stack.
     */
    public static InputStream createDeflateStream(String path) throws Exception {

        DeflaterInputStream deflate;
        FileInputStream instream;

		/*	Open and create deflate stream.	*/
        instream = new FileInputStream(path);
        deflate = new DeflaterInputStream(instream);

        return deflate;
    }

    /**
     * Create inflate OutputStream object.
     *
     * @param path file path.
     * @return non-null stream.
     * @throws Exception relay exception down the stack.
     */
    public static OutputStream createInflateStream(String path) throws Exception {

        InflaterOutputStream inflate;
        FileOutputStream instream;

		/*	Open and create deflate stream.	*/
        instream = new FileOutputStream(path);
        inflate = new InflaterOutputStream(instream);

        return inflate;
    }

}
