package com.example.android.camera2basic;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import android.media.Image;
import android.util.Log;

public class LuminosityAnalyzer {
    private long lastAnalyzedTimestamp = 0L;
    private byte[] toByteArray(ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        byte[] data = new byte[byteBuffer.remaining()];
        byteBuffer.get(data);
        return data;
    }
    public void analyze(Image image) {
        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] data = toByteArray(buffer);

            long total=0;
            for(byte aByte : data){
                total += ((int) aByte) & 0xff;
            }
            double luma = total/(double)data.length;
            Log.d("Charles", String.format("luminosity=%f",luma));
            this.lastAnalyzedTimestamp = currentTimestamp;
        }
    }
}
