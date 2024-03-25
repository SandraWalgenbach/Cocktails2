package com.example.cocktails2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails2.databinding.FragmentSettingsBinding
import com.example.cocktails2.viewModel.MainViewModel


class SettingsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val newUsername = binding.usernameEditText.text.toString()

            if (newUsername.isNotEmpty()) {
                viewModel.updateUsername(newUsername, viewModel.user.value?.id ?: "")
            }
        }

        binding.returnButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToProfileFragment())
        }
    }
}