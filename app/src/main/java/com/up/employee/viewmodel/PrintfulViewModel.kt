package com.up.employee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.up.employee.data.model.Printful
import com.up.employee.data.repository.PrintfulRepository
import com.up.employee.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrintfulViewModel
@Inject
constructor(printfulRepository: PrintfulRepository): ViewModel() {
    val _printfulRepository: PrintfulRepository = printfulRepository

    fun get_printfull(): LiveData<DataState<Printful>>{
        return _printfulRepository.get_printfull()
    }

}