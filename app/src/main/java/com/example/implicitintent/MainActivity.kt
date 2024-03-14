package com.example.implicitintent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import java.io.IOException

class MainActivity : Activity() {
    private val imageView: ImageView? = null
    private var imageUri: Uri? = null
    var addurl: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addurl = findViewById(R.id.urlInput)
        addurl?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val editText = addurl
                if (editText != null && Patterns.WEB_URL.matcher(editText.text.toString()).matches()) {
                    //
                } else {
                    addurl?.setError("Invalid Url")
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            try {
                imageUri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Handle exception
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun link(view: View?) {
        val edit = findViewById<View>(R.id.urlInput) as EditText
        val result = edit.text.toString()
        val uri = Uri.parse(result)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun dial(view: View?) {
        val edit = findViewById<View>(R.id.phoneInput) as EditText
        val result = edit.text.toString()
        val uri = Uri.parse("tel:$result")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }

    companion object {
        private const val REQUEST_CODE_GALLERY = 1000
    }
}
