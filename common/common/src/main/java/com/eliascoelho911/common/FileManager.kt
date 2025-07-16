package com.eliascoelho911.common

import java.io.File
import java.io.IOException
import java.io.InputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FileManager(private val basePath: String) {

    init {
        val baseDir = File(basePath)
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
    }

    suspend fun createDirectory(dirName: String): File = withContext(Dispatchers.IO) {
        val dir = File(basePath, dirName)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir
    }

    fun directoryExists(dirName: String): Boolean {
        val dir = File(basePath, dirName)
        return dir.exists()
    }

    suspend fun saveFile(
        fileName: String,
        inputStream: InputStream,
        dirName: String? = null
    ): Boolean =
        withContext(Dispatchers.IO) {
            saveFileGeneric(dirName, fileName) { file ->
                inputStream.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }

    suspend fun saveFile(fileName: String, content: ByteArray, dirName: String? = null): Boolean =
        withContext(Dispatchers.IO) {
            saveFileGeneric(dirName, fileName) { file ->
                file.writeBytes(content)
            }
        }

    suspend fun deleteFile(fileName: String, dirName: String? = null): Boolean =
        withContext(Dispatchers.IO) {
            val file = File(basePath, dirName?.let { "$dirName/$fileName" } ?: fileName)
            file.delete()
        }

    suspend fun deleteDirectory(dirName: String): Boolean =
        withContext(Dispatchers.IO) {
            val dir = File(basePath, dirName)
            dir.deleteRecursively()
        }

    suspend fun getFile(fileName: String, dirName: String? = null): File =
        withContext(Dispatchers.IO) {
            val dir = dirName?.let { "$dirName/" } ?: ""
            File(basePath, "$dir$fileName")
        }

    suspend fun getDirectory(dirName: String): File =
        withContext(Dispatchers.IO) {
            File(basePath, dirName)
        }

    suspend fun getBaseDirectory(): File =
        withContext(Dispatchers.IO) {
            File(basePath)
        }

    suspend fun listFilesInDirectory(dirName: String? = null): List<String> =
        withContext(Dispatchers.IO) {
            val dir = File(basePath, dirName ?: "")
            if (dir.exists()) {
                return@withContext dir.list()?.toList().orEmpty()
            }
            emptyList()
        }

    private fun saveFileGeneric(
        dirName: String?,
        fileName: String,
        writeAction: (File) -> Unit
    ): Boolean {
        val dir = File(basePath, dirName ?: "")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, fileName)
        return try {
            writeAction(file)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}