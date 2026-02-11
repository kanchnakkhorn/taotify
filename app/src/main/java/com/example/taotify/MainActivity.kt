package com.example.taotify

import android.content.Context
import android.os.Bundle
import android.se.omapi.Session
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taotify.data.UserPreferences
import com.example.taotify.nagivation.Screen
import com.example.taotify.screens.HomeScreen
import com.example.taotify.ui.theme.TaotifyTheme
import com.example.taotify.screens.LoginScreen
import com.example.taotify.session.SessionProvider
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val context = LocalContext.current
      TaotifyTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          AppNav(
            modifier = Modifier.padding(innerPadding),
            context
          )
        }
      }
    }
  }
}

@Composable
fun AppNav(
  modifier: Modifier,
  context: Context
) {
  val navController = rememberNavController()

  LaunchedEffect(Unit) {
    val session = UserPreferences.session(context).first()
    if (session.token.isNotEmpty()) {
      SessionProvider.session = session
      navController.navigate(Screen.Home.route) {
        popUpTo(Screen.Login.route) { inclusive = true }
      }
    }
  }

  NavHost(
    navController = navController,
    startDestination = Screen.Login.route
  ) {


    composable(Screen.Login.route) {
      LoginScreen(
        onLoginSuccess = {
          navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
          }
        }
      )
    }

    composable(Screen.Home.route) {
      HomeScreen(modifier)
    }
  }
}
