package com.brikmas.quranapp.util

sealed class Resource<T>(
    val status: ResourceState,
    val data: T? = null,
    val message: String? = null
) {
    class Safas<T>(status: ResourceState, data: T, message: String) : Resource<T>(ResourceState.SAFAS,data,message)
    class Paras<T>(status: ResourceState, data: T, message: String) : Resource<T>(ResourceState.PARAS,data,message)
    class SignIn<T>(status: ResourceState, data: T, message: String) : Resource<T>(ResourceState.SIGN_IN,data,message)
    class SignUp<T>(status: ResourceState, data: T, message: String) : Resource<T>(ResourceState.SIGN_UP,data,message)
    class ForgetPass<T>(status: ResourceState, data: T, message: String) : Resource<T>(ResourceState.FORGET,data,message)
    class User<T>(status: ResourceState, data: T, message: String) : Resource<T>(ResourceState.USER,data,message)
    class Error<T>(status: ResourceState, data: T? = null, message: String) : Resource<T>(ResourceState.ERROR,data, message)
}
