package dev.bytecode.foodrecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dev.bytecode.foodrecipe.ui.components.FoodCategoryChip
import dev.bytecode.foodrecipe.ui.components.RecipeCard
import dev.bytecode.foodrecipe.ui.components.getAllFoodCategories
import dev.bytecode.foodrecipe.ui.theme.FoodRecipeTheme
import dev.bytecode.foodrecipe.viewModels.RecipeListViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val vm: RecipeListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FoodRecipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(vm)
                }
            }
        }
    }
}

@Composable
fun Greeting(vm: RecipeListViewModel) {


    val recipes = vm.recipes.value
    val selectedCategory = vm.selectedCategory.value
    val query = vm.query.value

    Column(
        Modifier.background(Color.Yellow)
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Yellow
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Red,
                        shape = RoundedCornerShape(bottomStart = 60.0f, bottomEnd = 60.0f)
                    ),
                horizontalArrangement = Arrangement.Center
            ) {


                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(bottomStart = 60.0f, bottomEnd = 60.0f)
                        ),
                ) {

                    TextField(
                        value = query,
                        onValueChange = { vm.onQueryChange(it) },
                        modifier = Modifier.fillMaxWidth(0.95f)
                            .padding(8.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(bottomStart = 60.0f, bottomEnd = 60.0f),
                            )
                            .border(
                                0.dp,
                                Color.Transparent,
                                shape = RoundedCornerShape(bottomStart = 60.0f, bottomEnd = 60.0f),

                                ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                            keyboardType = KeyboardType.Text,
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                vm.newSearch()
                            }
                        ),

//                        onImeActionPerformed = { action, controller ->
//                            if(action == ImeAction.Search) {
//                                controller?.hideSoftwareKeyboard()
//                                vm.newSearch(vm.query.value)
//
//                            }
//                        },

                        placeholder = { Text(text = "Search...") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                tint = Color.Black,
                                contentDescription = "Search Icon"
                            )
                        },
                        backgroundColor = Color.White,
                        activeColor = Color.Transparent,
                        inactiveColor = Color.Transparent,
                        textStyle = TextStyle(color = Color.Black),

                        )
                }

            }


        }

        val scrollState = rememberScrollState()
        val scope = rememberCoroutineScope()


        Row(
            modifier = Modifier
                .horizontalScroll(scrollState,enabled = true)
                .padding(horizontal = 8.dp, vertical = 8.dp),
        ) {

         scope.launch {
             scrollState.scrollTo(vm.categoryScrollPosition)
         }




            for (category in getAllFoodCategories()) {

                FoodCategoryChip(
                    category = category.value,
                    isSelected = selectedCategory == category,
                    onSelectedCategoryChanged = {
                        vm.onChangeCategoryScrollPosition(scrollState.value)
                        vm.onSelectedCategoryChange(it)
                    },
                    onExecuteSearch = vm::newSearch,
                    )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))



        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
        ) {

            itemsIndexed(
                items = recipes,
            ) { _, recipe ->

                RecipeCard(recipe = recipe, onClick = { /*TODO*/ })
            }


        }

    }


}





