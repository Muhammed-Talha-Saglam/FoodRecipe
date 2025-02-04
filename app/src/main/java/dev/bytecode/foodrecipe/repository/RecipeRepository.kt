package dev.bytecode.foodrecipe.repository

import dev.bytecode.foodrecipe.domain.models.Recipe


interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun get(token: String, id: Int): Recipe

}