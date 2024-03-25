package com.example.cocktails2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails2.databinding.FragmentListBinding
import com.example.cocktails2.ui.adapter.CocktailAdapter
import com.example.cocktails2.viewModel.MainViewModel

class ListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        Log.d("ListFragment", "onCreateView called")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCocktailList()

        binding.profileIcon.setOnClickListener {
            val navController = findNavController()
            navController.navigate(ListFragmentDirections.actionListFragmentToProfileFragment())
        }

        viewModel.cocktailList.observe(viewLifecycleOwner) {
            binding.cocktailsList.adapter = CocktailAdapter(it, viewModel)
        }
    }
}