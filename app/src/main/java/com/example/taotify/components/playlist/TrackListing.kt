package com.example.taotify.components.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.taotify.R
import com.example.taotify.data.model.Song
import com.example.taotify.network.MediaRetrieval
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Neutral01
import com.example.taotify.ui.theme.Neutral02

@Composable
fun TrackListing(
  entry: Song,
  index: Int,
  onItemClick: (Song) -> Unit,
  modifier: Modifier = Modifier
) {

  val coverArtURL = remember(entry.coverArt) {
    MediaRetrieval.getCoverArt(entry.coverArt)
  }

  Box (
    modifier = modifier
      .height(74.dp)
      .fillMaxWidth()
      .clickable { onItemClick(entry) }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)

    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
          .fillMaxHeight()
          .weight(1f)
      ) {

        Box(
          modifier = Modifier.width(30.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "$index",
            color = Neutral01,
            fontSize = 16.sp,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Medium
          )
        }

        Box(
          modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.dp))
            .background(color = Neutral01)
        ) {
          AsyncImage(
            model = coverArtURL,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_broken_image),
            error = painterResource(R.drawable.ic_broken_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxHeight()
          )
        }

        Column(
          modifier = Modifier
            .weight(1f)
            .padding(end = 16.dp),
        ) {
          Text(
            text = entry.title,
            fontSize = 20.sp,
            color = Neutral01,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )

          Text(
            text = "${entry.playCount}",
            color = Neutral02,
            fontSize = 16.sp,
            fontFamily = CircularStd,
            fontWeight = FontWeight.Medium,
          )
        }
      }

      Box(
        modifier = Modifier.clickable {},
        contentAlignment = Alignment.Center
      ) {
        Icon(
          painter = painterResource(R.drawable.more),
          tint = Neutral01,
          contentDescription = null,
        )
      }


    }
  }
}