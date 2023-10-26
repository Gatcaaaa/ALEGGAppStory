package com.submisson.aleggappstory.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.submisson.aleggappstory.data.Result
import com.submisson.aleggappstory.data.UserRepository
import com.submisson.aleggappstory.data.pref.UserModel
import com.submisson.aleggappstory.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    private val _storyListItem = MediatorLiveData<Result<List<ListStoryItem>>>()
    val storyListItem: LiveData<Result<List<ListStoryItem>>> = _storyListItem

    fun getStories(token: String){
        val liveData = repository.getStories(token)
        _storyListItem.addSource(liveData){ result ->
            _storyListItem.value = result
        }
    }

    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}