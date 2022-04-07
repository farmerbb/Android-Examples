package com.example.graphqlapp

import ArtistSearchQuery
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_artist_search.*
import kotlinx.android.synthetic.main.viewholder_search_results.view.*

class ArtistSearchFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_artist_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vm = ViewModelProviders.of(requireActivity())[MainViewModel::class.java]
        val adapter = SearchResultsAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAdapter(adapter)
        }

        vm.searchResults.observe(this, Observer {
            progressBar.isVisible = false
            adapter.results = it.search?.artists?.edges ?: emptyList()
        })

        searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    progressBar.isVisible = true
                    adapter.results = emptyList()
                    query?.let { vm.search(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?) = false // no-op
            })
        }
    }

    inner class SearchResultsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var results: List<ArtistSearchQuery.Edge?> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.viewholder_search_results, parent, false)
            return object: RecyclerView.ViewHolder(view) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val node = results[position]?.node
            holder.itemView.apply {
                setOnClickListener {
                    (requireActivity() as MainActivity).navigateTo(ArtistDetailsFragment().apply {
                        arguments = Bundle().apply {
                            putString("mbid", node?.mbid)
                        }
                    })
                }

                artist_name.text = node?.name
                artist_country.text = node?.area?.name

                Picasso.get()
                        .load(node?.fanArt?.thumbnails?.firstOrNull()?.url)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .into(thumbnail)
            }
        }

        override fun getItemCount() = results.size
    }
}
