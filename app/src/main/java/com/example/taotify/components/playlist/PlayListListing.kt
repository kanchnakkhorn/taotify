package com.example.taotify.components.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.taotify.R
import com.example.taotify.session.SessionProvider
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Secondary02
import com.example.taotify.ui.theme.Secondary04

sealed class CoverArtResult {
  data class Success(val coverArt: String) : CoverArtResult()
  object InvalidSession : CoverArtResult()
}

private fun getCoverArtURL(
  coverArtID: String
): CoverArtResult {
  val session = SessionProvider.session
    ?: return CoverArtResult.InvalidSession

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
    return CoverArtResult.InvalidSession
  }

  val url = "$server/rest/getCoverArt.view?&u=$username&t=$token&s=$salt&v=1.16.1&c=taotify&id=$coverArtID"

  return CoverArtResult.Success(url)
}

@Composable
fun PlayListListing(
  name: String,
  coverArtID: String
) {
  //Hold the state of cover art
  val coverArtURL = remember(coverArtID) {
    when(val result = getCoverArtURL(coverArtID)) {
      is CoverArtResult.Success -> result.coverArt
      else -> null
    }
  }

  Surface(
    color = Secondary02,
    modifier = Modifier
      .clip(RoundedCornerShape(4.dp))
  ) {

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier
        .width(218.dp)
        .height(66.dp)
        .padding(8.dp, 0.dp)
    ) {
      AsyncImage(
        model = coverArtURL,
        contentDescription = null,
        placeholder = painterResource(R.drawable.ic_broken_image),
        error = painterResource(R.drawable.ic_broken_image),
        alignment = Alignment.Center,
        modifier = Modifier
          .size(50.dp)
          .background(Secondary02)
          .clip(RoundedCornerShape(25.dp))
      )

      Text(
        text = name,
        fontSize = 12.sp,
        fontFamily = CircularStd,
        color = Secondary04
      )
    }
  }
}