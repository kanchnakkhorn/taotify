package com.example.taotify.network

import com.example.taotify.data.model.Playlist
import com.example.taotify.data.model.Playlists
import com.example.taotify.data.model.Song
import com.google.gson.annotations.SerializedName
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

data class SubsonicResponse<T>(
  @SerializedName("subsonic-response")
  val `subsonic-response`: SubsonicMeta<T>
)

data class SubsonicMeta<T>(
  val status: String,
  val version: String,
  val type: String,
  val serverVersion: String,
  val openSubsonic: Boolean,
  val playlists: T? = null,
  val playlist: T? = null,
  val song: T? = null
)

interface ApiService {
  @GET("rest/ping.view")
  suspend fun ping(
    @Query("s") salt: String,
    @Query("v") apiVersion: String,
    @Query("c") client: String = "taotify",
    @Query("u") username: String,
    @Query("f") format: String = "json",
    @Query("t") token: String
  ): PingWrapper

  @GET("rest/getPlaylists")
  suspend fun getPlaylists(
    @Query("s") salt: String,
    @Query("v") apiVersion: String,
    @Query("c") client: String = "taotify",
    @Query("u") username: String,
    @Query("f") format: String = "json",
    @Query("t") token: String
  ): SubsonicResponse<Playlists>

  @GET("rest/getPlaylist")
  suspend fun getPlaylist(
    @Query("s") salt: String,
    @Query("v") apiVersion: String,
    @Query("c") client: String = "taotify",
    @Query("u") username: String,
    @Query("f") format: String = "json",
    @Query("t") token: String,
    @Query("id") id: String
  ): SubsonicResponse<Playlist>

  @GET("rest/getSong")
  suspend fun getSong(
    @Query("s") salt: String,
    @Query("v") apiVersion: String,
    @Query("c") client: String = "taotify",
    @Query("u") username: String,
    @Query("f") format: String = "json",
    @Query("t") token: String,
    @Query("id") id: String
  ): SubsonicResponse<Song>
}
