package com.example.taotify.data.repository

import com.example.taotify.data.model.Playlist
import com.example.taotify.data.model.Song
import com.example.taotify.session.SessionProvider
import com.example.taotify.utility.FetchResult
import javax.inject.Inject

class PlaylistRepository @Inject constructor() {
  suspend fun getPlaylists(): FetchResult<List<Playlist>> {
    val session = SessionProvider.session ?: return FetchResult.InvalidSession
    val server = session.server
    val username = session.username
    val salt = session.salt
    val token = session.token

    if (
      server.isNullOrBlank() ||
      username.isNullOrBlank() ||
      salt.isNullOrBlank() ||
      token.isNullOrBlank()
    ) {
      return FetchResult.InvalidSession
    }

    return try {
      val api = ApiClient.create(server)

      val response = api.getPlaylists(
        salt = salt,
        apiVersion = "1.16.1",
        username = username,
        token = token
      )

      val actualPlaylists = response.`subsonic-response`.playlists?.playlist ?: emptyList()
      FetchResult.Success(actualPlaylists)

    } catch (e: Exception) {
      return FetchResult.UnknownError(e.message)
    }
  }

  suspend fun getPlaylist(playlistId: String): FetchResult<List<Song>> {
    val session = SessionProvider.session ?: return FetchResult.InvalidSession
    val server = session.server
    val username = session.username
    val salt = session.salt
    val token = session.token

    if (
      server.isNullOrBlank() ||
      username.isNullOrBlank() ||
      salt.isNullOrBlank() ||
      token.isNullOrBlank()
    ) {
      return FetchResult.InvalidSession
    }

    return try {
      val api = ApiClient.create(server)

      val response = api.getPlaylist(
        salt = salt,
        apiVersion = "1.16.1",
        username = username,
        token = token,
        id = playlistId,
      )

      val actualPlaylist = response.`subsonic-response`.playlist?.entry ?: emptyList()
      FetchResult.Success(actualPlaylist)


    } catch (e: Exception) {
      return FetchResult.UnknownError(e.message)
    }
  }
}