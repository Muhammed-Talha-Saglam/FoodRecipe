package dev.bytecode.foodrecipe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.bytecode.foodrecipe.RecipeListEvent
import dev.bytecode.foodrecipe.RecipeListEvent.NewSearchEvent
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: (RecipeListEvent) -> Unit,
    scrollPosition: Float,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChange: (String) -> Unit,
    onChangeCategoryScrollPosition: (Float) -> Unit


) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Red,
                        shape = RoundedCornerShape(bottomStart = 60.0f, bottomEnd = 60.0f)
                    ),
            ) {


                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(bottomStart = 60.0f, bottomEnd = 60.0f)
                        ),
                    ) {

                    TextField(
                        value = query,
                        onValueChange = { onQueryChanged(it) },
                        modifier = Modifier.fillMaxWidth()
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
                                onExecuteSearch(NewSearchEvent)
                            }
                        ),

                        placeholder = { Text(text = "Search...") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                tint = Color.Black,
                                contentDescription = "Search Icon"
                            )
                        },
                        backgroundColor = Color.White,
                        activeColor = Color.Black,
                        inactiveColor = Color.Transparent,
                        textStyle = TextStyle(color = Color.Black),

                        )
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
                    scrollState.scrollTo(scrollPosition)
                }

                for (category in getAllFoodCategories()) {

                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChange(it)
                            onChangeCategoryScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = { onExecuteSearch(NewSearchEvent)}
                    )
                }
            }

        }



    }


}