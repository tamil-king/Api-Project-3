package com.my.apiproject.support

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.my.apiproject.R
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.regex.Pattern

object Utils {

    fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result = true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                            result = true
                        }
                    }
                }
            }
        } else {
            if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    fun emailValidation(value: String): Boolean {
        val email = value.trim()
        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        Log.e("dragon_test", "email : " + email + " :" + matcher.matches())
        return matcher.matches()
    }

    fun mobileNumberValidation(value: String): Boolean {
        val number = value.trim()
        val numberRegex = "^[6-9]\\d{9}$"
        val pattern = Pattern.compile(numberRegex)
        val matcher = pattern.matcher(number)
        return matcher.matches()
    }

    fun getJsonFromAssets(context: Context, fileName: String): String? {
        val jsonString: String = try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getColor(
        context: Context,
        language: String
    ): Int {
        return if (Constants.languageColorCodes.containsKey(language)) {
            ContextCompat.getColor(
                context,
                Constants.languageColorCodes[language]!!
            )
        } else {
            ContextCompat.getColor(context, R.color.lan_java)
        }
    }


}