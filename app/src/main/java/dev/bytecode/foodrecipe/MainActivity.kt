package dev.bytecode.foodrecipe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.foodrecipe.ui.components.RecipeDetailsPage
import dev.bytecode.foodrecipe.ui.components.RecipeListPage
import dev.bytecode.foodrecipe.ui.theme.FoodRecipeTheme
import dev.bytecode.foodrecipe.utils.CheckNetwork
import dev.bytecode.foodrecipe.viewModels.RecipeListViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var networkConnection: CheckNetwork


    override fun onCreate(savedInstanceState: Bundle?) {

        networkConnection = CheckNetwork(this)

        super.onCreate(savedInstanceState)

        setContent {

            val isOnline = networkConnection.observeAsState().value

            FoodRecipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp(this, isOnline)
                }
            }
        }


    }



}

@Composable
fun MyApp(context: MainActivity, isOnline: Boolean?) {



    val vm: RecipeListViewModel = viewModel("RecipeListViewModel",)



    if (isOnline == true) {
        val navController = rememberNavController()
        vm.initialSearch()

        NavHost(navController = navController, startDestination = "recipeList") {
            composable("recipeList") {
                RecipeListPage(navController, context, vm)
            }
            composable("recipeDetails") {
                RecipeDetailsPage(context, vm)

            }

        }
    } else {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Check your network connection!",
                color = Color.Red,
                fontWeight = FontWeight.Bold)
        }
    }



}




