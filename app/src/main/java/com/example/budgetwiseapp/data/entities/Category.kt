package com.example.budgetwiseapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
