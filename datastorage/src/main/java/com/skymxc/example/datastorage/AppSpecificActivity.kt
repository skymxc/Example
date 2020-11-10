package com.skymxc.example.datastorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.io.*
import java.nio.charset.StandardCharsets


/**
 * 访问应用专属文件
 * 内部外部都不需要申请权限
 * 会随着应用卸载而被删除
 * 内部存储缓存文件会在系统空间不足时被系统删除，使用前应该检查是否存在
 *
 * https://developer.android.google.cn/training/data-storage/app-specific
 */
class AppSpecificActivity : AppCompatActivity() {


    lateinit var mTvResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_specific)
        mTvResult = findViewById(R.id.test_tv_result)
        findViewById<Button>(R.id.test_btn_internal_allocatable).setOnClickListener {
            internalAllocatableBytes()
        }
        findViewById<Button>(R.id.test_btn_external_allocatable).setOnClickListener {
            externalAllocatableBytes()
        }
        findViewById<Button>(R.id.test_btn_to_remove).setOnClickListener {
            toRemoveFile()
        }
        setOnClickListener(R.id.test_btn_read_internal_file, View.OnClickListener {
            readInternalFile()
        })
        setOnClickListener(R.id.test_btn_write_internal_file, View.OnClickListener {
            writeInternalFile()
        })
        setOnClickListener(R.id.test_btn_internal_list, View.OnClickListener {
            internalList()
        })
        setOnClickListener(R.id.test_btn_create_nested_dir, View.OnClickListener {
            createNestedDir()
        })
        setOnClickListener(R.id.test_btn_del_internal_cache, View.OnClickListener {
            deleteInternalCacheFile()
        })

        setOnClickListener(R.id.test_btn_read_internal_cache_file, View.OnClickListener {
            readInternalCacheFile()
        })
        setOnClickListener(R.id.test_btn_write_internal_cache_file, View.OnClickListener {
            writeInternalCacheFile()
        })
        setOnClickListener(R.id.test_btn_write_external_cache_file, View.OnClickListener {
            writeExternalCacheFile()
        })

        setOnClickListener(R.id.test_btn_read_external_cache_file, View.OnClickListener {
            readExternalCacheFile()
        })
        setOnClickListener(R.id.test_btn_write_external_file, View.OnClickListener {
            writeExternalFile()
        })

        setOnClickListener(R.id.test_btn_read_external_file, View.OnClickListener {
            readExternalFile()
        })
     setOnClickListener(R.id.test_btn_external_list, View.OnClickListener {
            externalRoot()
        })

    }

    private fun setOnClickListener(btnId: Int, listener: View.OnClickListener) {
        findViewById<Button>(btnId).setOnClickListener(listener)
    }

    private fun externalRoot() {
        //files 目录
        val externalFilesDir = getExternalFilesDir(null)
        var buffer =StringBuffer("外部专属空间files目录：")
        if (null!=externalFilesDir) {
            buffer.append("\n")
            buffer.append(externalFilesDir)
            externalFilesDir.list()?.forEach {
                buffer.append("\n")
                buffer.append(it)
            }
        }
        showAndPrint(buffer.toString())
    }

    private fun readExternalFile() {
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

        val file = File(externalFilesDir, "myFile")
        showAndPrint(readFile(file))
    }

    private fun readExternalCacheFile() {
        val file = File(externalCacheDir, "myFile")
        showAndPrint(readFile(file))
    }


    private fun writeExternalFile() {
        //这个获取的是外部存储的应用专属文件目录，需要传入对应的文件类型。
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalFilesDir, "myFile")
        val content = """这是被写入到外部存储文档里的内容
            |第二行
        """.trimMargin()
        showAndPrint(writeFile(file, content))
    }

    private fun writeExternalCacheFile() {
        //这个获取的是外部存储的应用专属空间缓存目录
        val file = File(externalCacheDir, "myFile")
        val content = """这是被写入到外部缓存里的内容
            |第二行
        """.trimMargin()
        showAndPrint(writeFile(file, content))
    }

    private fun showAndPrint(string: String) {
        mTvResult.text = string
        println(string)
    }

    private fun writeFile(file: File, content: String): String {
        return if (file.exists()) {
            BufferedWriter(FileWriter(file, false)).use { it.write(content) }
            "$file 存在，已写入：\n$content"
        } else {
            if (file.createNewFile()) {
                BufferedWriter(FileWriter(file, false)).use { it.write(content) }
                "$file 创建成功并写入：\n$content"
            } else {
                "$file 文件创建失败"
            }
        }
    }

    private fun readFile(file: File): String {
        return if (file.exists()) {
            val useLines = BufferedReader(FileReader(file)).useLines {
                val fold = it.fold("$file 存在，读取内容：\n") { some, text -> "$some \n$text" }
                fold
            }
            useLines
        } else "$file 不存在"
    }

    private fun deleteInternalCacheFile() {
        val cacheFile = File(cacheDir, "cacheFile")
        val string = if (cacheFile.exists()) {
            "$cacheFile 删除结果${cacheFile.delete()}"
        } else {
            "$cacheFile 不存在"
        }
        mTvResult.text = string
        println(string)
    }

    private fun writeInternalCacheFile() {
        val cacheFile = File(cacheDir, "cacheFile")
        val content = """我是被写入到缓存文件里的内容
            |第二行。
        """
        val string = if (cacheFile.exists()) {
            BufferedWriter(FileWriter(cacheFile, false)).use {
                it.write(content)
            }
            "${cacheFile} 存在，已写入 \n$content"
        } else {
            if (cacheFile.createNewFile()) {
                BufferedWriter(FileWriter(cacheFile, false)).use {
                    it.write(content)
                }
                "${cacheFile} 已创建并写入 \n$content"
            } else {
                "${cacheFile} 创建失败，无法写入"
            }
        }
        mTvResult.text = string
        println(string)
    }

    private fun readInternalCacheFile() {
        //缓存目录 cacheDir
        //根目录是 getDir()
        val cacheFile = File(cacheDir, "cacheFile")
        val string = if (cacheFile.exists()) {
            val useLines = BufferedReader(FileReader(cacheFile)).useLines { lines ->
                val fold = lines.fold("读取缓存文件 $cacheFile :") { some, text ->
                    "$some \n$text"
                }
                fold
            }
            useLines
        } else {
            //当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
            "$cacheFile 不存在"
        }
        mTvResult.text = string
        println(string)
    }

    private fun createNestedDir() {
        //getDir 得到的是 根目录
        //filesDir 是 files 目录
        val dir = File(filesDir, "dir")
        val string = if (dir.exists()) {
            "${dir} 已存在"
        } else {
            "${dir} 创建结果：${dir.mkdir()}"
        }
        mTvResult.text = string
        println(string)
    }

    private fun internalList() {
        var buffer = StringBuffer("内部文件：")
        fileList().forEach {
            buffer.append("\n")
            buffer.append(it)
        }
        mTvResult.text = buffer.toString()
        println(buffer.toString())
    }

    private fun readInternalFile() {
        //内部文件目录 filesDir
        //根目录是 getDir()
        val file = File(filesDir, "myFile")
        val string = if (file.exists()) {
            val useLines = openFileInput(file.name).bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                val fold = lines.fold("读取内部文件${file.name}:") { some, text ->
                    "$some \n$text"
                }
                fold
            }
//            "读取内部文件 ${file.name}: ${useLines}"
            useLines
        } else {
            "${file.name} 文件不存在"
        }
        mTvResult.text = string
        println(string)
    }

    private fun writeInternalFile() {
        val file = File(filesDir, "myFile")
        val content = """Hello world!
           我是第二行。 
        """
        val string = if (file.exists()) {
            openFileOutput(file.name, MODE_PRIVATE).use {
                it.write(content.toByteArray(StandardCharsets.UTF_8))
            }
            "${file.name} 存在，已写入 \n$content"
        } else {
            if (file.createNewFile()) {
                openFileOutput(file.name, MODE_PRIVATE).use { it.write(content.toByteArray(StandardCharsets.UTF_8)) }
                "${file.name} 已创建并写入 \n$content"
            } else {
                "${file.name} 创建失败，无法写入"
            }
        }
        mTvResult.text = string
        println(string)
    }

    private fun internalAllocatableBytes() {
        val bytes = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            getInternalAllocatableBytes()
        } else {
            0
        }
        val string = "内部应用专属文件空间可提供 $bytes byte，${bytes / 1024} kb ${bytes / 1024 / 1024} m ${bytes / 1024 / 1024 / 1024} G"
        println(string)
        mTvResult.text = string
    }

    private fun externalAllocatableBytes() {
        //1.外部存储是否可用
        if (!isExternalStorageWritable()) {
            mTvResult.text = "外部存储空间不可用"
            return
        }
        //2.可用空间
        val bytes = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            getExternalAllocatableBytes()
        } else {
            0
        }
        val string = "外部部应用专属文件空间可提供 $bytes byte，${bytes / 1024} kb ${bytes / 1024 / 1024} m ${bytes / 1024 / 1024 / 1024} G"
        println(string)
        mTvResult.text = string
    }

    private fun toRemoveFile() {
        val intent = Intent().apply {
            action = ACTION_MANAGE_STORAGE
        }
        startActivity(intent)
    }
}