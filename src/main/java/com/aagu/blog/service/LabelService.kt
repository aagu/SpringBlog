package com.aagu.blog.service

import com.aagu.blog.model.Label
import com.aagu.blog.exception.ModificationFailedException

interface LabelService {
    fun getById(id: Int): Label

    fun getAll(): List<Label>

    @Throws(ModificationFailedException::class)
    fun add(label: Label): Label

    @Throws(ModificationFailedException::class)
    fun modify(label: Label): Label

    @Throws(ModificationFailedException::class)
    fun delete(id: Int)
}