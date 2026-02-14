package com.example.taotify.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taotify.R
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Secondary04

@Composable
fun Header(
  label: String,
) {
  Row (
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.taotify),
      contentDescription = "Logo Image",
      modifier = Modifier.size(50.dp)
    )
    Text(
      text = label,
      fontSize = 28.sp,
      fontFamily = CircularStd,
      fontWeight = FontWeight.Bold,
      color = Secondary04
    )
  }
}