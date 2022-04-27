package com.example.myapplication.ui.fav


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AlertDialog
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
import com.example.myapplication.ui.posts.PostsFragmentDirections
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class FavFragment : Fragment(R.layout.fragment_fav) , SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentFavBinding


    private val viewModel: FavoriteViewModel by viewModel()

    private lateinit var postAdapter :PostsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavBinding.bind(view)
        setHasOptionsMenu(true)
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
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
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
                val itemToDelete = postAdapter.oldPostsList[viewHolder.adapterPosition]
                viewModel.removeFromFavorite(itemToDelete.id)
                postAdapter.notifyItemRemoved(viewHolder.adapterPosition)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.post_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmPostRemoval()
            R.id.menu_sort_a_to_z -> sortByASC()
            R.id.menu_sort_z_to_a -> sortByDESC()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }


    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.search(searchQuery).observe(this, Observer { list ->
            list?.let {
                setupRecyclerView(it)
                postAdapter.setData(it)
            }
        })
    }



    private fun confirmPostRemoval(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_ ->
            viewModel.removeAllFromFavorite()
            requireActivity().showToast("All posts have been removed successfully", 1)
        }
        builder.setNegativeButton("No"){ _,_ ->}
        builder.setTitle("Remove Posts?")
        builder.setMessage("Are you sure you want to remove all posts from favorite list?")
        builder.create().show()

    }

    private fun sortByASC(){
        viewModel.sortByASC().observe(this, Observer{
            setupRecyclerView(it)
            postAdapter.setData(it)
        })
    }


    private fun sortByDESC(){
        viewModel.sortByDESC().observe(this, Observer{
            setupRecyclerView(it)
            postAdapter.setData(it)
        })
    }



}