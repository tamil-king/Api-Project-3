package com.my.apiproject.mvvm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.my.apiproject.room.entity.ItemsEntity


data class DataModel(
    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null,

    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = null,

    @SerializedName("items")
    @Expose
    var items: List<ItemsEntity>? = null
)
