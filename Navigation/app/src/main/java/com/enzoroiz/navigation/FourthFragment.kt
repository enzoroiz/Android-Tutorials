package com.enzoroiz.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.enzoroiz.navigation.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {
    private lateinit var binding: FragmentFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false)
        binding.lifecycleOwner = this

        arguments?.let {
            binding.isDataVisible = true
            binding.txtName.text = it.getString("userName")
            binding.txtPassword.text = it.getString("userPassword")
            binding.txtPromoCode.text = it.getString("userPromoCode")
        } ?: let {
            binding.isDataVisible = false
        }

        return binding.root
    }
}