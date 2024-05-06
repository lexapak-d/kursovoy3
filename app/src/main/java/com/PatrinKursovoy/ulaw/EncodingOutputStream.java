package com.PatrinKursovoy.ulaw;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Кодирование
 */

public class EncodingOutputStream extends FilterOutputStream {

    private static final int cClip = 32635;
    private static final int cBias = 0x84;

    public EncodingOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void write(byte[] buffer, int length, byte[] outBuffer) throws IOException {
        int encoded = encode(buffer, 0, length, outBuffer);
        out.write(outBuffer, 0, encoded);
    }

    public int encode(byte[] buffer, int offset, int length, byte[] outBytes) {
        int count = length / 2;
        short sample;

        for (int i = 0; i < count; i++) {
            sample = (short) (((buffer[offset++] & 0xFF) | (buffer[offset++]) << 8));
            outBytes[i] = compress(sample);
        }

        return count;
    }

    protected byte compress(short sample) {
        int sign;
        int exponent;
        int mantissa;
        int compressedByte;

        sign = (sample >> 8) & 0x80;

        if (sign != 0) {
            sample *= -1;
        }

        if (sample > cClip) {
            sample = cClip;
        }

        sample += cBias;

        exponent = uLawCompressTable[(sample >> 7) & 0x00FF];
        mantissa = (sample >> (exponent + 3)) & 0x0F;
        compressedByte = ~(sign | (exponent << 4) | mantissa);

        return (byte) (compressedByte & 0x000000FF);
    }

    private final int[] uLawCompressTable = {
            0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7
    };
}
