package com.example.bindingsample

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
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
