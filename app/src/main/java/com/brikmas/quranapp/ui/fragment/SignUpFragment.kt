package com.brikmas.quranapp.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.brikmas.quranapp.R
import com.brikmas.quranapp.util.ResourceState
import com.brikmas.quranapp.util.Utility
import com.brikmas.quranapp.viewModel.AuthViewModel
import com.example.roadi.util.ActivityStack
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    var TAG = "SignUpFragment"
    var authViewModel: AuthViewModel? = null
    var loadingProgressBar: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.1f)

        sing_up_btn.setOnClickListener {
            if (validation()){
                loadingProgressBar!!.show()
                authViewModel!!.signUpWithFirebase(sing_up_username.text.toString(),sing_up_email.text.toString(),sing_up_password.text.toString())
            }
        }

        authViewModel!!.signUpLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SIGN_UP -> {
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    requireActivity().findViewById<ViewPager2>(R.id.auth_activity_viewPager2).currentItem = 1
                    loadingProgressBar!!.dismiss()
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    Utility.printErrorLog(TAG, "ERROR : ${it.message}")
                    loadingProgressBar!!.dismiss()
                }
            }
        })

        authViewModel!!.signInLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SIGN_IN -> {
                    var email = sing_up_email.text.toString()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    //update session
                    authViewModel!!.updateSession(requireContext(),email)
                    // add user infor in firestore USER collection.
                    authViewModel!!.addUserInfo(requireContext(),email)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    Utility.printErrorLog(TAG, "ERROR : ${it.message}")
                    loadingProgressBar!!.dismiss()
                }
            }
        })

        authViewModel!!.userLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.USER -> {
                    Utility.printErrorLog(TAG, "USER : ${it.message}")
                    loadingProgressBar!!.dismiss()
                    ActivityStack.startMainActivity(requireContext())
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    Utility.printErrorLog(TAG, "ERROR : ${it.message}")
                    loadingProgressBar!!.dismiss()
                }
            }
        })
    }

    fun validation(): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(sing_up_username.text.toString())) {
            isValid = false
            sing_up_username.error = "Enter email"

        }else if (TextUtils.isEmpty(sing_up_email.text.toString())) {
            isValid = false
            sing_up_email.error = "Enter email"

        } else if (!Patterns.EMAIL_ADDRESS.matcher(sing_up_email.text.toString()).matches()) {
            isValid = false
            sing_up_email.error = "Enter valid email"

        } else if (TextUtils.isEmpty(sing_up_password.text.toString())) {
            isValid = false
            sing_up_password.error = "Enter password"
        }

        return isValid
    }
}