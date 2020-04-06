package com.aagu.blog.service.impl

import com.aagu.blog.Dao.LabelDao
import com.aagu.blog.Models.Label
import com.aagu.blog.service.LabelService
import org.springframework.stereotype.Service

@Service
class LabelServiceImpl(private val labelDao: LabelDao) : LabelService {
    override fun getById(id: Int): Label {
        return labelDao.getById(id)
    }

    override fun getAll(): List<Label> {
        return labelDao.all
    }
}