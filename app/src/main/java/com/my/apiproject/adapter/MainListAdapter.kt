package com.my.apiproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.apiproject.R
import com.my.apiproject.databinding.ListItemLayBinding
import com.my.apiproject.room.entity.ItemsEntity
import com.my.apiproject.support.Utils

class MainListAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mainList = arrayListOf<ItemsEntity>()

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
            Glide.with(mContext).load(mainList[position].owner!!.avatarUrl).placeholder(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.avatar_placeholder
                )
            ).into(repositoryIcon)

            repositoryTitle.text = mainList[position].owner!!.login
            repositoryFullTitle.text = mainList[position].name
            repositoryDescription.text = mainList[position].description
            starCount.text = mainList[position].stargazersCount.toString()

            if (!mainList[position].language.isNullOrEmpty()) {

                val drawable = ContextCompat.getDrawable(
                    mContext,
                    R.drawable.language_circle
                ) as GradientDrawable
                drawable.setColor(Utils.getColor(mContext, mainList[position].language!!))
                languageIcon.setImageDrawable(drawable)

                languageTitle.text = mainList[position].language!!
            } else {
                languageIcon.visibility = View.GONE
                languageTitle.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<ItemsEntity>) {
        mainList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ListItemLayBinding) :
        RecyclerView.ViewHolder(binding.root)
}