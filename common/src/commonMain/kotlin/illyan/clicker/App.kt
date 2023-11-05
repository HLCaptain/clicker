package illyan.clicker

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import illyan.clicker.ui.home.HomeScreen
import illyan.clicker.ui.theme.ExampleTheme
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        ExampleTheme {
            Navigator(HomeScreen())
        }
    }
}