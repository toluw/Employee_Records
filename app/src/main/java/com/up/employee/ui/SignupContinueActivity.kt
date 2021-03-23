package com.up.employee.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.up.employee.R
import com.up.employee.util.DataState
import com.up.employee.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signup_continue.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class SignupContinueActivity : AppCompatActivity() {
    lateinit var relative: RelativeLayout
    lateinit var dialog: ProgressBar
    lateinit var imageView: ImageView
    lateinit var password1: EditText
    lateinit var username1: EditText
    lateinit var password: String
    lateinit var username: String
    lateinit var image: String
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var gender: String
    lateinit var dob: String
    lateinit var address: String
    lateinit var country: String
    private val IMAGE_REQUEST = 2
    private  val employeeViewModel: EmployeeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_continue)
        initialize()
        a.setOnClickListener { passportClicked() }
        b.setOnClickListener { submitClicked() }
        val mData: Bundle? = intent.extras
        if(mData != null){
            firstname = mData.getString("firstname").toString()
            lastname = mData.getString("lastname").toString()
            gender = mData.getString("gender").toString()
            dob = mData.getString("dob").toString()
            address = mData.getString("address").toString()
            country = mData.getString("country").toString()
        }

    }

    fun initialize(){
        relative = findViewById<RelativeLayout>(R.id.relative)
        dialog = findViewById<ProgressBar>(R.id.dialog)
        imageView = findViewById<ImageView>(R.id.imageView)
        password1 = findViewById<EditText>(R.id.password1)
        username1 = findViewById<EditText>(R.id.username1)
    }

    //Upload passport
   fun passportClicked(){
      username = username1.text.toString().trim()
       password = password1.text.toString().trim()
       if(username.equals("")){
           val a: Toast = Toast.makeText(applicationContext, "Enter Username", Toast.LENGTH_LONG)
           a.setGravity(Gravity.CENTER, 0, 0)
           a.show()
       }else if(password.equals("")){
           val a: Toast = Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_LONG)
           a.setGravity(Gravity.CENTER, 0, 0)
           a.show()
       }else{
           val intent = Intent()
           intent.type = "image/jpeg"
           intent.action = Intent.ACTION_GET_CONTENT
           startActivityForResult(intent, IMAGE_REQUEST)
       }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
           val uri = data!!.data
            imageView.setImageURI(uri)
            relative.visibility = View.VISIBLE

        }
    }

    fun submitClicked(){
        val image = (imageView.getDrawable() as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
       val encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
       employeeViewModel.admin_signup(firstname, lastname, gender, dob, address, country, username, password, encodedImage).observe(this, Observer {
           when (it) {
               is DataState.Success -> {
                   dialog.visibility = View.GONE
                   val data = it.data
                   val status = data.status
                   val response = data.response
                   if (status.equals("yes")) {
                       val intent = Intent(applicationContext, SigninActivity::class.java)
                       startActivity(intent)
                   } else if (status.equals("no")) {
                       val a: Toast = Toast.makeText(applicationContext, response, Toast.LENGTH_LONG)
                       a.setGravity(Gravity.CENTER, 0, 0)
                       a.show()
                   }
               }
               is DataState.Loading -> {
                   dialog.visibility = View.VISIBLE
               }
               is DataState.Error -> {
                   dialog.visibility = View.GONE
                   val a: Toast = Toast.makeText(applicationContext, "A connction error occured", Toast.LENGTH_LONG)
                   a.setGravity(Gravity.CENTER, 0, 0)
                   a.show()

               }
           }
       })
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, SigninActivity::class.java)
        startActivity(intent)
    }
}


