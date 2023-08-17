package ua.mrrobot1413.movies.misc

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.R

object Utils {
    val fonts = listOf(
        Font(R.font.nunito_regular),
        Font(R.font.nunito_bold),
        Font(R.font.nunito_black),
        Font(R.font.nunito_black_italic),
        Font(R.font.nunito_bold_italic),
        Font(R.font.nunito_extra_bold),
        Font(R.font.nunito_extra_bold_italic),
        Font(R.font.nunito_extra_light),
        Font(R.font.nunito_extra_light_italic),
        Font(R.font.nunito_italic),
        Font(R.font.nunito_light),
        Font(R.font.nunito_light_italic),
        Font(R.font.nunito_medium),
        Font(R.font.nunito_medium_italic),
        Font(R.font.nunito_semi_bold),
        Font(R.font.nunito_semi_bold_italic)
    )

    val defaultTextStyle = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily(fonts),
        )
    )

    fun onNetworkStatusChange(context: Context, onChange: suspend (connected: Boolean) -> Unit) {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                CoroutineScope(Dispatchers.Main).launch {
                    onChange(true)
                }
            }

            override fun onLost(network: Network) {
                CoroutineScope(Dispatchers.Main).launch {
                    onChange(false)
                }
            }
        })
    }
}