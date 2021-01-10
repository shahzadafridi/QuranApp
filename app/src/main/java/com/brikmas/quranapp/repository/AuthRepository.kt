package com.brikmas.quranapp.repository

import androidx.lifecycle.MutableLiveData
import com.brikmas.quranapp.model.User
import com.brikmas.quranapp.model.auth.login.AuthResponse
import com.brikmas.quranapp.model.auth.login.Data
import com.brikmas.quranapp.util.Constants
import com.brikmas.quranapp.util.Resource
import com.brikmas.quranapp.util.ResourceState
import com.brikmas.quranapp.util.Utility
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class AuthRepository {

    var TAG = "AuthRepository"

    var auth: FirebaseAuth? = null
    var gson: Gson? = null

    init {
        auth = FirebaseAuth.getInstance()
        gson = Gson()
    }

    companion object {
        fun provideAuthRepository(): AuthRepository {
            return AuthRepository()
        }
    }

    fun signInFirebase(
        email: String,
        password: String,
        signInLiveData: MutableLiveData<Resource<AuthResponse>>
    ) {
        auth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                var data = Data(email,Constants.AUTH_APP,"")
                var loginResponse = AuthResponse(data,"","Successfully login")
                signInLiveData.value = Resource.SignIn(ResourceState.SIGN_IN,loginResponse!!,"Successfully login")
            }else{
                signInLiveData.value = Resource.Error(ResourceState.ERROR,null,it.exception!!.message!!)
            }
        }.addOnFailureListener {
            signInLiveData.value = Resource.Error(ResourceState.ERROR,null,it.message!!)
        }
    }

    fun signUpFirebase(
        username: String,
        email: String,
        password: String,
        signUpLiveData: MutableLiveData<Resource<AuthResponse>>
    ) {
        auth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                var data = Data(email,"",Constants.AUTH_APP)
                var registerResponse = AuthResponse(data,"","Successfully Register")
                signUpLiveData.value = Resource.SignUp(ResourceState.SIGN_UP,registerResponse,"Successfully Register")
            }else{
                signUpLiveData.value = Resource.Error(ResourceState.ERROR,null,it.exception!!.message!!)
            }
        }.addOnFailureListener {
            signUpLiveData.value = Resource.Error(ResourceState.ERROR,null,it.message!!)
        }
    }

    fun forgetPassword(email: String, forgetPassLiveData: MutableLiveData<Resource<String>>) {
        auth!!.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                forgetPassLiveData.value = Resource.ForgetPass(ResourceState.FORGET,"Reset password instructions has sent to your email","Reset password instructions has sent to your email")
            }else{
                forgetPassLiveData.value = Resource.Error(ResourceState.ERROR,null,it.exception!!.message!!)
            }
        }.addOnFailureListener {
            forgetPassLiveData.value = Resource.Error(ResourceState.ERROR,null,it.message!!)
        }
    }

    fun addUserInfo(user: User, userLiveData: MutableLiveData<Resource<User>>){
        var email = user.email
        var email_name =  email.substring(0,email.indexOf("@"))
        var ref = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_USER).document(email_name)
        user.id = ref.id
        ref.set(user).addOnCompleteListener({
            if (it.isSuccessful){
                userLiveData.value = Resource.User(ResourceState.USER, user, "User info added successfully")
            }else{
                userLiveData.value = Resource.Error(ResourceState.ERROR, null, "User info addedunsuccessfully.")
            }
        }).addOnFailureListener({
            userLiveData.value = Resource.Error(ResourceState.ERROR, null, it.message!!)
        })
    }

}