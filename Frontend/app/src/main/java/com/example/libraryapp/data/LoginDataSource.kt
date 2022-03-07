package com.example.libraryapp.data

import android.util.Log
import com.example.libraryapp.CellBookItem
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.data.model.LoggedInUser
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class  LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {

            val (access, refresh) = getLogin(username, password)

            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), username, access, refresh)
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }


    fun getLogin(username: String, password: String) : Pair<String,String> {

        // Create Service
        val service = ApiInterface.invoke()
        var access = ""
        var refresh = ""
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("email", username)
        jsonObject.put("password", password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.loginUser(requestBody).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {


                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(response.body())

                        Log.d("Pretty login: ", prettyJson)
                        val items = response.body()
                        if (items != null) {


                                access = items.access ?: "N/A"
                                refresh = items.refresh ?: "N/A"


                        }
                        else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                        }
                    }
                }
            }
        }

        return Pair(access , refresh)

    }
}