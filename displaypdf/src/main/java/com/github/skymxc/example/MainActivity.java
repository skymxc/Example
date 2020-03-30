package com.github.skymxc.example;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.github.skymxc.example.databinding.ActivityMainBinding;

import java.io.File;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2020/3/26  10:50 PM
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        WebSettings webSettings = binding.webView.getSettings();
        //支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //允许js 并读取文件
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        display("file:///android_asset/demo.pdf");


    }

    void display(String url) {
        Log.e(MainActivity.class.getSimpleName(), "url>" + url);
        binding.webView.loadUrl("file:///android_asset/viewer.html?file=" + url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_src, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()) return false;
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.asset:
                display("file:///android_asset/demo.pdf");
                break;
            case R.id.local_file:
                pickFile();
                break;
            case R.id.network_file:
                int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                int checkSelfPermission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (checkSelfPermission == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission1 == PackageManager.PERMISSION_GRANTED) {
                    downloadFile(url);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                    }
                }

                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10||requestCode==11) {
            if (grantResults.length == 2) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    pickFile();
                } else {
                    Toast.makeText(this, "权限被拒绝", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 10:
                if (null == data.getData()) {
                    Log.e(MainActivity.class.getSimpleName(), "onActivityResult: 没有加载到 文件");
                    return;
                }
                Uri returnUri = data.getData();
                display(returnUri.toString());
                break;
        }
    }


    protected String getPath(Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(contentUri, selection, selectionArgs);
            }


        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    void pickFile() {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int checkSelfPermission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission1 == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");

            //指定类型
//            var types = arrayOf(pdf)
//            intent.putExtra(Intent.EXTRA_MIME_TYPES,types)
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 10);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }
    }


    void downloadFile(String uri) {
        if (TextUtils.isEmpty(uri)){
            Toast.makeText(this,"填写PDF地址",Toast.LENGTH_SHORT).show();
            return;
        }

        String substring = uri.substring(uri.lastIndexOf("/")+1);

        File file = new File(getFilesDir(),substring);
        Log.e(MainActivity.class.getSimpleName(), "downloadFile: "+file.getAbsolutePath());

        new DownloadFileAsyncTask(file, new DownloadFileAsyncTask.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                display(path);
            }

            @Override
            public void onDownloading(int progress) {
                Log.e(MainActivity.class.getSimpleName(), "onDownloading: "+progress);
            }

            @Override
            public void onDownloadFailed(String msg) {
                Log.e(MainActivity.class.getSimpleName(), "onDownloadFailed: "+msg);
            Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }).execute(uri);
    }



}
