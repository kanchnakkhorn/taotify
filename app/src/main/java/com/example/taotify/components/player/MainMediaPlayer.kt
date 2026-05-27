package com.example.taotify.components.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import com.example.taotify.R
import com.example.taotify.data.viewmodel.AudioViewModel
import com.example.taotify.network.MediaRetrieval
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Neutral01
import com.example.taotify.ui.theme.Secondary04

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@UnstableApi
@Composable
fun MediaPlayingScreen(
  viewModel: AudioViewModel = hiltViewModel(),
  onCollapse: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  Scaffold { paddingValues ->
    Column(
      modifier = Modifier.padding(paddingValues).fillMaxSize()
    ) {

      // Head
      Row(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = { onCollapse() }
        ) {
          Icon(
            painter = painterResource(R.drawable.back),
            contentDescription = "minimize",
            modifier = Modifier.rotate(270f),
            tint = Neutral01
          )
        }

        Column(
          verticalArrangement = Arrangement.spacedBy(2.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Playing From Artist",
            fontSize = 14.sp,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Medium,
            color = Secondary04,
          )

          Text(
            text = state.currentSong?.artist ?: "Artist",
            fontSize = 14.sp,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Black,
            color = Secondary04,
          )
        }

        Icon(
          painter = painterResource(R.drawable.more),
          contentDescription = "more",
          tint = Neutral01
        )
      }

      // Cover Art
      Box(
        modifier = Modifier.padding(horizontal = 16.dp)
          .fillMaxWidth()
          .aspectRatio(1f)
          .clip(RoundedCornerShape(16.dp))
          .background(color = Neutral01)
      ) {
        AsyncImage(
          model = MediaRetrieval.getCoverArt(state.currentSong?.coverArt),
          contentDescription = "playlist cover",
          placeholder = painterResource(R.drawable.ic_broken_image),
          error = painterResource(R.drawable.ic_broken_image),
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      }

      // Title and Artist
      Row(
        modifier = Modifier.padding(16.dp)
          .fillMaxWidth()
          .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
          Text(
            text = state.currentSong?.title ?: "Title",
            fontSize = 24.sp,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Bold,
            color = Secondary04,
          )

          Text(
            text = state.currentSong?.artist ?: "Artist",
            fontSize = 20.sp,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Medium,
            color = Secondary04,
          )
        }

        IconButton(
          onClick = {}
        ) {
          Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = "add",
            tint = Neutral01
          )
        }
      }

      // Player Progress bar and it's components
      MediaPlayer()

      state.error?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
      }
    }
  }
}

