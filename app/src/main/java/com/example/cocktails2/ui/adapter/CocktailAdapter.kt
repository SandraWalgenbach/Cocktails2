package com.example.cocktails2.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cocktails2.R
import com.example.cocktails2.databinding.CocktailitemBinding
import com.example.cocktails2.remote.models.CocktailPreview
import com.example.cocktails2.ui.DetailFragment
import com.example.cocktails2.ui.ListFragmentDirections
import com.example.cocktails2.viewModel.MainViewModel

class CocktailAdapter(
    private val cocktailList: List<CocktailPreview>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    inner class CocktailViewHolder(
        val binding: CocktailitemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = CocktailitemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = cocktailList[position]

        val imageUri = cocktail.strDrinkThumb.toUri().buildUpon().scheme("https").build()

        holder.binding.cocktailPic.load(imageUri) {
            placeholder(R.drawable.ic_broken_image_24)
            error(R.drawable.ic_broken_image_24)
            transformations(RoundedCornersTransformation(10f))
        }
        holder.binding.cocktailName.text = cocktail.strDrink

        //fehler..
        // getestet ob beim navigieren in das DetailFragment überhaupt die onCreateView bzw. onViewCreated callt
        // mit einem Log aber es kommt nichtmal zu dem Schritt, daher kann das Problem nicht an dem asynchronen Task
        // ist da beide Views unabhängig von diesem gecallt werden müssten
        holder.itemView.setOnClickListener {
            viewModel.getCocktailDetails(cocktail.idDrink)
            val navController = holder.itemView.findNavController()
            navController.navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
        }
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }
}