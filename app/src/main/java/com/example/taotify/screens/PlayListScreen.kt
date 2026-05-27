package com.example.taotify.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.taotify.R
import com.example.taotify.components.playlist.PlayListCover
import com.example.taotify.components.playlist.TrackListing
import com.example.taotify.data.viewmodel.AudioViewModel
import com.example.taotify.data.viewmodel.PlaylistsViewModel
import com.example.taotify.network.MediaRetrieval
import com.example.taotify.ui.theme.Neutral01
import com.example.taotify.ui.theme.Primary01

@Composable
fun PlaylistScreen(
  navController: NavController,
  playlistId: String,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
  playlistViewModel: PlaylistsViewModel = hiltViewModel(),
  audioViewModel: AudioViewModel = hiltViewModel()
) {
  val playlist = playlistViewModel.getPlaylistById(playlistId)
  val state by audioViewModel.state.collectAsStateWithLifecycle()
  val coverArtURL = remember(playlist?.coverArt) {
    MediaRetrieval.getCoverArt(playlist?.coverArt ?: "")
  }

  LaunchedEffect(playlistId) {
    playlistViewModel.fetchPlaylist(playlistId)
  }

  LazyColumn(
    modifier = modifier.fillMaxSize()
  ) {
    item {
      PlayListCover(coverArtURL, onBack, playlist);
    }

    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

      ) {
        IconButton(
          onClick = { audioViewModel.playQueue(songIds = playlistViewModel.playlistEntries.map { it.id }) },
          modifier = Modifier.size(64.dp)
        ) {
          Icon(
            painter = painterResource(R.drawable.playlist_pause),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(56.dp)
          )
        }

        Row(
          horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          IconButton(
//            modifier = Modifier.size(),
            onClick = { audioViewModel.toggleShuffle() }
          ) {
            Icon(
              painter = painterResource(R.drawable.shuffle),
              contentDescription = null,
              tint = if (state.isShuffled) Primary01 else Neutral01,
            )
          }

          IconButton(
//            modifier = Modifier.size(),
            onClick = {  },
          ) {
            Icon(
              painter = painterResource(R.drawable.more),
              contentDescription = null,
              tint = Neutral01,
            )
          }
        }
      }
    }

    itemsIndexed(playlistViewModel.playlistEntries) { index, entry ->
      TrackListing(
        index = index + 1,
        entry =  entry,
        modifier = Modifier.padding(horizontal = 16.dp),
        onItemClick = { entry ->
            audioViewModel.playQueue(
              playlistViewModel.playlistEntries.map { it.id },
              startIndex = index)
        }
      )
    }
  }
}