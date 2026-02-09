package com.example.taotify.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Neutral02
import com.example.taotify.ui.theme.Primary01
import com.example.taotify.ui.theme.Secondary01
import com.example.taotify.ui.theme.Secondary04

@Composable
fun PrimaryButton(
  label: String,
  loading: Boolean,
  onClick: () -> Unit
) {
  FilledTonalButton(
    enabled = !loading,
    colors = ButtonDefaults.buttonColors(
      containerColor = Primary01,
      disabledContainerColor = Secondary01
    ),
    onClick = onClick,
    modifier = Modifier.padding(16.dp)
  ) {
    if (!loading) {
      Text(
        label,
        fontFamily = CircularStd,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Secondary01,
        modifier = Modifier.padding(12.dp, 6.dp)
      )
    } else {
      CircularProgressIndicator(
        modifier = Modifier.width(24.dp),
        strokeWidth = 2.dp,
        color = Neutral02,
        trackColor = Secondary04
      )
    }
  }
}
