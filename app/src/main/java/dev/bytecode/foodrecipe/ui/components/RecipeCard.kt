package dev.bytecode.foodrecipe.ui.components

import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import dev.bytecode.foodrecipe.domain.models.Recipe

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
    ) {

    Card(
        shape = MaterialTheme.shapes.small,
    ) {
        
    }
}