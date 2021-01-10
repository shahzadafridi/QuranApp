package com.brikmas.quranapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brikmas.quranapp.ui.activity.AuthActivity
import com.brikmas.quranapp.ui.fragment.SignInFragment
import com.brikmas.quranapp.ui.fragment.SignUpFragment
import java.lang.IllegalArgumentException


class AuthFragmentAdapter(authActivity: AuthActivity): FragmentStateAdapter(authActivity) {

    val TOTAL_FRAGMENTS = 2

    override fun createFragment(position: Int): Fragment {
        when(position){

            0 -> return SignUpFragment()
            1 -> return SignInFragment()
            else -> {
                throw IllegalArgumentException("Invalid fragment access")
            }

        }
    }

    override fun getItemCount(): Int {
        return TOTAL_FRAGMENTS
    }
}