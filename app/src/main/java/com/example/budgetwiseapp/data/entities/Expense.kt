package com.example.budgetwiseapp.data.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val description: String,
    @ColumnInfo(name = "amount") val amount: Double,
    val date: String,
    val categoryName: String,
    val startTime: String = "",
    val endTime: String = "",
    val categoryId: Long,  // FK linking to Category
    val photoPath: String? = null
)
