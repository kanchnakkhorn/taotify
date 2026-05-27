package com.example.taotify.nagivation

sealed class Screen(val route: String) {
  object Main : Screen("main")
  object Login : Screen("login")
  object Home : Screen("home")
  object Search : Screen("search")
  object Library : Screen("library")
  object Playlist : Screen("playlist/{playlistId}") {
    fun createRoute(playlistId: String) = "playlist/$playlistId"
  }
}