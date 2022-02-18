package com.my.apiproject.mvvm.viewmodel

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.apiproject.mvvm.Result
import com.my.apiproject.mvvm.repository.DataRepository
import com.my.apiproject.room.database.AppDatabase
import com.my.apiproject.room.entity.ItemsEntity
import kotlinx.coroutines.launch

class DataViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var repository: DataRepository
    private val itemsDao = AppDatabase.getInstance(application).itemsDao()

    fun initVieModel() {
        repository = DataRepository.getInstance(itemsDao)
        repository.initList()
    }

    fun getDataList() {
        repository.getDataList()
    }

    fun getFilterData(query: String){
        repository.getFilterData(query)
    }


    fun getDataResponse(): MutableLiveData<Result<List<ItemsEntity>>> {
        return repository.getDataResponse()
    }

}