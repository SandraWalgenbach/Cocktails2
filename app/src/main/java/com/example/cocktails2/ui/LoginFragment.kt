package com.example.cocktails2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails2.databinding.FragmentLoginBinding
import com.example.cocktails2.viewModel.MainViewModel

class LoginFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkAuth()

        binding.loginButton.setOnClickListener {
            val email = binding.username.text.toString()
            val password = binding.password.text.toString()

            viewModel.login(email, password)
        }

        binding.createAccount.setOnClickListener {
            val navController = findNavController()
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                val navController = findNavController()
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToListFragment())
            }
        }
    }
}