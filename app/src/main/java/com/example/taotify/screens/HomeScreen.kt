package com.example.taotify.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.taotify.components.PageHeader
import com.example.taotify.components.playlist.PlayListsContent
import com.example.taotify.data.viewmodel.AudioViewModel
import com.example.taotify.data.viewmodel.PlaylistsViewModel
import com.example.taotify.nagivation.Screen
import com.example.taotify.ui.theme.Neutral02

@Composable
fun HomeScreen(
  navController: NavController,
  modifier: Modifier = Modifier,
  audioViewModel: AudioViewModel = hiltViewModel(),
  playlistsViewModel: PlaylistsViewModel = hiltViewModel()
) {
  Column(modifier.padding(16.dp)) {
    PageHeader("Home")

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 16.dp),
      contentAlignment = Alignment.Center
    ) {
      when {
        playlistsViewModel.loading -> {
          Text(
            text = "Loading playlists...",
            color = Neutral02,
          )
        }

        playlistsViewModel.error != null -> {
          Text(
            text = "Error: ${playlistsViewModel.error}",
            color = Neutral02,
          )
        }

        playlistsViewModel.playlists.isEmpty() -> {
          Text(
            text = "You have no playlists yet.",
            color = Neutral02,
          )
        }

        else -> {
          PlayListsContent(
            playlists = playlistsViewModel.playlists,
            onItemClick = { playlist ->
              navController.navigate(Screen.Playlist.createRoute(playlist.id))
            }
          )
        }
      }
    }
  }
}
