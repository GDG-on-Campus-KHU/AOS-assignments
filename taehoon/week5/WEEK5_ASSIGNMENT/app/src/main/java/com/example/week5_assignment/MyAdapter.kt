package com.example.week5_assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val items: List<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // ViewHolder 클래스 정의
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //activity oncreate와 비슷. 처음에 생성될 때 setcontentview로 xml을 연결
        // 어댑터에서도 plug로 연결될 화면이 뭘까? : list_Item.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // oncreateviewholder로 만들어진 view를 bind(연결)
        // 스크롤 등 사용할 때 계속 호출되며 view에 대해 안정적으로 데이터를 매치
        holder.titleTextView.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}
