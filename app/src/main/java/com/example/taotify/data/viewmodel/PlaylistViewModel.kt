package com.example.taotify.data.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taotify.data.model.Playlist
import com.example.taotify.data.model.Song
import com.example.taotify.data.repository.PlaylistRepository
import com.example.taotify.utility.FetchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
  private val repository: PlaylistRepository
) : ViewModel() {

  var loading by mutableStateOf(true)
  var error by mutableStateOf<String?>(null)
  var playlists by mutableStateOf<List<Playlist>>(emptyList())
  var playlistEntries by mutableStateOf<List<Song>>(emptyList())


  init {
    fetchPlaylists()
  }

  fun getPlaylistById(playlistId: String): Playlist? {
    return playlists.find { it.id == playlistId }
  }

  private fun fetchPlaylists() {
    viewModelScope.launch {
      loading = true
      when(val result = repository.getPlaylists()) {
        is FetchResult.Success -> {
          playlists = result.data
        }

        is FetchResult.InvalidSession -> {
          error = "Session is invalid or has expired. Please log in again."
        }
        is FetchResult.UnknownError -> {
          error = result.message ?: "An unknown error occurred."
        }
      }

      loading = false
    }
  }

  fun fetchPlaylist(playlistId: String) {
    viewModelScope.launch {
      loading = true
      when(val result = repository.getPlaylist(playlistId)) {
        is FetchResult.Success -> {
          playlistEntries = result.data
        }

        is FetchResult.InvalidSession -> {
          error = "Session is invalid or has expired. Please log in again."
        }
        is FetchResult.UnknownError -> {
          error = result.message ?: "An unknown error occurred."
        }
      }

      loading = false
    }
  }
}