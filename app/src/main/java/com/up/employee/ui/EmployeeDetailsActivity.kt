package com.up.employee.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import android.graphics.drawable.Animatable;
import com.up.employee.R
import com.facebook.imagepipeline.image.ImageInfo;


class EmployeeDetailsActivity : AppCompatActivity() {

    lateinit var firtname1: TextView
    lateinit var lastname1: TextView
    lateinit var address1: TextView
    lateinit var gender1: TextView
    lateinit var designation1: TextView
    lateinit var dob1: TextView
    lateinit var country1: TextView
    lateinit var dialog: ProgressBar
    lateinit var image: SimpleDraweeView

    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var gender: String
    lateinit var dob: String
    lateinit var address: String
    lateinit var country: String
    lateinit var designation: String
    lateinit var id: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)
        initialize()

        val mData: Bundle? = intent.extras
        firstname = mData!!.getString("firstname").toString()
        lastname = mData.getString("lastname").toString()
        gender = mData.getString("gender").toString()
        dob = mData.getString("dob").toString()
        address = mData.getString("address").toString()
        country = mData.getString("country").toString()
        designation = mData.getString("designation").toString()

        id = mData.getString("id").toString()


        firtname1.text = firstname
        lastname1.text = lastname
        gender1.text = gender
        dob1.text = dob
        address1.text = address
        country1.text = country
        designation1.text = designation

        val url = "http://34.65.124.52/byte/employee/"+id+".jpg"
        val uri = Uri.parse(url)
        image.setImageURI(uri,applicationContext)

    }

    fun initialize(){
        dialog = findViewById<ProgressBar>(R.id.dialog)
        country1 = findViewById<TextView>(R.id.country1)
        designation1 = findViewById<TextView>(R.id.designation1)
        dob1 = findViewById<TextView>(R.id.dob1)
        gender1 = findViewById<TextView>(R.id.gender1)
        address1 = findViewById<EditText>(R.id.address1)
        lastname1 = findViewById<EditText>(R.id.lastname1)
        firtname1 = findViewById<EditText>(R.id.firstname1)
       image = findViewById<SimpleDraweeView>(R.id.image)
    }

    /*
    private fun load_image(uri: Uri) {
        val listener = object : BaseControllerListener<ImageInfo?>() {
            override fun onSubmit(id: String, callerContext: Any) {
                super.onSubmit(id, callerContext)
            }

            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                dialog.visibility = View.GONE
            }

            override fun onIntermediateImageSet(id: String, imageInfo: ImageInfo?) {
                super.onIntermediateImageSet(id, imageInfo)
            }

            override fun onIntermediateImageFailed(id: String, throwable: Throwable) {
                super.onIntermediateImageFailed(id, throwable)
            }

            override fun onFailure(id: String, throwable: Throwable) {
                super.onFailure(id, throwable)
                dialog.visibility = View.GONE
            }

            override fun onRelease(id: String) {
                super.onRelease(id)
            }
        }
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(false)
                .setOldController(image.getController())
                .setControllerListener(listener)
                .build()
        image.setController(controller)
    }

    */

}