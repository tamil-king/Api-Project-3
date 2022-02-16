package com.my.apiproject.mvvm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.my.apiproject.mvvm.model.DataModel
import com.my.apiproject.mvvm.model.Item
import com.my.apiproject.retrofit.RetrofitClient
import com.my.apiproject.support.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DataRepository {

    private val mutableLiveData = MutableLiveData<List<Item>>()


    fun initList() {
        mutableLiveData.value = LinkedList<Item>()
    }


    fun getDataList() {

        val map = mapOf(
            Pair("q", "square"),
            Pair("sort", "stars"),
            Pair("order", "desc"),
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

                Log.i("dragon_test", "Response Success")
                Log.i("dragon_test", "" + response.body()!!.totalCount!!)
                Log.i("dragon_test", "" + response.body()!!.incompleteResults!!)

                mutableLiveData.value = response.body()!!.items!!

                Log.i(
                    "dragon_test",
                    "successResponse : $successResponse"
                )
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Log.i("dragon_test", "failed : ${t.message}")
            }

        })


    }

    fun getDataResponse() = mutableLiveData


    companion object {

        private var instance: DataRepository? = null
        private val LOCK = Any()

        fun getInstance(): DataRepository = (instance ?: synchronized(LOCK) {
            instance = DataRepository()
            instance
        })!!

    }

}