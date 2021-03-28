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
import com.enzoroiz.navigation.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false)
        arguments?.let {
            binding.txtName.text = it.getString("userName")
            binding.txtPassword.text = it.getString("userPassword")
        }

        binding.btnSubmit.setOnClickListener {
            if (binding.edtPromoCode.text.isNotEmpty()) {
                val bundle = bundleOf(
                    "userName" to binding.txtName.text.toString(),
                    "userPassword" to binding.txtPassword.text.toString(),
                    "userPromoCode" to binding.edtPromoCode.text.toString()
                )

                binding.root.findNavController().navigate(R.id.action_thirdFragment_to_fourthFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "You must have a promo code", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }
}