package com.example.cocktails2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cocktails2.R
import com.example.cocktails2.databinding.FragmentDetailBinding
import com.example.cocktails2.viewModel.MainViewModel

class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cocktailDetails.observe(viewLifecycleOwner) {
            Log.e("TEST", it.toString())

            if (it != null) {
                binding.alcoholicTextView.text = "Ja"
                binding.glassTextView.text = it.glass
                binding.nameTextView.text = it.name
                binding.ingredientsTextView.text = it.ingredients.toString()
                binding.instructionTextView.text = it.instruction
                val imageUri = it.image.toUri().buildUpon().scheme("https").build()
                binding.imageView.load(imageUri) {
                    placeholder(R.drawable.ic_broken_image_24)
                    error(R.drawable.ic_broken_image_24)
                    transformations(RoundedCornersTransformation(10f))
                }
            }
        }
    }
}