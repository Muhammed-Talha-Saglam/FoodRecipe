package dev.bytecode.foodrecipe.viewModels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bytecode.foodrecipe.domain.models.Recipe
import dev.bytecode.foodrecipe.repository.RecipeRepository
import dev.bytecode.foodrecipe.ui.components.FoodCategory
import dev.bytecode.foodrecipe.ui.components.getFoodCategory
import kotlinx.coroutines.launch
import javax.inject.Named


class RecipeListViewModel
@ViewModelInject constructor(
    private val randomString: String,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
    ) : ViewModel(){


    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query =  mutableStateOf("chicken")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition = 0f

    init {
        newSearch()
    }


    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
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

}