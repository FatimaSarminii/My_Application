package com.example.myapplication.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.models.Resource
import com.example.myapplication.databinding.PostsModelBinding

class PostsAdapter(
    private val onItemClick: (Posts) -> Unit,
    private var posts: List<Posts>
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    var oldPostsList = emptyList<Posts>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostsModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick = { position ->
            val post = posts[position]
            if (post != null) {
                onItemClick(post)
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = posts[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ViewHolder(
        private val binding: PostsModelBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    onItemClick(position)
                }
            }
        }

        fun bind(post: Posts) {
            binding.title.text = post.title
            binding.body.text = post.body
            binding.username.text = post.userName
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setData(newPostsList: List<Posts>){
        val diffUtil = PostDiffUtil(oldPostsList, newPostsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.oldPostsList = newPostsList
        diffResult.dispatchUpdatesTo(this)
    }



}