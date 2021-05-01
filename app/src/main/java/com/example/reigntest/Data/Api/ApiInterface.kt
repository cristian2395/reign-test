package com.example.reigntest.Data.Api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


interface ApiInterface {
    @GET
    suspend fun get(@Url url: String): Response<String>


    @POST
    suspend fun post(@Url url: String): Response<String>
}