package com.android.codingtesttp.movie.view.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.codingtesttp.R
import com.android.codingtesttp.movie.adapter.MoviesListAdapter
import com.android.codingtesttp.movie.model.ContentRV
import com.android.codingtesttp.movie.viewmodel.MoviesListViewModel
import com.android.codingtesttp.utils.OnLoadMoreListener
import com.android.codingtesttp.utils.RecyclerViewLoadMoreScroll
import kotlinx.android.synthetic.main.movie_list_fragment.*


class MovieFragment : Fragment() {

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var searchView: SearchView
    private lateinit var moviesAdapter: MoviesListAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var moviesViewManager: RecyclerView.LayoutManager
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    private var pageNo:Int = 1
    private var totalItemCount:Int?=null
    private lateinit var movieslist : MutableList<ContentRV>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)
        setRVLayoutManager()
        setupObservers()
        setRVScrollListener()
        // movies list call with initial page number
        viewModel.getMovies(pageNo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Gets the Movies list and add it to adapter
     */
    private fun setupObservers() {
        viewModel.moviesList.observe(this,
            Observer { popularMovies ->
                if (popularMovies.size>0) {
                    movieslist = popularMovies
                    moviesAdapter =
                        MoviesListAdapter(
                            popularMovies
                        )
                    movies_recycler_view.adapter = moviesAdapter
                    moviesAdapter.updateList(movieslist)
                    totalItemCount = viewModel.pageInfo.totalCount
                }
            })

    }


    /**
     * setting up recyclerview LayoutManager
     */
    private fun setRVLayoutManager() {
        val columns = resources.getInteger(R.integer.gallery_columns)
        moviesViewManager = GridLayoutManager(this.context, columns)
        movies_recycler_view.layoutManager = moviesViewManager
        movies_recycler_view.setHasFixedSize(true)
    }


    /**
     * setting up recyclerview ScrollListener to get the last position of item to update the lists
     */
    private fun setRVScrollListener() {
        scrollListener =
            RecyclerViewLoadMoreScroll(
                moviesViewManager as GridLayoutManager
            )
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData(++pageNo)
            }
        })

        movies_recycler_view.addOnScrollListener(scrollListener)
    }
    /**
     * function to update the list with page index
     */
    private fun loadMoreData(pageNo:Int) {
        if (pageNo<=3)  viewModel.getMovies(pageNo)

        val currentItems = moviesAdapter.itemCount
        if (currentItems != totalItemCount)
        {
            Handler().postDelayed({
                moviesAdapter.updateList(movieslist)
                scrollListener.setLoaded()
                //Update the recyclerView in the main thread
                movies_recycler_view.post {
                    moviesAdapter.notifyDataSetChanged()
                }
            }, 100)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.xml, menu)
        val item = menu.findItem(R.id.action_search)
        if (item != null) {
             searchView = MenuItemCompat.getActionView(item) as SearchView

            val searchPlate =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchPlate.hint = "Search"
            val searchPlateView: View =
                searchView.findViewById(androidx.appcompat.R.id.search_plate)
            searchPlateView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )
            searchView.maxWidth = Integer.MAX_VALUE
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let {
                        //show results only if the query contains 3 or more characters
                        if (query.length>2)
                            moviesAdapter.filter.filter(query)
                        else if (query.isEmpty()) {
                            moviesAdapter.updateList(movieslist)
                            moviesAdapter.notifyDataSetChanged()
                        }
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.length>2) moviesAdapter.filter.filter(query) else Toast.makeText(requireContext(),getString(
                                            R.string.str_search_list),Toast.LENGTH_LONG).show()

                    return false
                }
            }
            item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }
                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    moviesAdapter.updateList(movieslist)
                    moviesAdapter.notifyDataSetChanged()
                    return true
                }
            })
            searchView.setOnQueryTextListener(queryTextListener)
            val searchManager =
                requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                return false
            else -> {
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }



}



