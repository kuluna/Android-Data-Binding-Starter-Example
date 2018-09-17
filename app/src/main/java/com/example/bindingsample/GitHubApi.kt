package com.example.bindingsample

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object GitHubApi {
    val client: GitHubService = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
}

interface GitHubService {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Call<RepositoriesResponse>
}

data class RepositoriesResponse(
        val items: List<RepositoryItem>
)

data class RepositoryItem(
        val name: String,
        val full_name: String,
        val owner: Owner,
        val stargazers_count: Int,
        val watchers_count: Int,
        val forks_count: Int,
        val language: String
)

data class Owner(
        val login: String,
        val avatar_url: String,
        val url: String
)
