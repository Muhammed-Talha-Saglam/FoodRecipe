package dev.bytecode.foodrecipe.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import dev.bytecode.foodrecipe.RecipeListEvent
import dev.bytecode.foodrecipe.viewModels.PAGE_SIZE
import dev.bytecode.foodrecipe.viewModels.RecipeListViewModel

@Composable
fun RecipeListPage(navController: NavHostController, context: Context, vm: RecipeListViewModel) {




    val recipes = vm.recipes.value
    val selectedCategory = vm.selectedCategory.value
    val query = vm.query.value
    val page = vm.page.value




    Scaffold(
        topBar =
        {
            SearchAppBar(
                query = query,
                onQueryChanged = vm::onQueryChange,
                onExecuteSearch = vm::onTriggerEvent,
                scrollPosition = vm.categoryScrollPosition,
                selectedCategory = selectedCategory,
                onSelectedCategoryChange = vm::onSelectedCategoryChange,
                onChangeCategoryScrollPosition = vm::onChangeCategoryScrollPosition
            )
        }

    ) {
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
        ) {

            itemsIndexed(
                items = recipes,
            ) { index, recipe ->

                vm.onChangeRecipeScrollPosition(index)
                Log.d("index", "index = $index")

                if((index + 1) >= (page * PAGE_SIZE) ) {
                    vm.onTriggerEvent(RecipeListEvent.NextPageEvent)
                }

                RecipeCard(
                    recipe = recipe,
                    context= context
                ) {
                    vm.selectedRecipe = recipe
                    navController.navigate("recipeDetails")
                }
            }

        }


        // Show loading progress bar
        if(vm.loading.value  ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .size(60.dp)
                )
            }

        }
    }


}