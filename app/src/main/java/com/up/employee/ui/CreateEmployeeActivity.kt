package com.up.employee.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import com.up.employee.R
import com.up.employee.data.model.Printful
import com.up.employee.util.DataState
import com.up.employee.viewmodel.EmployeeViewModel
import com.up.employee.viewmodel.PrintfulViewModel
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_create_employee.*
import kotlinx.android.synthetic.main.activity_create_employee.a
import kotlinx.android.synthetic.main.activity_create_employee.ar
import kotlinx.android.synthetic.main.activity_create_employee.ar1
import kotlinx.android.synthetic.main.activity_create_employee.ar3
import kotlinx.android.synthetic.main.activity_create_employee.button
import kotlinx.android.synthetic.main.activity_create_employee.im1
import kotlinx.android.synthetic.main.activity_create_employee.im2
import kotlinx.android.synthetic.main.activity_create_employee.im3
import kotlinx.android.synthetic.main.activity_signup.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CreateEmployeeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener {
    lateinit var firtname1: EditText
    lateinit var lastname1: EditText
    lateinit var address1: EditText
    lateinit var gender1: TextView
    lateinit var designation1: TextView
    lateinit var dob1: TextView
    lateinit var country1: TextView
    lateinit var dialog: ProgressBar
    lateinit var linear: LinearLayout
    lateinit var relate: RelativeLayout
    lateinit var relative: RelativeLayout
    lateinit var imageView: ImageView
    private  val printfulViewModel: PrintfulViewModel by viewModels()
    private val IMAGE_REQUEST = 2
    var countr = ArrayList<String>()
    private  val employeeViewModel: EmployeeViewModel by viewModels()
    lateinit var image: String
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var gender: String
    lateinit var dob: String
    lateinit var address: String
    lateinit var country: String
    lateinit var designation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_employee)
        initialize()
        a.setOnClickListener { uploadClicked() }
        b.setOnClickListener { submitClicked() }
        ar.setOnClickListener { genderClicked(it) }
        gender1.setOnClickListener { genderClicked(it) }
        im1.setOnClickListener { genderClicked(it) }
        ar3.setOnClickListener { countryClicked() }
        im3.setOnClickListener { countryClicked() }
        country1.setOnClickListener { countryClicked() }
        button.setOnClickListener { retry() }
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
        designation1 = findViewById<TextView>(R.id.designation1)
        dob1 = findViewById<TextView>(R.id.dob1)
        gender1 = findViewById<TextView>(R.id.gender1)
        address1 = findViewById<EditText>(R.id.address1)
        lastname1 = findViewById<EditText>(R.id.lastname1)
        firtname1 = findViewById<EditText>(R.id.firstname1)
        relate = findViewById<RelativeLayout>(R.id.relate)
        relative = findViewById<RelativeLayout>(R.id.relative)
        imageView = findViewById<ImageView>(R.id.imageView)
    }

    //Get list of countries
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
                                countr.add(result.name)
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
   fun retry(){
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
        val adapter = ArrayAdapter<String>(applicationContext, R.layout.simple_list_item_1, countr)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> //4

            val inst: String = countr.get(i)
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

   fun uploadClicked(){
        firstname = firtname1.text.toString().trim()
       lastname = lastname1.text.toString().trim()
       gender = gender1.text.toString().trim()
       dob = dob1.text.toString().trim()
       address = address1.text.toString().trim()
       country = country1.text.toString().trim()
       designation = designation1.text.toString().trim()
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
       }else if(designation.equals("")){
           val a: Toast = Toast.makeText(applicationContext, "Enter Designation", Toast.LENGTH_LONG)
           a.setGravity(Gravity.CENTER, 0, 0)
           a.show()
       }
       else if(dob.equals(dob0)){
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
           uploadImage()
       }

   }

    fun uploadImage(){
        val intent = Intent()
        intent.type = "image/jpeg"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            val uri = data!!.data
            imageView.setImageURI(uri)
            relate.visibility = View.VISIBLE

        }
    }

    fun submitClicked() {
        val image = (imageView.getDrawable() as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
        employeeViewModel.employee_signup(firstname,lastname,gender,dob,address,country,designation,encodedImage).observe(this,
            Observer {
                when (it){
                    is DataState.Success ->{
                        dialog.visibility = View.GONE
                        val data = it.data
                        val status = data.status
                        val response = data.response
                        if(status.equals("yes")){
                            val intent = Intent(applicationContext, EmployeeActivity::class.java)
                            startActivity(intent)
                        }else if (status.equals("no")){
                            val a: Toast = Toast.makeText(applicationContext, response, Toast.LENGTH_LONG)
                            a.setGravity(Gravity.CENTER, 0, 0)
                            a.show()
                        }
                    }
                    is DataState.Loading ->{
                        dialog.visibility = View.VISIBLE
                    }
                    is DataState.Error ->{
                        dialog.visibility = View.GONE
                        val a: Toast = Toast.makeText(applicationContext, "A connction error occured", Toast.LENGTH_LONG)
                        a.setGravity(Gravity.CENTER, 0, 0)
                        a.show()

                    }
                }
            })
    }



    override fun onBackPressed() {
        val intent = Intent(applicationContext, EmployeeActivity::class.java)
        startActivity(intent)
    }
}


