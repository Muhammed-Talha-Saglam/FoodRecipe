package dev.bytecode.foodrecipe.network.responses

import com.google.gson.annotations.SerializedName
import dev.bytecode.foodrecipe.network.model.RecipeDto

data class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>

    )