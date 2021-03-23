package com.up.employee.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.up.employee.R
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.activity_signup_continue.*

class EmployeeActivity : AppCompatActivity() {
    lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        text = findViewById<TextView>(R.id.text)
        val sharePref = getSharedPreferences("userInfo", MODE_PRIVATE)
        val firstname = sharePref.getString("firstname", "")
        val t = "Welcome "+firstname
        text.text = t
        createEmp.setOnClickListener {
            createEmployee()
        }
        viewEmp.setOnClickListener {
            viewEmployee()
        }
        sign.setOnClickListener {
            signoutClicked()
        }
    }

    public fun signoutClicked(){
        val sab = getSharedPreferences("userInfo", MODE_PRIVATE)
        val ed = sab.edit()
        ed.putString("firstname", "")
        ed.apply()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    public fun createEmployee(){
        val intent = Intent(applicationContext, CreateEmployeeActivity::class.java)
        startActivity(intent)
    }

    public fun viewEmployee(){
        val intent = Intent(applicationContext, ViewEmployeeActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
    }


}