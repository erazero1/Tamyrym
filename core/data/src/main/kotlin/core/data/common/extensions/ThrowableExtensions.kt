package core.data.common.extensions

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

fun Throwable.getErrorBody() : ServerError?{
    if (this is HttpException) {
        val body: ResponseBody? = response()?.errorBody()

        val errorConverter = Gson()
        return try {
            val error: ServerError? = body?.let { errorConverter.fromJson(it.string(), ServerError::class.java) }
            error
        } catch (e1: IOException) {
            null
        } catch (e: Exception) {
            null
        }
    }
    return null
}