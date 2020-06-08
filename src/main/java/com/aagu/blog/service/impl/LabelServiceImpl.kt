package com.aagu.blog.service.impl

import com.aagu.blog.dao.LabelDao
import com.aagu.blog.model.Label
import com.aagu.blog.exception.ModificationFailedException
import com.aagu.blog.service.LabelService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
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

    @CacheEvict(value = ["label"], key = "'allLabel'")
    override fun add(label: Label): Label {
        try {
            val res = labelDao.insertLabel(label)
            if (res != 1) throw ModificationFailedException("failed to add label: no row inserted")
        } catch (ex: Exception) {
            throw ModificationFailedException("failed to add label: ${ex.message}")
        }
        return label
    }

    @Caching(evict = [CacheEvict(value = ["label"], key = "'allLabel'"),
        CacheEvict(value = ["label"], key = "#label.id")])
    override fun modify(label: Label): Label {
        try {
            val res = labelDao.updateLabel(label)
            if (res != 1) throw ModificationFailedException("failed to add label: no row inserted")
        } catch (ex: Exception) {
            throw ModificationFailedException("failed to add label: ${ex.message}")
        }
        return label
    }

    @Caching(evict = [CacheEvict(value = ["label"], key = "'allLabel'"),
        CacheEvict(value = ["label"], key = "#id")])
    override fun delete(id: Int) {
        try {
            val res = labelDao.deleteLabel(id)
            if (res != 1) throw ModificationFailedException("failed to add label: no row inserted")
        } catch (ex: Exception) {
            throw ModificationFailedException("failed to add label: ${ex.message}")
        }
    }
}