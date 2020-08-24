package com.example.academy.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ContentEntity (
    @ColumnInfo(name = "content")
    var content: String?
)