package com.example.cocktails2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails2.databinding.FragmentRegisterBinding
import com.example.cocktails2.viewModel.MainViewModel


class RegisterFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val username = binding.username.text.toString()

            viewModel.register(email, password, username)
        }

        binding.login.setOnClickListener {
            val navController = findNavController()
            navController.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                val navController = findNavController()
                navController.navigate(RegisterFragmentDirections.actionRegisterFragmentToListFragment())
            }
        }
    }
}