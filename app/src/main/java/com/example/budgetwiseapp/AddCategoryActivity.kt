package com.example.budgetwiseapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.budgetwiseapp.data.db.AppDatabase
import com.example.budgetwiseapp.data.entities.Category
import kotlinx.coroutines.launch

class AddCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        val categoryNameEditText = findViewById<EditText>(R.id.editTextCategoryName)
        val saveButton = findViewById<Button>(R.id.buttonSaveCategory)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "budgetwise-db"
        ).build()

        saveButton.setOnClickListener {
            val name = categoryNameEditText.text.toString().trim()

            if (name.isNotEmpty()) {
                val category = Category(name = name)
                lifecycleScope.launch {
                    db.categoryDao().insertCategory(category)
                    runOnUiThread {
                        Toast.makeText(this@AddCategoryActivity, "Category saved!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
