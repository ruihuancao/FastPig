package com.ruihuan.fastcommon.storage.http.lisenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;


public abstract class FileLisenter extends BaseLisenter<File> {

    private String pathname;


    public FileLisenter(String pathname) {
        this.pathname = pathname;
    }

    @Override
    public File convert(Call call, Response response) throws IOException {
//        File file = new File(pathname);
//        byte[] buf = new byte[2048];
//        int len = 0;
//        long current = 0;
//        long total = response.body().contentLength();
//        InputStream is = response.body().byteStream();
//        FileOutputStream fos = new FileOutputStream(file);
//        int oldRate = 0;
//        while ((len = is.read(buf)) != -1) {
//            fos.write(buf, 0, len);
//            current += len;
//            int rate = Math.round(current * 1F / total * 100F);
//            if(oldRate != rate){
//                onProgress(current, total, rate * 1F / 100);
//                oldRate = rate;
//            }
//        }
//        fos.flush();
//        is.close();
//        fos.close();
//        return file;
        File file = new File(pathname);
        Sink sink = Okio.sink(file);
        Source source = Okio.source(response.body().byteStream());
        final long totalSize = response.body().contentLength();
        BufferedSink bufferedSink = Okio.buffer(sink);
        bufferedSink.writeAll(new ForwardingSource(source) {
            long sum = 0;
            int oldRate = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long readSize = super.read(sink, byteCount);
                if (readSize != -1L) {
                    sum += readSize;
                    final int rate = Math.round(sum * 1F / totalSize * 100F);
                    if (oldRate != rate) {
                        onProgress(sum, totalSize, rate * 1F / 100);
                        oldRate = rate;
                    }
                }
                return readSize;
            }
        });
        bufferedSink.flush();
        Util.closeQuietly(sink);
        Util.closeQuietly(source);
        return file;
    }

    public File saveFile(Response response) throws IOException {
        File file = new File(pathname);
        Sink sink = Okio.sink(file);
        Source source = Okio.source(response.body().byteStream());
        final long totalSize = response.body().contentLength();
        BufferedSink bufferedSink = Okio.buffer(sink);
        bufferedSink.writeAll(new ForwardingSource(source) {
            long sum = 0;
            int oldRate = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long readSize = super.read(sink, byteCount);
                if (readSize != -1L) {
                    sum += readSize;

                    final int rate = Math.round(sum * 1F / totalSize * 100F);
                    if (oldRate != rate) {
                        onProgress(sum, totalSize, rate * 1F / 100);
                        oldRate = rate;
                    }
                }
                return readSize;
            }
        });
        bufferedSink.flush();
        Util.closeQuietly(sink);
        Util.closeQuietly(source);
        return file;

    }
}
