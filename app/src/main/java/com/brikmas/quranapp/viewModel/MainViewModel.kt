package com.brikmas.quranapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.brikmas.quranapp.repository.RemoteRepository
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
}