package com.example.uas_2.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ObatViewModel : ViewModel() {

    private val _obatList = MutableLiveData<List<ObatResponseItem>>()
    val obatList: LiveData<List<ObatResponseItem>> get() = _obatList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        fetchObat()
    }

    fun fetchObat() {
        _isLoading.value = true
        _errorMessage.value = ""
        viewModelScope.launch {
            try {
                val response = ApiClient.crudService.getAllObatData()
                if (response.isNotEmpty()) {
                    _obatList.value = response
                } else {
                    _errorMessage.value = "Failed to load Obat Data"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown Error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateObat(id: Long, updatedObat: ObatRequest){
        viewModelScope.launch {
            try {
                val response = ApiClient.crudService.updateObat(id, updatedObat)
                _obatList.value = _obatList.value?.map { obat ->
                    if (obat.id == id) response else obat
                }
                fetchObat()
            } catch (e: Exception){
                _errorMessage.value = e.message ?: "Unknown Error"
            }
        }
    }

    fun deleteObat(id: Long) {
        viewModelScope.launch {
            try {
                val response = ApiClient.crudService.deleteObat(id)
                if (response.isSuccessful) {
                    _obatList.value = _obatList.value?.filterNot { it.id == id }
                } else {
                    _errorMessage.value = "Failed to delete: ${response.code()}"
                }
                fetchObat()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown Error"
            }
        }
    }

    fun createObat(obat: ObatRequest) {
        viewModelScope.launch {
            try {
                ApiClient.crudService.createObat(obat)
                fetchObat()
            } catch (e: Exception) {
             _errorMessage.value = e.message ?: "Unknown Error"
            }
            fetchObat()
        }
    }

}