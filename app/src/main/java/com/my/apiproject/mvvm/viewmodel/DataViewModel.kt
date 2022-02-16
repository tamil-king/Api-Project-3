package com.my.apiproject.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.my.apiproject.mvvm.model.Item
import com.my.apiproject.mvvm.repository.DataRepository

class DataViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var repository: DataRepository


    fun initVieModel() {
        repository = DataRepository.getInstance()
        repository.initList()
    }

    fun getDataList() {
        repository.getDataList()
    }

    fun getDataResponse(): MutableLiveData<List<Item>> {
        return repository.getDataResponse()
    }

}