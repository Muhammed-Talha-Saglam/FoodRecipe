package dev.bytecode.foodrecipe.viewModels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bytecode.foodrecipe.domain.models.Recipe
import dev.bytecode.foodrecipe.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Named


class RecipeListViewModel
@ViewModelInject constructor(
    private val randomString: String,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
    ) : ViewModel(){


    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())


    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                page = 1,
                query = "chicken"
            )
            recipes.value = result
        }
    }

}