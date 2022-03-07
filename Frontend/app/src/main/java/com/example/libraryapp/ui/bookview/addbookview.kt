package com.example.libraryapp.ui.bookview

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.data.api.models.UploadRequestBody
import com.example.libraryapp.ui.MainActivityNew
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_addbookbiew.*
import kotlinx.coroutines.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class addbookview : AppCompatActivity(), UploadRequestBody.UploadCallback {


    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbookbiew)


        image_view.setOnClickListener {
            openImageChooser()
        }

        button_upload.setOnClickListener {
            //TODO POST request book
            uploadImage()
            val myIntent = Intent(this, MainActivityNew::class.java)
            startActivity(myIntent)

        }
    }



    fun ContentResolver.getFileName(uri: Uri): String {
        var name  = ""
        var cursor = query(uri, null, null,null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            Toast.makeText(this,"Select an Image First", Toast.LENGTH_SHORT).show()
            return
        }

        val parcelFileDescriptor =
                contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        progress_bar.progress = 0
        val body = UploadRequestBody(file, "image", this)


        ApiInterface().postImage(
                MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        body
                ),
                findViewById<EditText>(R.id.Adminname).text.toString(),
                findViewById<EditText>(R.id.Adminauthor).text.toString(),
                findViewById<EditText>(R.id.Adminauthor).text.toString(),
                 ).enqueue(object : Callback<ResponseBody> {


            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                t.printStackTrace()
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                /* This will print the response of the network call to the Logcat */
                val results = response.body()


                progress_bar.progress = 100




                CoroutineScope(Dispatchers.IO).launch {


                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()

                            } else {

                            Log.e("RETROFIT_ERROR", response.code().toString())

                        }
                    }
                }
            }
        })
    }


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    image_view.setImageURI(selectedImageUri)
                }
            }
        }
    }


    override fun onProgressUpdate(percentage : Int){
        progress_bar.progress = percentage

    }
    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
}
