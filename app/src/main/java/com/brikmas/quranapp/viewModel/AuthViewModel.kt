package com.brikmas.quranapp.viewModel

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brikmas.quranapp.model.Session
import com.brikmas.quranapp.model.User
import com.brikmas.quranapp.model.auth.login.AuthResponse
import com.brikmas.quranapp.repository.AuthRepository
import com.brikmas.quranapp.repository.SharedPrefRepository
import com.brikmas.quranapp.util.Resource
import com.brikmas.quranapp.util.Utility

class AuthViewModel: ViewModel(){

    var authRepository: AuthRepository = AuthRepository.provideAuthRepository()

    val signInLiveData = MutableLiveData<Resource<AuthResponse>>()
    val signUpLiveData = MutableLiveData<Resource<AuthResponse>>()
    val forgetPassLiveData = MutableLiveData<Resource<String>>()
    val userLiveData = MutableLiveData<Resource<User>>()

    fun signInWithFirebase(email: String, password: String){
         authRepository.signInFirebase(email,password,signInLiveData)
     }

    fun signUpWithFirebase(username: String, email: String, password: String){
        authRepository.signUpFirebase(username,email,password,signUpLiveData)
    }

    fun forgetPassword(email: String){
        authRepository.forgetPassword(email,forgetPassLiveData)
    }

    fun addUserInfo(context: Context,email: String){
        var deviceId = Utility.getDeviceId(context)
        if(TextUtils.isEmpty(deviceId)){
            deviceId = ""
        }
        var user = User()
        user.deviceId = deviceId!!
        user.status = "active"
        user.email = email
        authRepository.addUserInfo(user,userLiveData)
    }

    fun updateSession(context: Context, email: String){
        var deviceId = Utility.getDeviceId(context)
        if(TextUtils.isEmpty(deviceId)){
            deviceId = ""
        }
        // Update Session - to use data throught app without getting from firebase.
        var session = Session()
        session.deviceId = deviceId!!
        session.email = email
        session.status = "active"
        session.isLogin = true

        var sharedPrefRepository: SharedPrefRepository = SharedPrefRepository.provideSharedRepository(context)
        sharedPrefRepository.updateSession(session)
    }

    fun getSession(context: Context): Session?{
        var sharedPrefRepository: SharedPrefRepository = SharedPrefRepository.provideSharedRepository(context)
        return sharedPrefRepository.getSession()
    }

}