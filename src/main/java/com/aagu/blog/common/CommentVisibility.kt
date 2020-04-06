package com.aagu.blog.common

enum class CommentVisibility {
    ALL,
    VISIBLE, // unread + read
    INVISIBLE, // deleted
    UNREAD, READ, DELETED
}