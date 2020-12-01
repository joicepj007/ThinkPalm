package com.android.codingtesttp.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.android.codingtesttp.R
import com.android.codingtesttp.databinding.MoviesListItemBinding
import com.android.codingtesttp.movie.model.ContentRV
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_list_item.view.*
import java.util.*


class MoviesListAdapter(var moviesList: MutableList<ContentRV>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var mList: MutableList<ContentRV>


    fun updateList(moviesList: MutableList<ContentRV>) {
        this.mList = moviesList
        notifyItemInserted(this.itemCount)
    }

    init {
        mList = moviesList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = MoviesListItemBinding.inflate(inflater, parent, false)
        return  MovieListViewHolder(dataBinding)
        }



    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val movieViewHolder = holder as MovieListViewHolder
                movieViewHolder.bind(mList[position])
    }



    inner class MovieListViewHolder(private val bindingItem : MoviesListItemBinding) : RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(itemData: ContentRV) {
            mList.size
            val avatarImage = itemView.iv_movie
            bindingItem.itemData=itemData
            bindingItem.executePendingBindings()

            when (itemData.posterImage) {
                "poster1.jpg" -> Picasso.get().load(R.drawable.poster1).into(avatarImage)
                "poster2.jpg" -> Picasso.get().load(R.drawable.poster2).into(avatarImage)
                "poster3.jpg" -> Picasso.get().load(R.drawable.poster3).into(avatarImage)
                "poster4.jpg" -> Picasso.get().load(R.drawable.poster4).into(avatarImage)
                "poster5.jpg" -> Picasso.get().load(R.drawable.poster5).into(avatarImage)
                "poster6.jpg" -> Picasso.get().load(R.drawable.poster6).into(avatarImage)
                "poster7.jpg" -> Picasso.get().load(R.drawable.poster7).into(avatarImage)
                "poster8.jpg" -> Picasso.get().load(R.drawable.poster8).into(avatarImage)
                "poster9.jpg" -> Picasso.get().load(R.drawable.poster9).into(avatarImage)
                else ->  Picasso.get().load(R.drawable.placeholder_for_missing_posters).into(avatarImage)
            }
        }
    }

    //function to filter the searchview
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                mList = if (charSearch.isEmpty()) {
                    moviesList
                } else {
                    val resultList: MutableList<ContentRV> = mutableListOf()
                    for (row in moviesList) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = mList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mList = results?.values as MutableList<ContentRV>
                notifyDataSetChanged()
            }

        }
    }



}