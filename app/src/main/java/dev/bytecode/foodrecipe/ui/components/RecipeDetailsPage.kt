package dev.bytecode.foodrecipe.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.bytecode.foodrecipe.R
import dev.bytecode.foodrecipe.domain.models.Recipe
import dev.bytecode.foodrecipe.utils.LoadPicture
import dev.bytecode.foodrecipe.viewModels.RecipeListViewModel

@Composable
fun RecipeDetailsPage(context: Context, vm: RecipeListViewModel) {


    val recipe = vm.selectedRecipe



    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {


        recipe.featuredImage?.let { url ->
            MakePicture(url, context)
        }


        MakeDetailsTitle(recipe)


        MakeDetailsIngredients(recipe)


        Row {
            Text(
                text = "Date added: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${recipe.dateAdded}",
                )


        }



    }


}

@Composable
fun MakePicture(url: String, context: Context) {

    val image = LoadPicture(context, url = url, defaultImage = R.drawable.empty_plate).value

    image?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            contentDescription = "Food Image",
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(250.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MakeDetailsTitle(recipe: Recipe) {



    Row(
        modifier = Modifier
            .background(color = Color.Blue.copy(alpha = 0.5f))
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = recipe.title ?: "N/A",
            modifier = Modifier.fillMaxWidth(0.8f),
            fontWeight = FontWeight.Bold
            )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 0.dp, start = 40.dp)
        ) {
            Text(
                text = recipe.rating.toString(),
                style = TextStyle(Color.Red, fontSize = 16.sp)
            )

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Star icon",
                tint = Color.Yellow,
                modifier = Modifier.align(Alignment.End)
            )
        }

    }
}

@Composable
fun MakeDetailsIngredients(recipe: Recipe) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ingredients:",
            style = TextStyle(
                fontSize = 25.sp,
                color = Color.Red,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
        )

        var allIngredients = ""

        recipe.ingredients.let {
            it.forEach { ing ->
                allIngredients += "$ing\n "
            }
        }

        Text(
            text = allIngredients,
            style = MaterialTheme.typography.subtitle1.copy(Color.Black),
            modifier = Modifier.padding(start = 5.dp)
        )

    }
}