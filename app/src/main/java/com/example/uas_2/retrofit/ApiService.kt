package com.example.uas_2.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("obat")
    suspend fun getAllObatData(): List<ObatResponseItem>

    @GET("obat/{id}")
    suspend fun getObatById(@Path("id") id: Int): ObatResponseItem

    @POST("obat/create")
    suspend fun createObat(@Body obat: ObatRequest): ObatResponseItem

    @PUT("obat/update/{id}")
    suspend fun updateObat(@Path("id") id: Long, @Body obat: ObatRequest): ObatResponseItem

    @DELETE("obat/delete/{id}")
    suspend fun deleteObat(@Path("id") id: Long): Response<Unit>

}