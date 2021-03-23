package com.up.employee.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.up.employee.data.model.*
import com.up.employee.data.remote.EmployeeApi
import com.up.employee.util.DataState
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Field
import javax.inject.Inject

class EmployeeRepository
@Inject
constructor(private val employeeApi: EmployeeApi){

    fun admin_login(username: String, password: String): LiveData<DataState<AdminLogin>> =
        liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
                val result = employeeApi.admin_login(username, password)
                emit(DataState.Success(result))
            }catch(e: Exception){
                emit(DataState.Error(e))
            }
        }

    fun admin_signup(firstname: String,
                     lastname: String,
                     gender: String,
                     dob: String,
                     address: String,
                     country: String,
                     username: String,
                     password: String,
                     image: String): LiveData<DataState<AdminSignup>> =
        liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
                val result = employeeApi.admin_signup(firstname,lastname,gender,dob,address,country,username,password,image)
                emit(DataState.Success(result))
            }catch(e: Exception){
                emit(DataState.Error(e))
            }
        }

    fun get_admin(username: String): LiveData<DataState<GetAdmin>> =
        liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
                val result = employeeApi.get_admin(username)
                emit(DataState.Success(result))
            }catch(e: Exception){
                emit(DataState.Error(e))
            }
        }


    fun employee_signup(firstname: String,
                     lastname: String,
                     gender: String,
                     dob: String,
                     address: String,
                     country: String,
                     designation: String,
                        image: String
                     ): LiveData<DataState<EmployeeSignup>> =
        liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
                val result = employeeApi.employee_signup(firstname,lastname,gender,dob,address,country,designation,image)
                emit(DataState.Success(result))
            }catch(e: Exception){
                emit(DataState.Error(e))
            }
        }

    fun get_employee(): LiveData<DataState<GetEmployee>> =
        liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
                val result = employeeApi.get_employee()
                emit(DataState.Success(result))
            }catch(e: Exception){
                emit(DataState.Error(e))
            }
        }

}