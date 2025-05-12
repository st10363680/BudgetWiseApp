package com.example.budgetwiseapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgetwiseapp.data.db.AppDatabase
import com.example.budgetwiseapp.data.entities.Expense
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private var selectedPhotoUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            selectedPhotoUri = result.data!!.data
            Toast.makeText(this, "Photo selected!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val amountEditText = findViewById<EditText>(R.id.editTextAmount)
        val descriptionEditText = findViewById<EditText>(R.id.editTextDescription)
        val dateEditText = findViewById<EditText>(R.id.etDate)
        val startTimeEditText = findViewById<EditText>(R.id.etStartTime)
        val endTimeEditText = findViewById<EditText>(R.id.etEndTime)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val addPhotoButton = findViewById<Button>(R.id.btnAddPhoto)
        val saveButton = findViewById<Button>(R.id.buttonSaveExpense)

        // Set up spinner values
        val categories = listOf("Food", "Transport", "Entertainment", "Bills")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        // Launch photo picker
        addPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        // Use singleton instance of DB
        val db = AppDatabase.getDatabase(this)

        // Save button logic
        saveButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull()
            val description = descriptionEditText.text.toString()
            val rawDate = dateEditText.text.toString()
            val startTime = startTimeEditText.text.toString()
            val endTime = endTimeEditText.text.toString()
            val selectedCategory = spinnerCategory.selectedItem.toString()

            // Validate and format date
            val formattedDate = try {
                val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val parsedDate = inputFormat.parse(rawDate)
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(parsedDate!!)
            } catch (e: Exception) {
                null
            }

            if (amount != null && description.isNotBlank() && !formattedDate.isNullOrBlank()) {
                val expense = Expense(
                    description = description,
                    amount = amount,
                    date = formattedDate,
                    startTime = startTime,
                    endTime = endTime,
                    categoryName = selectedCategory,
                    categoryId = 1,
                    photoPath = selectedPhotoUri?.toString() ?: ""
                )

                lifecycleScope.launch {
                    db.expenseDao().insertExpense(expense)
                    runOnUiThread {
                        Toast.makeText(this@AddExpenseActivity, "Expense saved!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Please fill all fields correctly, especially date in dd/MM/yyyy format.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
