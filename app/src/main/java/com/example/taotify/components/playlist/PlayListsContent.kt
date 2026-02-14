package com.example.taotify.components.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taotify.network.model.Playlist

@Composable
fun PlayListsContent(
  playlists: List<Playlist>
) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    verticalArrangement = Arrangement.spacedBy(10.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    modifier = Modifier.padding(0.dp, 16.dp)
  ) {
    items(playlists) { playlist ->
      PlayListListing(playlist.name, playlist.coverArt)
    }
  }
}