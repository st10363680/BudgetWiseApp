package com.example.budgetwiseapp.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetwiseapp.data.entities.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM category_table WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Long): Category?

}
