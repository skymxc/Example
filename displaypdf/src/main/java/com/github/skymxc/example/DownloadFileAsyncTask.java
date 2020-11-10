package com.github.skymxc.example;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2020/3/28  5:55 PM
 */
public class DownloadFileAsyncTask extends AsyncTask<String, Integer, String> {

    File file;

    OnDownloadListener listener;

    public DownloadFileAsyncTask(File file, OnDownloadListener listener) {
        this.file = file;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        Request request = new Request.Builder().url(url).build();


        Call call = new OkHttpClient().newCall(request);
        Response execute = null;
        try {
            execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        if (execute.isSuccessful()) {
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len;
            FileOutputStream fos = null;
            try {
                is = execute.body().byteStream();
                long total = execute.body().contentLength();
                if (!file.getParentFile().exists()){
                    boolean mkdir = file.getParentFile().mkdir();
                    if (!mkdir){
                        return "目录创建失败";
                    }
                }

                fos = new FileOutputStream(file);
                long sum = 0;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += len;
                    int progress = (int) (sum * 1.0f / total * 100);
                   publishProgress(progress);
                }
                fos.flush();

            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return execute.message();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onDownloading(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
      if (TextUtils.isEmpty(s)){
          Uri uri = Uri.fromFile(file);
          listener.onDownloadSuccess(uri.toString());
      }else{
          listener.onDownloadFailed(s);
      }
    }

    public interface OnDownloadListener {
        void onDownloadSuccess(String path);

        void onDownloading(int progress);

        void onDownloadFailed(String msg);
    }

}
