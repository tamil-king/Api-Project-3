package com.my.apiproject.room.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ItemsEntity(

    @NonNull
    @PrimaryKey
    var id: Int,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("full_name")
    var fullName: String? = null,

    @Embedded
    var owner: Owner? = null,

    @SerializedName("html_url")
    var htmlUrl: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("fork")
    var fork: Boolean? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    var updatedAt: String? = null,

    @SerializedName("pushed_at")
    var pushedAt: String? = null,

    @SerializedName("homepage")
    var homepage: String? = null,

    @SerializedName("size")
    var size: Int? = null,

    @SerializedName("stargazers_count")
    var stargazersCount: Int? = null,

    @SerializedName("watchers_count")
    var watchersCount: Int? = null,

    @SerializedName("language")
    var language: String? = null
)

@Entity
data class Owner(
    @SerializedName("login")
    var login: String? = null,

    @SerializedName("avatar_url")
    var avatarUrl: String? = null
)