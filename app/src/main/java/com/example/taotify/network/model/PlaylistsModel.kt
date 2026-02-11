package com.example.taotify.network.model


data class Playlists(
  val playlist: List<Playlist>
)

data class Playlist(
  val id: String,
  val name: String,
  val songCount: Int,
  val duration: Int,
  val public: Boolean,
  val owner: String,
  val created: String,
  val changed: String,
  val coverArt: String
)
