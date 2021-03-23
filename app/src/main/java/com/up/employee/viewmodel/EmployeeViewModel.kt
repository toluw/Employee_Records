package com.up.employee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.up.employee.data.model.*
import com.up.employee.data.repository.EmployeeRepository
import com.up.employee.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel
@Inject
constructor(
    employeeRepository: EmployeeRepository): ViewModel() {

    val _employeeRepository: EmployeeRepository
    init {
        _employeeRepository = employeeRepository
    }

      fun admin_login(username: String, password: String): LiveData<DataState<AdminLogin>>{
         return  _employeeRepository.admin_login(username,password)
      }

    fun admin_signup(firstname: String,
                     lastname: String,
                     gender: String,
                     dob: String,
                     address: String,
                     country: String,
                     username: String,
                     password: String,
                     image: String): LiveData<DataState<AdminSignup>>{
        return  _employeeRepository.admin_signup(firstname,lastname,gender,dob,address,country,username,password,image)


    }

    fun get_admin(username: String): LiveData<DataState<GetAdmin>>{
        return _employeeRepository.get_admin(username)
    }

    fun employee_signup(firstname: String,
                        lastname: String,
                        gender: String,
                        dob: String,
                        address: String,
                        country: String,
                        designation: String,
                        image: String
    ): LiveData<DataState<EmployeeSignup>>{
        return  _employeeRepository.employee_signup(firstname,lastname,gender,dob,address,country,designation,image)
    }

    fun get_employee(): LiveData<DataState<GetEmployee>>{
        return  _employeeRepository.get_employee()
    }
}