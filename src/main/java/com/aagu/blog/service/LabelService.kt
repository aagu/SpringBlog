package com.aagu.blog.service

import com.aagu.blog.Models.Label

interface LabelService {
    fun getById(id: Int): Label

    fun getAll(): List<Label>
}