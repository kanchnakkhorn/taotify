package com.example.taotify.nagivation

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.taotify.data.UserPreferences
import com.example.taotify.data.viewmodel.AudioViewModel
import com.example.taotify.screens.HomeScreen
import com.example.taotify.screens.LibraryScreen
import com.example.taotify.screens.LoginScreen
import com.example.taotify.screens.MainScreen
import com.example.taotify.screens.PlaylistScreen
import com.example.taotify.screens.SearchScreen
import com.example.taotify.session.SessionProvider
import kotlinx.coroutines.flow.first

@OptIn(UnstableApi::class)
@Composable
fun AppNavigation(
  context: Context,
  modifier: Modifier = Modifier,
  audioViewModel: AudioViewModel = hiltViewModel(),
) {
  val navController = rememberNavController()
  var startDestination by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(Unit) {
    val session = UserPreferences.session(context).first()
    if (session.token.isNotEmpty()) {
      SessionProvider.session = session
      startDestination = Screen.Main.route
    } else
      startDestination = Screen.Login.route
  }

  if (startDestination == null) return

  MainScreen(navController) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = startDestination!!,
      modifier = modifier.padding(innerPadding)
    ) {
      composable(Screen.Login.route) {
        LoginScreen(
          onLoginSuccess = {
            navController.navigate(Screen.Main.route) {
              popUpTo(Screen.Login.route) { inclusive = true }
            }
          }
        )
      }

      navigation(startDestination = Screen.Home.route, route = Screen.Main.route) {
        composable(Screen.Home.route) {
          HomeScreen(navController = navController, audioViewModel = audioViewModel)
        }
        composable(Screen.Search.route) {
          SearchScreen()
        }
        composable(Screen.Library.route) {
          LibraryScreen()
        }

        composable(Screen.Playlist.route) { backStackEntry ->
          val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
          PlaylistScreen(
            navController = navController,
            playlistId = playlistId,
            onBack = { navController.popBackStack() },
            audioViewModel = audioViewModel
          )
        }
      }
    }
  }
}
