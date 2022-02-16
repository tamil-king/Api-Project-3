package com.my.apiproject.retrofit

import com.my.apiproject.mvvm.model.DataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("search/repositories")
    fun getData(@QueryMap map: Map<String, String>): Call<DataModel>
}