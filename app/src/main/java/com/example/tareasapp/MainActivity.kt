package com.example.tareasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.airbnb.lottie.compose.*
import com.example.tareasapp.ui.theme.TareasAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TareasAppTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    var isLoading by remember { mutableStateOf(true) }

    // Simula carga
    LaunchedEffect(Unit) {
        delay(3000)
        isLoading = false
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isLoading) {
                LottieLoadingAnimation()
            } else {
                // Aquí empieza la navegación
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { GreetingScreen(navController) }
                    composable("other") { OtherScreen() }
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Hello from Home!")
        Button(onClick = { navController.navigate("other") }) {
            Text("Ir a otra pantalla")
        }
    }
}

@Composable
fun OtherScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Esta es otra pantalla 🚀")
    }
}

@Composable
fun LottieLoadingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TareasAppTheme {
        GreetingScreen(rememberNavController())
    }
}