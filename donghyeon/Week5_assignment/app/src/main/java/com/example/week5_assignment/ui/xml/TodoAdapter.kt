package com.example.week5_assignment.ui.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_assignment.R

class TodoAdapter(
    private val todoList: MutableList<Pair<Boolean, String>>,
    private val onCheckedChange: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.todoCheckBox)
        val todoText: TextView = itemView.findViewById(R.id.todoText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val (isChecked, text) = todoList[position]

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = isChecked
        holder.todoText.text = text

        holder.checkBox.setOnCheckedChangeListener { _, checked ->
            onCheckedChange(position, checked)
        }
    }

    override fun getItemCount(): Int = todoList.size
}
