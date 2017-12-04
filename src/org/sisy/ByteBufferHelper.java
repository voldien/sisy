package org.sisy;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Responsible of making all
 * byte buffer with the same byte ordering.
 *
 * @author Valdemar Lindberg
 */
public class ByteBufferHelper {

    /**
     * Create little endian byte buffer.
     *
     * @param capacity size in number of bytes.
     * @return non-null ByteBuffer object.
     * @throws IllegalArgumentException if the capacity is invalid.
     */
    public static ByteBuffer createByteBuffer(int capacity) throws Exception {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be a non-negative size");

        /*  Allocate buffer and set byte ordering.  */
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer;
    }
}
