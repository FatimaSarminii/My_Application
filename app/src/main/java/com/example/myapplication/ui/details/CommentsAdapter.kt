package com.example.myapplication.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.models.Comments
import com.example.myapplication.databinding.CommentsModelBinding

class CommentsAdapter (
    private var comments: List<Comments>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommentsModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = comments[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ViewHolder(
        private val binding: CommentsModelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comments) {
            binding.email.text = comment.email
            binding.comment.text = comment.body
        }
    }

        override fun getItemCount(): Int {
            return comments.size
        }

    }
