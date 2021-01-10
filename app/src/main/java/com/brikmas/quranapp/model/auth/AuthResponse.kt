package com.brikmas.quranapp.model.auth.login

data class AuthResponse(
    val `data`: Data,
    val error: String,
    val success: String
)