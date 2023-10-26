package com.submisson.aleggappstory.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.submisson.aleggappstory.data.UserRepository
import com.submisson.aleggappstory.data.response.RegisterResponse
import com.submisson.aleggappstory.data.Result

class SignupViewModel(private val repository: UserRepository): ViewModel() {

    private val _registrasiResponse = MediatorLiveData<Result<RegisterResponse>>()
    val registrasiResponse: LiveData<Result<RegisterResponse>> = _registrasiResponse

    fun registrasi(name: String, email: String, password: String){
        val liveData = repository.register(name, email, password)
        _registrasiResponse.addSource(liveData){ result ->
            _registrasiResponse.value = result
        }
    }
}