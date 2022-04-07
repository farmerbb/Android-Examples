package com.example.graphqlapp

import ArtistDetailsQuery
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_artist_details.*
import kotlinx.android.synthetic.main.fragment_artist_search.progressBar
import kotlinx.android.synthetic.main.fragment_artist_search.recyclerView
import kotlinx.android.synthetic.main.viewholder_details_results.view.*

class ArtistDetailsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_artist_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vm = ViewModelProviders.of(requireActivity())[MainViewModel::class.java]
        val adapter = DetailsResultsAdapter()

        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            setAdapter(adapter)
        }

        if(savedInstanceState == null)
            vm.detailsResults.value = null

        vm.detailsResults.observe(this, Observer {
            if(it == null) return@Observer

            val artist = it.lookup?.artist

            progressBar.isVisible = false
            adapter.results = artist?.releases?.edges ?: emptyList()

            artist_name.text = artist?.name

            Picasso.get()
                    .load(artist?.fanArt?.banners?.firstOrNull()?.url)
                    .fit()
                    .into(banner)
        })

        progressBar.isVisible = true
        vm.details(arguments?.getString("mbid") ?: "")
    }

    inner class DetailsResultsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var results: List<ArtistDetailsQuery.Edge?> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.viewholder_details_results, parent, false)
            return object: RecyclerView.ViewHolder(view) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val node = results[position]?.node
            holder.itemView.apply {
                album_name.text = node?.title
                album_date.text = node?.date

                Picasso.get()
                        .load(node?.coverArtArchive?.front)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .into(album_art)
            }
        }

        override fun getItemCount() = results.size
    }
}
