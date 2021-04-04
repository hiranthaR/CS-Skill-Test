package xyz.hirantha.csskilltest.data.remote.dto.responses


import com.google.gson.annotations.SerializedName

data class Entry(
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,
)