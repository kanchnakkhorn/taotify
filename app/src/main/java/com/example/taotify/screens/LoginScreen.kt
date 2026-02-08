package com.example.taotify.screens

import ApiClient.generateSalt
import ApiClient.md5
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taotify.R
import com.example.taotify.components.AuthTextField
import com.example.taotify.components.PrimaryButton
import kotlinx.coroutines.launch

suspend fun login(
  serverAddress: String,
  username: String,
  password: String
): Boolean {
  val salt = generateSalt()
  val token = md5(password + salt)

  val api = ApiClient.create(baseUrl = serverAddress)

  return try {
    val response = api.ping(
      salt = salt,
      apiVersion = "1.16.1",
      username = username,
      token = token
    )

    response.`subsonic-response`.status == "ok"
  } catch (e: Exception) {
    false
  }
}


@Composable
fun LoginScreen(
  modifier: Modifier = Modifier,
  onLoginSuccess: () -> Unit
) {
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  var serverAddress by remember { mutableStateOf("") };
  var username by remember { mutableStateOf("") };
  var password by remember { mutableStateOf("") };

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.taotify),
      contentDescription = "Logo Image"
    )
    AuthTextField(
      label = "Server Address",
      value = serverAddress,
      onValueChange = { serverAddress = it },
      placeHolder = "http://ip:port",
    )

    AuthTextField(
      label = "Username",
      value = username,
      onValueChange = { username = it },
      placeHolder = "username",
    )

    AuthTextField(
      label = "Password",
      value = password,
      onValueChange = { password = it },
      placeHolder = "password",
    )

    PrimaryButton(
      label = "Login",
      onClick = {
        scope.launch {
          val success = login(serverAddress, username, password)

          if (success) {
            onLoginSuccess()
          } else {
            Toast.makeText(
              context,
              "Login failed",
              Toast.LENGTH_SHORT
            ).show()
          }
        }
      }
    )

  }

}