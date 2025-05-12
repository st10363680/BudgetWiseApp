package com.example.budgetwiseapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetwiseapp.data.db.AppDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var datePickerStart: DatePicker
    private lateinit var datePickerEnd: DatePicker
    private lateinit var filterLayout: LinearLayout
    private lateinit var toggleFilterButton: Button
    private lateinit var filterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        recyclerView = findViewById(R.id.rvExpenses)
        datePickerStart = findViewById(R.id.datePickerStart)
        datePickerEnd = findViewById(R.id.datePickerEnd)
        filterLayout = findViewById(R.id.filterLayout)
        toggleFilterButton = findViewById(R.id.btnToggleFilter)
        filterButton = findViewById(R.id.btnFilter)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(emptyList())
        recyclerView.adapter = adapter

        val db = AppDatabase.getDatabase(this)

        // Helper to get date from DatePicker
        fun getSelectedDate(dp: DatePicker): String {
            val day = dp.dayOfMonth
            val month = dp.month
            val year = dp.year
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }

        // Toggle filter visibility
        toggleFilterButton.setOnClickListener {
            if (filterLayout.visibility == View.GONE) {
                filterLayout.visibility = View.VISIBLE
                toggleFilterButton.text = "Hide Filter"
            } else {
                filterLayout.visibility = View.GONE
                toggleFilterButton.text = "Show Filter"
            }
        }

        // Filter button click
        filterButton.setOnClickListener {
            val startDate = getSelectedDate(datePickerStart)
            val endDate = getSelectedDate(datePickerEnd)

            lifecycleScope.launch {
                val expenses = db.expenseDao().getExpensesBetween(startDate, endDate)
                if (expenses.isEmpty()) {
                    Toast.makeText(this@ViewExpensesActivity, "No expenses found.", Toast.LENGTH_SHORT).show()
                }
                adapter.updateData(expenses)
            }
        }

        // Load all expenses by default
        lifecycleScope.launch {
            val expenses = db.expenseDao().getAllExpenses()
            adapter.updateData(expenses)
        }
    }
}
