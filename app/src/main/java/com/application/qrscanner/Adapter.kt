package com.application.qrscanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val userlist: ArrayList<DataHistory>) :
    RecyclerView.Adapter<Adapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history,
            parent, false )
        return Holder(itemView)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = userlist[position]
        holder.username.text = currentItem.username
        holder.password.text = currentItem.password
    }

    override fun getItemCount(): Int {
        return userlist.size
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username = itemView.findViewById<TextView>(R.id.qrname)
        val password = itemView.findViewById<TextView>(R.id.password)

    }
}