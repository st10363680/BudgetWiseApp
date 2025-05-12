package com.example.budgetwiseapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetwiseapp.data.entities.Expense

class ExpenseAdapter(private var expenseList: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInfo: TextView = itemView.findViewById(R.id.tvExpenseInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        val context = holder.itemView.context
        holder.tvInfo.text = context.getString(
            R.string.expense_info,
            expense.categoryName.ifBlank { "Unknown" },
            expense.amount,
            expense.date
        )
    }

    override fun getItemCount() = expenseList.size

    fun updateData(newList: List<Expense>) {
        val diffCallback = ExpenseDiffCallback(expenseList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        expenseList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    class ExpenseDiffCallback(
        private val oldList: List<Expense>,
        private val newList: List<Expense>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

