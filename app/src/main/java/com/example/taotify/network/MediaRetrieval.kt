package com.example.taotify.network

import com.example.taotify.session.SessionProvider

object MediaRetrieval {
  fun getCoverArt(coverArtID: String?) = buildURL("getCoverArt", "id=$coverArtID")
  // stream
  fun getStreamingUrl(mediaID: String) = buildURL("stream", "id=$mediaID", "maxBitRate=128", "format=mp3")
  // getTrack)
  // getCaptions
  // getLyrics

  private fun buildURL(endpoint: String, vararg parameters: String): String? {
    val session = SessionProvider.session ?: return null
    val url = "${session.server}/rest/$endpoint.view?&u=${session.username}&t=${session.token}&s=${session.salt}&v=1.16.1&c=taotify"

    return if (parameters.isEmpty()) {
      url
    } else {
      "$url&${parameters.joinToString("&")}"
    }
  }
}