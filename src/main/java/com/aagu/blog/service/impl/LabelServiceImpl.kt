package com.aagu.blog.service.impl

import com.aagu.blog.Dao.LabelDao
import com.aagu.blog.Models.Label
import com.aagu.blog.service.LabelService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class LabelServiceImpl(private val labelDao: LabelDao) : LabelService {
    @Cacheable(value = ["label"], key = "#id")
    override fun getById(id: Int): Label {
        return labelDao.getById(id)
    }

    @Cacheable(value = ["label"], key = "'allLabel'")
    override fun getAll(): List<Label> {
        return labelDao.all
    }
}