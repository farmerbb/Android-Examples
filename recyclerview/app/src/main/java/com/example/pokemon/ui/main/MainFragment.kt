package com.example.pokemon.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.models.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.row.view.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val list = viewModel.getPokemon()

        val recyclerView = view.pokemon_list

        recyclerView.adapter = PokemonAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    inner class PokemonAdapter(private val list: List<Pokemon>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(requireContext())
            val view = inflater.inflate(R.layout.row, parent, false)

            return object: RecyclerView.ViewHolder(view) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val row = holder.itemView
            val pokemon = list[position]

            row.pokemon_name.text = pokemon.name
            row.pokemon_number.text = pokemon.number
            row.pokemon_types.text = pokemon.getTypes()

            Picasso.get()
                .load(pokemon.image)
                .into(row.pokemon_image)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}
