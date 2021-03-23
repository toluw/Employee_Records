package com.up.employee.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.up.employee.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_employee.*
import kotlinx.android.synthetic.main.activity_create_employee.ar
import kotlinx.android.synthetic.main.activity_create_employee.gender1
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.a


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val sharePref = getSharedPreferences("userInfo", MODE_PRIVATE)
        val firstname = sharePref.getString("firstname", "")

        if (firstname  != "") {
            val intent = Intent(applicationContext, EmployeeActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_main)
        a.setOnClickListener { registerClicked() }
        sign.setOnClickListener { signinClicked() }



    }



    fun registerClicked(){
        val intent = Intent(applicationContext, SignupActivity::class.java)
        startActivity(intent)
    }

       fun signinClicked(){
        val intent = Intent(applicationContext, SigninActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
    }


}