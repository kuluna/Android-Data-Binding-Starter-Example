package com.example.bindingsample

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel {
    val searchWord = ObservableField<String>("")
    val loading = ObservableBoolean()
    val repositories = MutableLiveData<List<RepositoryItem>>()

    fun search(word: String) {
        loading.set(true)
        repositories.postValue(emptyList())

        GitHubApi.client.searchRepositories(word).enqueue(object : Callback<RepositoriesResponse> {
            override fun onFailure(call: Call<RepositoriesResponse>, t: Throwable) {
                loading.set(false)
                Log.e("Error", "Why?", t)
            }

            override fun onResponse(call: Call<RepositoriesResponse>, response: Response<RepositoriesResponse>) {
                loading.set(false)

                if (response.isSuccessful) {
                    response.body().let {
                        repositories.postValue(it?.items)
                    }
                }
            }

        })
    }
}
