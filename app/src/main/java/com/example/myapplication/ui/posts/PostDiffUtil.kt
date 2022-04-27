package com.example.myapplication.ui.posts

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.data.models.Posts

class PostDiffUtil(
    private val oldList: List<Posts>,
    private val newList: List<Posts>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> { false
            }
            oldList[oldItemPosition].userId != newList[newItemPosition].userId -> { false
            }
            oldList[oldItemPosition].title != newList[newItemPosition].title -> { false
            }
            oldList[oldItemPosition].body != newList[newItemPosition].body -> { false
            }
            else -> true
        }
    }


}