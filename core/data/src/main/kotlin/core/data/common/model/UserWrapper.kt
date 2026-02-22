package core.data.common.model

import com.google.gson.annotations.SerializedName

data class UserWrapper(
    @SerializedName("user")
    val userDTO: UserDTO
)
