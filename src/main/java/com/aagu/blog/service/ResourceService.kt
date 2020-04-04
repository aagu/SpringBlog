package com.aagu.blog.service

import com.qiniu.storage.model.FileInfo

interface ResourceService {
    fun getFileList(): List<FileInfo>

    fun getUploadToken(): String

    fun getReplaceToken(originalKey: String): String
}