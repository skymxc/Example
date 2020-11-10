package com.skymxc.example.datastorage

import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import java.io.File


/**
 * 可提供空间
 *
 * @param path
 * @return
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getAllocatableBytes(path: File): Long {
    val storageManager = APP.application.getSystemService<StorageManager>()!!
    val uuid = storageManager.getUuidForPath(path)
    return storageManager.getAllocatableBytes(uuid)
}

/**
 *
 *
 * @return 内部存储可提供空间
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getInternalAllocatableBytes(): Long = getAllocatableBytes(APP.application.applicationContext.filesDir)

/**
 *
 * @return 外部空间是否可用
 */
fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

/**
 * @return 外部存储可提供空间
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getExternalAllocatableBytes(): Long {
    if (!isExternalStorageWritable()) {
        return 0
    }
    val externalFilesDir = APP.application.applicationContext.getExternalFilesDir(null)
    return if (externalFilesDir == null) 0 else getAllocatableBytes(externalFilesDir)
}
