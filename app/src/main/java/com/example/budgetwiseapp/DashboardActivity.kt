package com.example.budgetwiseapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        findViewById<Button>(R.id.btnAddCategory).setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.btnSetGoals).setOnClickListener {
            startActivity(Intent(this, SetGoalsActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        findViewById<Button>(R.id.btnSummary).setOnClickListener {
            startActivity(Intent(this, ViewSummaryActivity::class.java))

        }
        findViewById<Button>(R.id.btnViewPhoto).setOnClickListener {
            startActivity(Intent(this, ViewPhotoActivity::class.java))
        }
    }
}
