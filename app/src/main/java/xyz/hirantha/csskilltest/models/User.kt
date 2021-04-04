package xyz.hirantha.csskilltest.models


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @SerializedName("address")
    @Embedded(prefix = "address_")
    val address: Address,
    @SerializedName("company")
    @Embedded(prefix = "company_")
    val company: Company,
    @SerializedName("email")
    val email: String,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: String,
    var avatar:String?
)