package com.example.taotify.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_session")

object UserPreferences {
  private val SERVER = stringPreferencesKey("server")
  private val USERNAME = stringPreferencesKey("username")
  private val SALT = stringPreferencesKey("salt")
  private val TOKEN = stringPreferencesKey("token")

  suspend fun save(
    context: Context,
    server: String,
    username: String,
    salt: String,
    token: String
  ) {
    context.dataStore.edit {
      it[SERVER] = server
      it[USERNAME] = username
      it[SALT] = salt
      it[TOKEN] = token
    }
  }

  fun session(context: Context) = context.dataStore.data.map { prefs ->
    UserSession(
      prefs[SERVER] ?: "",
      prefs[USERNAME] ?: "",
      prefs[TOKEN] ?: "",
      prefs[SALT] ?: ""
    )
  }
}

data class UserSession(
  val server: String,
  val username: String,
  val token: String,
  val salt: String
)