package dev.bytecode.foodrecipe.viewModels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bytecode.foodrecipe.RecipeListEvent
import dev.bytecode.foodrecipe.RecipeListEvent.*
import dev.bytecode.foodrecipe.domain.models.Recipe
import dev.bytecode.foodrecipe.repository.RecipeRepository
import dev.bytecode.foodrecipe.ui.components.FoodCategory
import dev.bytecode.foodrecipe.ui.components.getFoodCategory
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

class RecipeListViewModel
@ViewModelInject constructor(
    private val randomString: String,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {


    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    var selectedRecipe = Recipe()

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Float = 0f

    val loading = mutableStateOf(false)



    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    public fun initialSearch() {
        viewModelScope.launch {
            newSearch()
        }
    }

    fun onTriggerEvent(event: RecipeListEvent) {

        viewModelScope.launch {

            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {

            }
        }

    }


    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = result
        loading.value = false

    }

    private suspend fun nextPage() {

        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()

            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                appendRecipes(result)
            }
            loading.value = false

        }
    }

    fun onSelectedCategoryChange(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChange(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    private fun incrementPage() {
        page.value += 1
    }

    private fun appendRecipes(recipe: List<Recipe>) {
        val current = ArrayList(recipes.value)
        current.addAll(recipe)
        recipes.value = current

    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

}