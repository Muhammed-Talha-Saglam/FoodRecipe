package dev.bytecode.foodrecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.foodrecipe.ui.components.RecipeCard
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


    val recipes = vm.recipes.value


    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ){
        itemsIndexed(
            items = recipes,
        ) { _, recipe ->

            RecipeCard(recipe = recipe, onClick = { /*TODO*/ })
        }


    }
}


