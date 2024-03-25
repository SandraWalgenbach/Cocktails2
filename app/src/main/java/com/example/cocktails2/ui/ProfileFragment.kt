package com.example.cocktails2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails2.databinding.FragmentProfileBinding
import com.example.cocktails2.viewModel.MainViewModel

class ProfileFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.email.text = viewModel.user.value?.email
        binding.username.text = viewModel.user.value?.username

        binding.settingsButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
        }

        binding.returnButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToListFragment())
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            val navController = findNavController()
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }
}