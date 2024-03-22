package com.example.appassignment.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appassignment.R
import com.example.appassignment.dto.Todo
import com.example.appassignment.dto.TodosList
import java.util.Locale

class Adapter(val context: Context, val mlist: ArrayList<Todo>) : RecyclerView.Adapter<Adapter.MyViewHolder>(){

    private var filteredDataList: List<Todo> = mlist

    inner class  MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoText: TextView = itemView.findViewById(R.id.todoTv)
        val completionText: TextView = itemView.findViewById(R.id.completionTv)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_views, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemView = mlist[position]
        holder.todoText.text = itemView.todo
        holder.completionText.text = itemView.completed.toString()
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    fun updateList(list : List<Todo>){
        mlist.clear()
        mlist.addAll(list)
        notifyDataSetChanged()
    }

}