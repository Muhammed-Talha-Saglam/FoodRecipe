package dev.bytecode.foodrecipe.repository

import dev.bytecode.foodrecipe.domain.models.Recipe
import dev.bytecode.foodrecipe.network.RecipeService
import dev.bytecode.foodrecipe.network.model.RecipeDtoMapper

class RecipeRepository_Impl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query).recipes
        return mapper.toDomainList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        val result = recipeService.get(token, id)
        return mapper.mapToDomainModel(result)
    }
}