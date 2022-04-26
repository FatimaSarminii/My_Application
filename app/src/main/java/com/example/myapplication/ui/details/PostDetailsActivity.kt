package com.example.myapplication.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.models.Comments
import com.example.myapplication.data.models.Posts
import com.example.myapplication.databinding.ActivityPostDetailsBinding
import com.example.myapplication.helper.ktx.isNetworkAvailable
import com.example.myapplication.helper.ktx.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()

    private var id = 0
    private var usernamePost = ""
    private var titlePost = ""
    private var bodyPost = ""
    private var userId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        setupView()
        loadComments()
        setupListener()

    }

    override fun onResume() {
        super.onResume()
        setupView()
        loadComments()
    }

    private fun getIntentData() {
        id = intent.getIntExtra("KEY_ID", 0)
        usernamePost = intent.getStringExtra("KEY_USERNAME") ?: ""
        titlePost = intent.getStringExtra("KEY_TITLE") ?: ""
        bodyPost = intent.getStringExtra("KEY_BODY") ?: ""
        userId = intent.getIntExtra("KEY_USER_ID",0)
    }

    private fun setupView() = with(binding) {
        title.text = titlePost
        body.text = bodyPost
        username.text = usernamePost

        viewModel.CheckFavorite(id)
        if (viewModel.isFavorite) {
            favBtn.setImageResource(R.drawable.ic_red_favorite_24)
        }

    }

    private fun setupRecyclerView(comments : List<Comments>) = with(binding) {
        commentsList.apply {
            adapter = CommentsAdapter(comments)
            layoutManager = LinearLayoutManager(this@PostDetailsActivity)
            setHasFixedSize(true)

        }
    }

    private fun loadComments() {

        if(this.isNetworkAvailable()) {

            viewModel.getCommentsById(id.toString())
            viewModel.responseComments.observe(this, Observer{
                setupRecyclerView(it)
            })

    } else this.showToast("No internet connection", 1)

    }

    private fun setupListener() = with(binding) {

        favBtn.setOnClickListener {
            viewModel.CheckFavorite(id)
            if (viewModel.isFavorite) {
                confirmPostRemoval(id)
            } else {
                var post = Posts(id,bodyPost,titlePost,userId, usernamePost )
                viewModel.addToFavorite(post)
                favBtn.setImageResource(R.drawable.ic_red_favorite_24)
            }
        }

        backBtn.setOnClickListener {
            this@PostDetailsActivity.finish()
        }
    }

    private fun confirmPostRemoval(id : Int){
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){ _,_ ->
            viewModel.removeFromFavorite(id)
            binding.favBtn.setImageResource(R.drawable.ic_white_favorite_24)
            this.showToast("The post has been removed successfully", 1)
            this@PostDetailsActivity.finish()
        }
        builder.setNegativeButton("No"){ _,_ ->}
        builder.setTitle("Remove Post?")
        builder.setMessage("Are you sure you want to remove (${titlePost}) from favorite list?")
        builder.create().show()

    }
}