package dev.bytecode.foodrecipe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.bytecode.foodrecipe.network.RecipeService
import dev.bytecode.foodrecipe.network.model.RecipeDtoMapper
import dev.bytecode.foodrecipe.repository.RecipeRepository
import dev.bytecode.foodrecipe.repository.RecipeRepository_Impl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl(recipeService, recipeDtoMapper)
    }

}