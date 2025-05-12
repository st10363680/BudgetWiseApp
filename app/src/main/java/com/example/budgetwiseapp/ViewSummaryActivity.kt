package com.example.budgetwiseapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgetwiseapp.data.db.AppDatabase
import kotlinx.coroutines.launch

class ViewSummaryActivity : AppCompatActivity() {
    private lateinit var totalSpentText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_summary)

        totalSpentText = findViewById(R.id.tvTotalSpent)

        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val total = db.expenseDao().getTotalExpenses() ?: 0.0  // Safely handle null
            totalSpentText.text = "Total Spent: R$total"
        }
    }
}
