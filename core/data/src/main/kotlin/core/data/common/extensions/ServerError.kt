package core.data.common.extensions

import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("details") val details: String,
    @SerializedName("error") val error: String? = null,
)