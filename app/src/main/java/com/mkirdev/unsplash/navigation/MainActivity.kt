package com.mkirdev.unsplash.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme

class MainActivity : ComponentActivity() {
    private val deepLinkIntent = mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deepLinkIntent.value = intent
        setContent {
            UnsplashTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                MainNavHost(deepLinkIntent)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deepLinkIntent.value = intent
        setIntent(intent)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnsplashTheme {
        Greeting("Android")
    }
}