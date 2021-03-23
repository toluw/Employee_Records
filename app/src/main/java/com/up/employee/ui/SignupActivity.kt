package com.up.employee.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.up.employee.R
import com.up.employee.data.model.Printful

import com.up.employee.util.DataState
import com.up.employee.viewmodel.EmployeeViewModel
import com.up.employee.viewmodel.PrintfulViewModel
import dagger.hilt.android.AndroidEntryPoint

import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.a
import kotlinx.android.synthetic.main.activity_signup.ar


@AndroidEntryPoint
class SignupActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener {
    lateinit var firtname1: EditText
    lateinit var lastname1: EditText
    lateinit var address1: EditText
    lateinit var gender1: TextView
    lateinit var dob1: TextView
    lateinit var country1: TextView
    lateinit var dialog: ProgressBar
    lateinit var linear: LinearLayout
    lateinit var relative: RelativeLayout
    private  val printfulViewModel: PrintfulViewModel by viewModels()

    var country = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initialize()
       a.setOnClickListener { continueClicked() }
        ar.setOnClickListener { genderClicked(it) }
        gender1.setOnClickListener { genderClicked(it) }
        im1.setOnClickListener { genderClicked(it) }
        ar3.setOnClickListener { countryClicked() }
        im3.setOnClickListener { countryClicked() }
        country1.setOnClickListener { countryClicked() }
        button.setOnClickListener { retryClicked() }
        ar1.setOnClickListener {dobClicked()  }
        im2.setOnClickListener { dobClicked() }
        dob1.setOnClickListener { dobClicked() }

        //Get list of countries from Printful API
        getCountry()
    }

    fun initialize(){
        dialog = findViewById<ProgressBar>(R.id.dialog)
        linear = findViewById<LinearLayout>(R.id.linear)
        country1 = findViewById<TextView>(R.id.country1)
        dob1 = findViewById<TextView>(R.id.dob1)
        gender1 = findViewById<TextView>(R.id.gender1)
        address1 = findViewById<EditText>(R.id.address1)
        lastname1 = findViewById<EditText>(R.id.lastname1)
        firtname1 = findViewById<EditText>(R.id.firstname1)
        relative = findViewById<RelativeLayout>(R.id.relative)
    }

    fun getCountry(){
        printfulViewModel.get_printfull().observe(this, Observer {
            when (it) {
                is DataState.Success<Printful> -> {

                    dialog.visibility = View.GONE
                    val data = it.data

                    val results = data.result
                    if (results != null) {
                        for (result in results) {
                            val name = result.name
                            if (name != null) {
                                country.add(result.name)
                            }
                        }
                        linear.visibility = View.VISIBLE
                    }

                }
                is DataState.Error -> {
                    dialog.visibility = View.GONE
                    relative.visibility = View.VISIBLE

                }
                is DataState.Loading -> {
                    dialog.visibility = View.VISIBLE

                }
            }
        })
    }

    //Retry after connection failure
    fun retryClicked(){
        relative.visibility = View.GONE
        getCountry()
    }

   fun countryClicked(){
        val inflater = applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView: View = inflater.inflate(R.layout.country_list, null)
      val  mPopupWindow = PopupWindow(
              customView,
              ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT
      )

        mPopupWindow.setOutsideTouchable(false)
        mPopupWindow.setFocusable(true)
        mPopupWindow.showAtLocation(country1, Gravity.BOTTOM, 0, 0)
        mPopupWindow.update()

       val list = customView.findViewById<View>(R.id.list) as ListView
       val adapter = ArrayAdapter<String>(applicationContext, R.layout.simple_list_item_1, country)
        list.adapter = adapter
        list.onItemClickListener = OnItemClickListener { adapterView, view, i, l -> //4

            val inst: String = country.get(i)
            country1.text = inst
            mPopupWindow.dismiss()

        }


    }

    fun dobClicked(){
        val datePicker: DialogFragment = DatePickerFragment()
        datePicker.show(supportFragmentManager, "date picker")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val currentDateString: String = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime())
        dob1.text = currentDateString

    }

      fun genderClicked(view: View){
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.gend)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    //Gender selected
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val l = item!!.title.toString()
        gender1.text = l
        return true
    }

    fun continueClicked(){
       val firstname = firtname1.text.toString().trim()
        val lastname = lastname1.text.toString().trim()
        val gender = gender1.text.toString().trim()
        val dob = dob1.text.toString().trim()
        val address = address1.text.toString().trim()
        val country = country1.text.toString().trim()
        val country0 = resources.getString(R.string.country)
        val dob0 = resources.getString(R.string.dob)
        val gender0 = resources.getString(R.string.gender)
        if(firstname.equals("")){
            val a: Toast = Toast.makeText(applicationContext, "Enter Firstname", Toast.LENGTH_LONG)
            a.setGravity(Gravity.CENTER, 0, 0)
            a.show()
        }else if(lastname.equals("")){
            val a: Toast = Toast.makeText(applicationContext, "Enter Lastname", Toast.LENGTH_LONG)
            a.setGravity(Gravity.CENTER, 0, 0)
            a.show()
        }else if(gender.equals(gender0)){
            val a: Toast = Toast.makeText(applicationContext, "Select Gender", Toast.LENGTH_LONG)
            a.setGravity(Gravity.CENTER, 0, 0)
            a.show()
        }else if(dob.equals(dob0)){
            val a: Toast = Toast.makeText(applicationContext, "Enter Date of Birth", Toast.LENGTH_LONG)
            a.setGravity(Gravity.CENTER, 0, 0)
            a.show()
        }else if(address.equals("")){
            val a: Toast = Toast.makeText(applicationContext, "Enter Address", Toast.LENGTH_LONG)
            a.setGravity(Gravity.CENTER, 0, 0)
            a.show()
        }else if(country.equals(country0)){
            val a: Toast = Toast.makeText(applicationContext, "Select Country", Toast.LENGTH_LONG)
            a.setGravity(Gravity.CENTER, 0, 0)
            a.show()
        }else{
            val intent = Intent(applicationContext, SignupContinueActivity::class.java)
            intent.putExtra("firstname",firstname)
            intent.putExtra("lastname",lastname)
            intent.putExtra("gender",gender)
            intent.putExtra("dob",dob)
            intent.putExtra("address",address)
            intent.putExtra("country",country)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

}