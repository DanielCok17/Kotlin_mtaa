package com.example.libraryapp.data.api


import com.example.libraryapp.data.api.models.Book
import com.example.libraryapp.data.api.models.BookItem
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Part


data class BookResponse(
        var count: Int,
        var next: String,
        var previous: String,
        var results: List<Book>

)

data class BookItemsResponse(
        var results: List<BookItem>

)

data class UserLogin(
        val refresh: String,
        val access: String,

)



data class BookI(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("author")
        val author: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("photo")
        val photo: String,
        @SerializedName("category")
        val category: Array<Int>,
        @SerializedName("bookitems")
        val bookitems: String,
        @SerializedName("item")
        val item: String

)


public interface ApiInterface {

    @Headers(
            "Content-Length: ",
            "Content-Type: application/json",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNjAyNDk0LCJqdGkiOiI3ODI5ODQ1MDVjYjE0MmU1Yjc4MTc4YmEwMDFiNDE1YSIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.aQ8xamp_ka5MMWLCFSWcYVAwVFXOZiXRf584iQs2f1o",
            "Host: localhost:8000",
    )
    @PUT("/library/bookitemrentupdate/{id}")
    fun rentBook(@Body requestBody: RequestBody, @Path("id") id: String?): Call<ResponseBody>

    @Headers(
//        "Content-Length: ",
        "Content-Type: application/json",
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNjAyNDk0LCJqdGkiOiI3ODI5ODQ1MDVjYjE0MmU1Yjc4MTc4YmEwMDFiNDE1YSIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.aQ8xamp_ka5MMWLCFSWcYVAwVFXOZiXRf584iQs2f1o",
        "Host: localhost:8000",
    )
    @PUT("/library/bookitem/{id}")
    fun updateBookItem(@Body requestBody: RequestBody, @Path("id") id: String?): Call<ResponseBody>


    @Headers(
            "Content-Length: ",
            "Content-Type: application/json",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNjAyNDk0LCJqdGkiOiI3ODI5ODQ1MDVjYjE0MmU1Yjc4MTc4YmEwMDFiNDE1YSIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.aQ8xamp_ka5MMWLCFSWcYVAwVFXOZiXRf584iQs2f1o",
            "Host: localhost:8000",
    )
    @POST("/login/")
    fun loginUser(@Body requestBody: RequestBody): Call<UserLogin>



    //GET METHOD
    @Headers(
            "Content-Length: ",
            "Content-Type: application/json",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNDQyNTYwLCJqdGkiOiJjNWY0OTQ3NTgwODk0YTZhOTA5OTRlMzhjY2VmODFhYiIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.fTGHVO5tx0MLMALTYgYomlYHdTixyK5DO4gD_Og-SAA",
            "Host: localhost:8000",
    )
    @GET("library/book/")
     fun getBooks(): Call<BookResponse>


    //GET METHOD
    @Headers(
            "Content-Length: ",
            "Content-Type: text/plain",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNDQyNTYwLCJqdGkiOiJjNWY0OTQ3NTgwODk0YTZhOTA5OTRlMzhjY2VmODFhYiIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.fTGHVO5tx0MLMALTYgYomlYHdTixyK5DO4gD_Og-SAA",
            "Host: localhost:8000",
    )
    @GET("/library/bookitemfilter/{id}")
    fun getBookItems(@Path("id") id: String?): Call<BookItemsResponse>


    @Headers(
            "Content-Length: ",
            "Content-Type: text/plain",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNDQyNTYwLCJqdGkiOiJjNWY0OTQ3NTgwODk0YTZhOTA5OTRlMzhjY2VmODFhYiIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.fTGHVO5tx0MLMALTYgYomlYHdTixyK5DO4gD_Og-SAA",
            "Host: localhost:8000",
    )
    @GET("library/bookimg64/{id}")
    fun getBookImage(@Path("id") id: String?): Call<BookI>

    //PUT METHOD

    //DELETE METHOD
    @Headers(
            "Content-Length: ",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNDQyNTYwLCJqdGkiOiJjNWY0OTQ3NTgwODk0YTZhOTA5OTRlMzhjY2VmODFhYiIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.fTGHVO5tx0MLMALTYgYomlYHdTixyK5DO4gD_Og-SAA",
            "Host: localhost:8000",
    )
    @DELETE("/library/book/{id}")
    fun deleteBook(@Path("id") id: String?): Call<ResponseBody>


    @Headers(
            "Content-Length: ",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjIxNDQyNTYwLCJqdGkiOiJjNWY0OTQ3NTgwODk0YTZhOTA5OTRlMzhjY2VmODFhYiIsInVzZXJfaWQiOjEsImVtYWlsIjoiZXJpa0BtdGFhLnNrIiwiaXNfc3VwZXJ1c2VyIjp0cnVlfQ.fTGHVO5tx0MLMALTYgYomlYHdTixyK5DO4gD_Og-SAA",
            "Host: localhost:8000",
    )
    @Multipart
    @POST("/library/book/")
    fun postImage(
            @Part photo: MultipartBody.Part,
            @Part("name") name: String,
            @Part("author") author: String,
            @Part("description") description: String,

            ): Call<ResponseBody>

    companion object {

       operator fun invoke() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8000/")
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }


}