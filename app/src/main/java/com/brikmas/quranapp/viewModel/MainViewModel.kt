package com.brikmas.quranapp.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.brikmas.quranapp.model.Session
import com.brikmas.quranapp.repository.RemoteRepository
import com.brikmas.quranapp.repository.SharedPrefRepository
import com.brikmas.quranapp.util.Resource

class MainViewModel: ViewModel(){

    var remoteRepository: RemoteRepository = RemoteRepository.provideRemoteRepository()
    val safaLiveData = MutableLiveData<Resource<List<Safa>>>()

    fun getParas(): MutableLiveData<Resource<List<Para>>>{
        return remoteRepository.loadParas()
    }

    fun getSafas(paraId: String){
        remoteRepository.getSafasOfPara(safaLiveData,paraId)
    }

    fun getSession(context: Context): Session?{
        var sharedPrefRepository: SharedPrefRepository = SharedPrefRepository.provideSharedRepository(context)
        return sharedPrefRepository.getSession()
    }

    fun updateSession(context: Context, session: Session){
        var sharedPrefRepository: SharedPrefRepository = SharedPrefRepository.provideSharedRepository(context)
        sharedPrefRepository.updateSession(session)
    }
}