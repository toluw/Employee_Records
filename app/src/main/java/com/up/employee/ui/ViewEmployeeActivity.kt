package com.up.employee.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.up.employee.R
import com.up.employee.util.DataState
import com.up.employee.viewmodel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_view_employee.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ViewEmployeeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var list: ListView
    lateinit var text: TextView
    lateinit var dialog: ProgressBar
    lateinit var relative: RelativeLayout
    private  val employeeViewModel: EmployeeViewModel by viewModels()

    lateinit  var id : Array<String?>
    lateinit  var firstname : Array<String?>
    lateinit var lastname : Array<String?>
    lateinit var gender : Array<String?>
    lateinit  var designation : Array<String?>
    lateinit  var dob : Array<String?>
    lateinit var address : Array<String?>
    lateinit var country : Array<String?>
    lateinit var title : Array<String?>




    var id1 = ArrayList<String>()
    var firstname1 = ArrayList<String>()
    var lastname1 = ArrayList<String>()
    var gender1 = ArrayList<String>()
    var designation1 = ArrayList<String>()
    var dob1 = ArrayList<String>()
    var address1 = ArrayList<String>()
    var country1 = ArrayList<String>()
    var title1 = ArrayList<String>()
     lateinit  var  adapter: ArrayAdapter<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_employee)
        initialize()
        button.setOnClickListener { retry() }

        getData()
    }

    fun initialize(){
        list = findViewById<ListView>(R.id.list)
        text = findViewById<TextView>(R.id.text)
        dialog = findViewById<ProgressBar>(R.id.dialog)
        relative = findViewById<RelativeLayout>(R.id.relative)
    }

    fun getData(){
       employeeViewModel.get_employee().observe(this, Observer {
           when (it) {
               is DataState.Success -> {

                   dialog.visibility = View.GONE
                   val data = it.data
                   val status = data.status
                   val response = data.response
                   val results = data.result


                   if (status.equals("yes")) {
                       if (!results.isNullOrEmpty()) {

                           id = arrayOfNulls<String>(results.size)
                           firstname = arrayOfNulls<String>(results.size)
                           lastname = arrayOfNulls<String>(results.size)
                           gender = arrayOfNulls<String>(results.size)
                           designation = arrayOfNulls<String>(results.size)
                           dob = arrayOfNulls<String>(results.size)
                           address = arrayOfNulls<String>(results.size)
                           country = arrayOfNulls<String>(results.size)
                           title = arrayOfNulls<String>(results.size)


                           for (j: Int in 0 until results.size) {
                               var result = results.get(j)
                               if (result.id !== null) {
                                   id[j] = result.id.toString()
                               }

                               if (result.firstname !== null) {
                                   firstname[j] = result.firstname
                               }
                               if (result.lastname !== null) {
                                   lastname[j] = result.lastname
                               }
                               if (result.gender !== null) {
                                   gender[j] = result.gender
                               }
                               if (result.designation !== null) {
                                   designation[j] = result.designation
                               }
                               if (result.dob !== null) {
                                   dob[j] = result.dob
                               }
                               if (result.address !== null) {
                                   address[j] = result.address
                               }
                               if (result.country !== null) {
                                   country[j] = result.country
                               }
                               if ((results.get(j).firstname !== null) && (results.get(j).lastname !== null)) {
                                   val first = result.firstname
                                   val last = result.lastname
                                   val name = first + " " + last
                                   title[j] = name
                               }
                               initialList()
                               initList()
                           }

                       }

                   } else if (status.equals("no")) {
                       val a: Toast = Toast.makeText(applicationContext, response, Toast.LENGTH_LONG)
                       a.setGravity(Gravity.CENTER, 0, 0)
                       a.show()
                   } else if (status.equals("not")) {
                       val a: Toast = Toast.makeText(applicationContext, response, Toast.LENGTH_LONG)
                       a.setGravity(Gravity.CENTER, 0, 0)
                       a.show()
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

   fun retry(){
        relative.visibility = View.GONE
        getData()
    }

    fun initialList(){

        id1 = java.util.ArrayList(Arrays.asList(*id))
        firstname1 = java.util.ArrayList(Arrays.asList(*firstname))
        lastname1 = java.util.ArrayList(Arrays.asList(*lastname))
        gender1 = java.util.ArrayList(Arrays.asList(*gender))
        designation1 = java.util.ArrayList(Arrays.asList(*designation))
        dob1 = java.util.ArrayList(Arrays.asList(* dob))
        address1 = java.util.ArrayList(Arrays.asList(*address))
        country1 = java.util.ArrayList(Arrays.asList(*country))
        title1 = java.util.ArrayList(Arrays.asList(*title))
    }

    fun initList(){
     adapter = ArrayAdapter<String>(applicationContext, R.layout.simple_list_item_1, title1)
            list.adapter = adapter

        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> //4

            val firstname0 = firstname1.get(i)
            val  id0 =  id1.get(i)
            val lastname0 = lastname1.get(i)
            val gender0 = gender1.get(i)
            val designation0 = designation1.get(i)
            val dob0 = dob1.get(i)
            val address0 = address1.get(i)
            val country0 = country1.get(i)

            val intent = Intent(applicationContext, EmployeeDetailsActivity::class.java)
            intent.putExtra("firstname", firstname0)
            intent.putExtra("lastname", lastname0)
            intent.putExtra("gender", gender0)
            intent.putExtra("dob", dob0)
            intent.putExtra("address", address0)
            intent.putExtra("country", country0)
            intent.putExtra("designation", designation0)
            intent.putExtra("id", id0)
            startActivity(intent)

        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
          if(newText != null){
              initialList()
              if (!newText.equals("")) {
                  for (j: Int in title.indices) {
                      if (!title[j]!!.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))) {
                          val n: Int = title1.indexOf(title[j])
                          id1.removeAt(n)
                          title1.remove(title[j])
                          firstname1.removeAt(n)
                          lastname1.removeAt(n)
                          gender1.removeAt(n)
                          designation1.removeAt(n)
                          dob1.removeAt(n)
                          address1.removeAt(n)
                          country1.removeAt(n)
                      }
                  }
                  initList()

              } else {
                  initialList()
                  initList()
              }
              adapter.notifyDataSetChanged()

          }
        return false

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}