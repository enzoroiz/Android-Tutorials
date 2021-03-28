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
import com.enzoroiz.navigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.btnSubmit.setOnClickListener {
            if (binding.edtText.text.isNotEmpty()) {
                val bundle = bundleOf("userName" to binding.edtText.text.toString())
                it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please enter a text", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_fourthFragment)
        }

        return binding.root
    }
}