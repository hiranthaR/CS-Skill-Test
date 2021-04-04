package xyz.hirantha.csskilltest.data.remote.dto.requests

import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("userId")
    val userId: Int = 1
)