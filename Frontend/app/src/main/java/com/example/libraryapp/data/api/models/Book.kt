package com.example.libraryapp.data.api.models

import android.os.Handler
import android.os.Looper
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream






data class Book(

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
)




class UploadRequestBody(
        private val file: File,
        private val contentType: String,
        private val callback: UploadCallback
) : RequestBody() {


    interface UploadCallback {
        fun onProgressUpdate(percentage: Int)
    }

    override fun contentType() = "$contentType/*".toMediaTypeOrNull()


    override fun contentLength() = file.length()

    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L
        fileInputStream.use { inputStream ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())

            while (inputStream.read(buffer).also { read = it } != -1) {
                handler.post(ProgressUpdater(uploaded, length))
                uploaded += read
                sink.write(buffer, 0, read)
            }
        }

    }

    inner class ProgressUpdater(
            private val uploaded: Long,
            private val total: Long
    ) : Runnable {
        override fun run() {
            callback.onProgressUpdate((100 * uploaded / total).toInt())
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}






