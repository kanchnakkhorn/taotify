package com.example.taotify.network

import retrofit2.http.GET
import retrofit2.http.Query

data class PingWrapper(
  val `subsonic-response`: PingResponse
)

data class PingResponse(
  val status: String,
  val version: String,
  val type: String,
  val serverVersion: String,
  val openSubsonic: Boolean
)

interface ApiService {
  @GET("rest/ping.view")
  suspend fun ping(
    @Query("s") salt: String,
    @Query("v") apiVersion: String,
    @Query("c") client: String = "myapp",
    @Query("u") username: String,
    @Query("f") format: String = "json",
    @Query("t") token: String
  ): PingWrapper
}
