package com.my.apiproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.apiproject.databinding.ListItemLayBinding
import com.my.apiproject.mvvm.model.Item

class MainListAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mainList = emptyList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ListItemLayBinding.inflate(
                LayoutInflater.from(mContext),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).binding.run {
            repositoryTitle.text = mainList[position].name
            repositoryFullTitle.text = mainList[position].fullName
            repositoryDescription.text = mainList[position].description
            languageTitle.text = mainList[position].language!!
        }
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Item>) {
        mainList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ListItemLayBinding) :
        RecyclerView.ViewHolder(binding.root)
}