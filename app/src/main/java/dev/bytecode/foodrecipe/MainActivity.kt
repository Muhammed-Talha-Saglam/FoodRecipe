package dev.bytecode.foodrecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.foodrecipe.ui.theme.FoodRecipeTheme
import dev.bytecode.foodrecipe.viewModels.RecipeListViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val vm: RecipeListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FoodRecipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(vm )
                }
            }
        }
    }
}

@Composable
fun Greeting(vm: RecipeListViewModel) {


    val r = vm.recipes.value

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize()
    ) {

        r.forEach {
            it.title?.let { it1 -> Text(text = it1) }
        }
    }
}


