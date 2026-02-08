import com.example.taotify.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

object ApiClient {
  fun create(baseUrl: String): ApiService {
    val logging = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
      .addInterceptor(logging)
      .build()

    return Retrofit.Builder()
      .baseUrl(baseUrl.trimEnd('/') + "/")
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(ApiService::class.java)
  }

  // Generate 8-character random salt
  fun generateSalt(): String {
    val allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789"
    return (1..8)
      .map { allowedChars.random() }
      .joinToString("")
  }

  // Compute MD5 hash
  fun md5(input: String): String {
    val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
  }
}
