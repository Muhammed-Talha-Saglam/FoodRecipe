package dev.bytecode.foodrecipe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bytecode.foodrecipe.R
import dev.bytecode.foodrecipe.domain.models.Recipe
import dev.bytecode.foodrecipe.utils.LoadPicture

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(
            topStart = 25.dp,
            topEnd = 25.dp,
            bottomEnd = 25.dp,
            bottomStart = 25.dp
            ),
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .clickable(onClick =onClick),
        elevation = 8.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(200.dp)
                .background(Color.Black)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                MakeFoodImage(recipe)

                ShowIngredients(recipe)

            }

                MakeRecipeTitle(recipe)

        }


    }
}


@Composable
fun MakeRecipeTitle(recipe: Recipe, ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recipe.title.toString(),
            style = MaterialTheme.typography.subtitle1.copy(Color.Blue),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
fun MakeFoodImage(recipe: Recipe) {

    recipe.featuredImage?.let { url ->

        val image = LoadPicture(url = url, defaultImage = R.drawable.empty_plate).value

        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = "Food Image",
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .preferredWidth(125.dp)
                    .preferredHeight(125.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }



}

@Composable
fun ShowIngredients(recipe: Recipe) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.Black).padding(start = 5.dp, top = 10.dp)
    ) {


        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.body1.copy(Color.Red,fontSize = 20.sp),
        )

        Divider()

        var allIngredients = ""

        recipe.ingredients?.let {
            it.forEach { ing ->
                allIngredients += "$ing\n "
            }
        }

        Text(
            text = allIngredients,
            style = MaterialTheme.typography.subtitle1.copy(Color.White),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )


    }
}