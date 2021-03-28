package com.enzoroiz.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.enzoroiz.navigation.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        binding.txtName.text = arguments?.getString("userName")
        binding.btnSubmit.setOnClickListener {
            if (binding.edtPassword.text.isNotEmpty() && binding.edtPassword.text.length > 6) {
                val bundle = bundleOf(
                    "userName" to arguments?.getString("userName"),
                    "userPassword" to binding.edtPassword.text.toString()
                )

                binding.root.findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please type in a valid password", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }
}