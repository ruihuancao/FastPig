package com.ruihuan.storagepig.http.lisenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;


public abstract class FileLisenter extends BaseLisenter<File> {

    private String pathname;

    public FileLisenter(String pathname) {
        this.pathname = pathname;
    }

    @Override
    public File convert(Call call, Response response) throws IOException {
        File file = new File(pathname);
        byte[] buf = new byte[2048];
        int len = 0;
        long current = 0;
        long total = response.body().contentLength();
        InputStream is = response.body().byteStream();
        FileOutputStream fos = new FileOutputStream(file);
        while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
            current += len;
            onProgress(current, total);
        }
        fos.flush();
        is.close();
        fos.close();
        return file;
    }

}
