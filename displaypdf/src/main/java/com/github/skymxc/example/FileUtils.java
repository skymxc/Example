package com.github.skymxc.example;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2020/3/26  10:42 PM
 */
public class FileUtils {


    public static void copyToFile(FileDescriptor fd, File destFile, OnCopyListener listener) {

    }

    public interface OnCopyListener {
        void onCopySuccess(String path);

        void onCopying(int progress);

        void onCopyFailed(String msg);
    }

}
