package com.submisson.aleggappstory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.submisson.aleggappstory.data.pref.UserModel
import com.submisson.aleggappstory.data.pref.UserPreference
import com.submisson.aleggappstory.data.response.ListStoryItem
import com.submisson.aleggappstory.data.response.LoginResponse
import com.submisson.aleggappstory.data.response.RegisterResponse
import com.submisson.aleggappstory.data.response.StoriesResponse
import com.submisson.aleggappstory.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(

    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    suspend fun saveSession(user: UserModel){
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel>{
        return userPreference.getSession()
    }

    suspend fun logout(){
        userPreference.logout()
    }

    fun register(
        name: String,
        email: String,
        password: String
    ):LiveData<Result<RegisterResponse>> =
        liveData(Dispatchers.IO){
            emit(Result.Loading)
            try {
                val response = apiService.register(name, email, password)
                emit(Result.Success(response))
            } catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> =
        liveData(Dispatchers.IO){
            emit(Result.Loading)
            try {
                val response = apiService.login(email, password)
                val token = response.loginResult.token
                saveSession(UserModel(email, token))
                emit(Result.Success(response))
            } catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }

   fun getStories(token: String): LiveData<Result<List<ListStoryItem>>> =
       liveData(Dispatchers.IO){
           emit(Result.Loading)
           try {
               val response = apiService.getStories("Bearer $token")
               val stories = response.listStory
               emit(Result.Success(stories))
           } catch (e: Exception){
               emit(Result.Error(e.message.toString()))
           }
       }

    companion object {
        @Volatile
        private var instance : UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository = instance ?: synchronized(this){
            instance ?: UserRepository(userPreference,apiService)
        }.also { instance = it }
    }
}