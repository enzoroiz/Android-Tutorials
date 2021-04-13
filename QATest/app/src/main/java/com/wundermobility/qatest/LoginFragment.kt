package com.wundermobility.qatest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    companion object {
        private const val LOADING_TIME_IN_MS = 2000L
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener {
            login(edtLoginEmail.text.toString(), edtLoginPassword.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        progressBarLogin.visibility = View.VISIBLE

        handler.postDelayed({
            progressBarLogin.visibility = View.GONE
            if (email == "qatest@wundermobility.com" && password == "12345678") {
                findNavController().navigate(R.id.action_loginFragment_to_mapFragment)
            } else {
                txtLoginError.visibility = View.VISIBLE
            }
        }, LOADING_TIME_IN_MS)
    }
}