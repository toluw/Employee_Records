package com.up.employee.ui

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.up.employee.R
import com.up.employee.util.DataState
import com.up.employee.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signin.*

@AndroidEntryPoint
class SigninActivity : AppCompatActivity() {

    lateinit var password1: EditText
    lateinit var username1: EditText
    lateinit var password: String
    lateinit var username: String
    lateinit var dialog: ProgressBar
    private  val employeeViewModel: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        initialize()
         a.setOnClickListener { signinClicked() }


    }

    fun initialize(){
        dialog = findViewById<ProgressBar>(R.id.dialog)
        password1 = findViewById<EditText>(R.id.password1)
        username1 = findViewById<EditText>(R.id.username1)
    }

    fun signinClicked(){
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
            signin()
        }
    }

    fun signin(){
       employeeViewModel.admin_login(username, password).observe(this, Observer {
           when (it) {
               is DataState.Success -> {
                   val data = it.data
                   val status = data.status
                   val firstname = data.firstname
                   val response = data.response
                   if (status.equals("yes")) {
                       val sab = getSharedPreferences("userInfo", MODE_PRIVATE)
                       val ed = sab.edit()
                       ed.putString("firstname", firstname)
                       ed.apply()
                       val intent = Intent(applicationContext, EmployeeActivity::class.java)
                       startActivity(intent)
                   } else if (status.equals("no")) {
                       val a: Toast = Toast.makeText(applicationContext, response, Toast.LENGTH_LONG)
                       a.setGravity(Gravity.CENTER, 0, 0)
                       a.show()
                   }
               }
               is DataState.Error -> {
                   dialog.visibility = View.GONE
                   val a: Toast = Toast.makeText(applicationContext, "A connction error occured", Toast.LENGTH_LONG)
                   a.setGravity(Gravity.CENTER, 0, 0)
                   a.show()
               }
               is DataState.Loading -> {
                   dialog.visibility = View.VISIBLE
               }
           }
       })
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}