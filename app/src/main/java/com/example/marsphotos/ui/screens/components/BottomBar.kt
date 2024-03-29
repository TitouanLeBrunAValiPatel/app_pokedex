package com.example.marsphotos.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.marsphotos.R

@Composable
fun BottomBar(
    navToFavorite: () -> Unit,
    navToHome: () -> Unit
              ) {
    BottomAppBar(
        actions = {
            IconButton(onClick = navToHome, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(Icons.Filled.Home, contentDescription = stringResource(id = R.string.nav_home))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navToFavorite,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = stringResource(id = R.string.nav_favorite_pokemon))
            }
        }
    )
}

