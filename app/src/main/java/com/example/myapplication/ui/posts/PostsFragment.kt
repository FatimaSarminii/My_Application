package com.example.myapplication.ui.posts

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.models.Status
import com.example.myapplication.databinding.FragmentPostsBinding
import com.example.myapplication.helper.ktx.*
import com.example.myapplication.ui.details.PostDetailsActivity
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class PostsFragment : Fragment(R.layout.fragment_posts) {

    private lateinit var binding: FragmentPostsBinding

    private val viewModel: PostsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostsBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        loadPosts()
    }


    private fun setupRecyclerView(posts: List<Posts>) = with(binding) {

        postsList.apply {
            adapter = PostsAdapter(onItemClick = { posts ->
                onPostClick(posts) }, posts)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadPosts(){

        if(requireContext().isNetworkAvailable()) {

            viewModel.responsePosts.observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        handleSuccessStatus()
                        it.data?.let { posts -> setupRecyclerView(posts) }

                    }
                    Status.LOADING -> {
                        handleLoadingStatus()
                    }
                    Status.ERROR -> {
                        handleErrorStatus()
                        requireContext().showToast(it.message.toString(), 10)
                    }
                }
            })
        }else requireContext().showToast("No internet connection", 1)
    }


    private fun handleSuccessStatus() = with(binding) {
        postsList.visible()
        progressBar.gone()
        noDataImageView2.gone()
        noDataTextView.gone()
    }

    private fun handleLoadingStatus() = with(binding) {
        postsList.gone()
        progressBar.visible()
        noDataImageView2.gone()
        noDataTextView.gone()
    }

    private fun handleErrorStatus() = with(binding) {
        postsList.gone()
        progressBar.gone()
        noDataImageView2.visible()
        noDataTextView.visible()
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
