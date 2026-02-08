package com.example.taotify.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taotify.ui.theme.CircularStd
import com.example.taotify.ui.theme.Neutral02
import com.example.taotify.ui.theme.Secondary04

@Composable
fun AuthTextField(
  label: String,
  value: String,
  onValueChange: (String) -> Unit,
  placeHolder: String
) {
  Column(modifier = Modifier.padding(16.dp, 0.dp)) {
    Text(
      text = label,
      fontFamily = CircularStd,
      fontWeight = FontWeight.Bold,
      fontSize = 20.sp,
      color = Secondary04,
      modifier = Modifier.padding(0.dp, 6.dp)
    )

    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      placeholder = {
        Text(
          placeHolder,
          fontFamily = CircularStd,
          fontWeight = FontWeight.Medium
        )
      },
      shape = RoundedCornerShape(28.dp),
      colors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Secondary04,
        focusedContainerColor = Secondary04,
        focusedTextColor = Neutral02,
        unfocusedTextColor = Neutral02
      ),
      modifier = Modifier
        .fillMaxWidth()
    )
  }
}