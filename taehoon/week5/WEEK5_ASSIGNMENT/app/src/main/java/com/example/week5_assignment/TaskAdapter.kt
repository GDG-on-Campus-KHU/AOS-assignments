package com.example.week5_assignment.com.example.week5_assignment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_assignment.R


//RecycleView 데이터 관리. 뷰 생성 및 바인딩
class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onCheckedChange: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBoxTask)//체크박스
        val taskName: TextView = view.findViewById(R.id.tvTaskName)//텍스트뷰
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //activity oncreate와 비슷. 처음에 생성될 때 setcontentview로 xml을 연결
        // 어댑터에서도 plug로 연결될 화면이 뭘까? : list_Item.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // oncreateviewholder로 만들어진 view를 bind(연결)
        // 스크롤 등 사용할 때 계속 호출되며 view에 대해 안정적으로 데이터를 매치
        val task = tasks[position]//체크박스 상태 변경을 계속 확인
        holder.taskName.text = task.name
        holder.checkBox.isChecked = task.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isChecked = isChecked
            onCheckedChange(task)
        }
    }

    override fun getItemCount() = tasks.size

    fun removeCheckedTasks() {//체크된 아이템 제
        Log.d("check1", tasks.toString())
        tasks.removeAll { it.isChecked }
        Log.d("check1", tasks.toString())

        notifyDataSetChanged()
    }

    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }
}

data class Task(var name: String, var isChecked: Boolean = false)
