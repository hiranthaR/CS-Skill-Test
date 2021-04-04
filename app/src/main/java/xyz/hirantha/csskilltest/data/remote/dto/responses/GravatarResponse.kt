package xyz.hirantha.csskilltest.data.remote.dto.responses


import com.google.gson.annotations.SerializedName

data class GravatarResponse(
    @SerializedName("entry")
    val entry: List<Entry>
)