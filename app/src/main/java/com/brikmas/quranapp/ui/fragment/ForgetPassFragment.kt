package com.brikmas.quranapp.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brikmas.quranapp.R
import com.brikmas.quranapp.util.ResourceState
import com.brikmas.quranapp.util.Utility
import com.brikmas.quranapp.viewModel.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgetPassFragment(context: Context) : BottomSheetDialogFragment() {

    val TAG = "ForgetPassFragment"
    var loadingProgressBar: KProgressHUD? = null
    var mContext: Context
    var authViewModel: AuthViewModel? = null
    init {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.fragment_forget_password,null)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.1f)

        fragment_forget_pass_back.setOnClickListener {
            this.dismiss()
        }

        forgot_btn.setOnClickListener {
            if (TextUtils.isEmpty(forgot_email.text.toString())) {
                forgot_email.error = "Enter email"
            }else{
                loadingProgressBar!!.show()
                authViewModel!!.forgetPassword(forgot_email.text.toString())
            }
        }

        authViewModel!!.forgetPassLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.FORGET -> {
                    Toast.makeText(mContext,it.message,Toast.LENGTH_SHORT).show()
                    loadingProgressBar!!.dismiss()
                    this.dismiss()
                }
                ResourceState.ERROR -> {
                    Toast.makeText(mContext,it.message,Toast.LENGTH_SHORT).show()
                    Utility.printErrorLog(TAG, "ERROR : ${it.message}")
                }
            }
        })

    }

    override fun getTheme(): Int  = R.style.SettingFragmentTheme

    fun fullScreenBottomSheet(){
        var dialog = getDialog()
        if (dialog != null) {
            var bottomSheet: View  = dialog.findViewById(R.id.design_bottom_sheet)
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        val params = (requireView().parent as View).layoutParams as CoordinatorLayout.LayoutParams
        (params.behavior as BottomSheetBehavior<*>?)!!.state = BottomSheetBehavior.STATE_EXPANDED
        (params.behavior as BottomSheetBehavior<*>?)!!.peekHeight = 0
        (params.behavior as BottomSheetBehavior<*>?)!!.setDraggable(false);
        ((requireView().parent) as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onStart() {
        super.onStart()
        fullScreenBottomSheet()
    }

}