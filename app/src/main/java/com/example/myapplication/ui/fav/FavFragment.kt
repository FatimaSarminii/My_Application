package com.example.myapplication.ui.fav


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.Posts
import com.example.myapplication.databinding.FragmentFavBinding
import com.example.myapplication.helper.ktx.gone
import com.example.myapplication.helper.ktx.showToast
import com.example.myapplication.helper.ktx.visible
import com.example.myapplication.ui.details.PostDetailsActivity
import com.example.myapplication.ui.posts.PostsAdapter

class FavFragment : Fragment(R.layout.fragment_fav)  {

    private lateinit var binding: FragmentFavBinding


    private val viewModel: FavoriteViewModel by viewModel()

    private lateinit var postAdapter :PostsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        loadPosts()
    }



    private fun setupRecyclerView(posts: List<Posts>) = with(binding) {

        showEmptyListViews(posts)

        favList.apply {
            postAdapter = PostsAdapter(onItemClick = { posts ->
                onPostClick(posts)
            }, posts)
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

            swipeToDelete(favList)
        }

    }

    private fun loadPosts() {
        viewModel.responsePosts?.observe(this, Observer{
            setupRecyclerView(it)
            postAdapter.setData(it)
        })

    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = postAdapter.postsList[viewHolder.adapterPosition]
                viewModel.removeFromFavorite(itemToDelete.id)
                requireActivity().showToast("The post has been removed successfully", 1)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private fun showEmptyListViews(list: List<Posts>) = with(binding) {
        if (list.isNotEmpty()){
            noDataImageView2.gone()
            noDataTextView.gone()
        }
        else {
            noDataImageView2.visible()
            noDataTextView.visible()
        }
    }



    private fun onPostClick(post: Posts) {
        val intent = Intent(activity, PostDetailsActivity::class.java)
        intent.putExtra("KEY_ID", post.id)
        intent.putExtra("KEY_USERNAME", post.userName)
        intent.putExtra("KEY_TITLE", post.title)
        intent.putExtra("KEY_BODY", post.title)
        intent.putExtra("KEY_USER_ID", post.userId)
        startActivity(intent)

    }



}