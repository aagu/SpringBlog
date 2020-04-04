package com.aagu.blog.service.impl

import com.aagu.blog.service.ResourceService
import com.qiniu.storage.BucketManager
import com.qiniu.storage.Configuration
import com.qiniu.storage.Region
import com.qiniu.storage.model.FileInfo
import com.qiniu.util.Auth
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class ResourceServiceImpl : ResourceService {
    @Value("\${qiniu.accessKey}")
    private lateinit var accessKey: String

    @Value("\${qiniu.secretKey}")
    private lateinit var secretKey: String

    @Value("\${qiniu.bucket}")
    private lateinit var bucketName: String

    // 七牛云储存分区
    private val cfg = Configuration(Region.region0())

    private lateinit var auth: Auth

    override fun getFileList(): List<FileInfo> {
        val fileList = LinkedList<FileInfo>()
        val bucketManager = BucketManager(getAuth(), cfg)
        val fileListIterator = bucketManager.createFileListIterator(bucketName, "", 40, "")
        while (fileListIterator.hasNext()) {
            fileList.addAll(fileListIterator.next())
        }

        return fileList
    }

    override fun getUploadToken(): String {
        return getAuth().uploadToken(bucketName)
    }

    override fun getReplaceToken(originalKey: String): String {
        return getAuth().uploadToken(bucketName, originalKey)
    }

    private fun getAuth(): Auth {
        if (!::auth.isInitialized) {
            auth = Auth.create(accessKey, secretKey)
        }

        return auth
    }
}