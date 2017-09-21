package org.sisy;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Responsible of making all
 * byte buffer with the same byte ordering.
 */
public class ByteBufferHelper {

    /**
     * Create little endian byte buffer.
     *
     * @param capacity
     * @return non-null ByteBuffer.
     * @throws IllegalArgumentException if the capacity is invalid.
     */
    public static ByteBuffer createByteBuffer(int capacity) throws Exception {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be a non-negative size");

        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer;
    }
}
