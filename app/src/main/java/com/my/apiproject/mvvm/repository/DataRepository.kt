package com.my.apiproject.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.my.apiproject.mvvm.Result
import com.my.apiproject.mvvm.model.DataModel
import com.my.apiproject.retrofit.RetrofitClient
import com.my.apiproject.room.dao.ItemsDao
import com.my.apiproject.room.entity.ItemsEntity
import com.my.apiproject.support.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(private val itemsDao: ItemsDao) {

    private val mutableLiveData = MutableLiveData<Result<List<ItemsEntity>>>()


    fun initList() {
        mutableLiveData.value = Result.loading(null)
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun getDataList() {

        val map = mapOf(
            Pair("q", "square"),
            Pair("page", Constants.pageCount.toString())
        )

        val request = RetrofitClient.getInstance().getApiService()
            .getData(map)

        request.enqueue(object : Callback<DataModel> {

            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {

                val gson = Gson()
                val successResponse = gson.toJson(response.body())

                if (response.isSuccessful) {

                    if (response.body() != null) {
                        Log.i("dragon_test", "Response Success")
                        Log.i("dragon_test", "" + response.body()!!.totalCount!!)
                        Log.i("dragon_test", "" + response.body()!!.incompleteResults!!)

                        mutableLiveData.value = Result.success(response.body()!!.items!!)

                        GlobalScope.launch {
                            itemsDao.insertData(response.body()!!.items!!)
                        }

                    } else {
                        GlobalScope.launch {
                            if (itemsDao.getDataCount()) {
                                mutableLiveData.postValue(Result.success(itemsDao.getDataFromDB()))
                            } else {
                                mutableLiveData.postValue(
                                    Result.error(
                                        Constants.somethingWrong,
                                        null
                                    )
                                )
                            }
                        }
                    }

                } else {

                    GlobalScope.launch {
                        if (itemsDao.getDataCount()) {
                            mutableLiveData.postValue(Result.success(itemsDao.getDataFromDB()))
                        } else {
                            mutableLiveData.postValue(Result.error(Constants.somethingWrong, null))
                        }
                    }
                }

                Log.i(
                    "dragon_test",
                    "successResponse : $successResponse"
                )
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Log.i("dragon_test", "failed : ${t.message}")

                GlobalScope.launch {

                    if (itemsDao.getDataCount()) {
                        mutableLiveData.postValue(Result.success(itemsDao.getDataFromDB()))
                    } else {
                        mutableLiveData.postValue(Result.error(Constants.noInternetCon, null))
                    }
                }
            }

        })


    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFilterData(query: String) {

        GlobalScope.launch {
            mutableLiveData.postValue(Result.success(itemsDao.getFilterData(query)))
        }
    }

    fun getDataResponse() = mutableLiveData


    companion object {

        private var instance: DataRepository? = null
        private val LOCK = Any()

        fun getInstance(itemsDao: ItemsDao): DataRepository = (instance ?: synchronized(LOCK) {
            instance = DataRepository(itemsDao)
            instance
        })!!

    }

}