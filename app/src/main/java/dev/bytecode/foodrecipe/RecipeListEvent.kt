package dev.bytecode.foodrecipe

sealed class RecipeListEvent {

    object NewSearchEvent: RecipeListEvent()

    object NextPageEvent: RecipeListEvent()
}