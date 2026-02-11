package com.example.taotify.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taotify.network.model.Playlist
import com.example.taotify.session.SessionProvider
import com.example.taotify.ui.theme.Neutral02
import com.example.taotify.ui.theme.Secondary01

sealed class PlaylistResult {
  data class Success(val playlists: List<Playlist>) : PlaylistResult()
  object InvalidSession : PlaylistResult()
  data class UnknownError(val message: String?) : PlaylistResult()
}

suspend fun getPlaylists(
  context: Context
): PlaylistResult {
  val session = SessionProvider.session
    ?: return PlaylistResult.InvalidSession

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
    return PlaylistResult.InvalidSession
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
    PlaylistResult.Success(actualPlaylists)

  } catch (e: Exception) {
    return PlaylistResult.UnknownError(e.message)
  }
}


@Composable
fun HomeScreen(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  var loading by remember { mutableStateOf(true) }
  var error by remember { mutableStateOf<String?>(null) }
  var playlists by remember { mutableStateOf<List<Playlist>>(emptyList()) }

  LaunchedEffect(Unit) {
    when (val result = getPlaylists(context)) {
      is PlaylistResult.Success -> {
        playlists = result.playlists
        loading = false
      }
      is PlaylistResult.InvalidSession -> {
        error = "Session is invalid or has expired. Please log in again."
        loading = false
      }
      is PlaylistResult.UnknownError -> {
        error = result.message ?: "An unknown error occurred."
        loading = false
      }
    }
  }

  Column(modifier.padding(16.dp)) {
    when {
      loading -> {
        Text("Loading playlists...")
      }

      error != null -> {
        Text("Error: $error")
      }

      playlists.isEmpty() -> {
        Text("You have no playlists yet.")
      }

      else -> {
        playlists.forEach { playlist: Playlist ->
          Text(
            playlist.name,
            color = Neutral02
          )
        }
      }
    }
  }
}
