package com.up.employee.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.up.employee.data.model.GetEmployee
import com.up.employee.data.model.Printful
import com.up.employee.data.remote.EmployeeApi
import com.up.employee.data.remote.PrintfulApi
import com.up.employee.util.DataState
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PrintfulRepository
@Inject
constructor(private val printfulApi: PrintfulApi) {
    fun get_printfull(): LiveData<DataState<Printful>> =
        liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
                val result = printfulApi.getPrintful()
                emit(DataState.Success(result))
            }catch(e: Exception){
                emit(DataState.Error(e))
            }
        }
}