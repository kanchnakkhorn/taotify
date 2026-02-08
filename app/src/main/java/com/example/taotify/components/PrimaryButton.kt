package com.example.taotify.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Primary01
import com.example.taotify.ui.theme.Secondary01

@Composable
fun PrimaryButton(
  label: String,
  onClick: () -> Unit,
) {
  FilledTonalButton(
    colors = ButtonDefaults.buttonColors(
      containerColor = Primary01
    ),
    onClick = { onClick() },
    modifier = Modifier.padding(16.dp)
  ) {
    Text(
      label,
      fontFamily = CircularStd,
      fontWeight = FontWeight.Bold,
      fontSize = 20.sp,
      color = Secondary01,
      modifier = Modifier.padding(12.dp,6.dp)
    )
  }
}