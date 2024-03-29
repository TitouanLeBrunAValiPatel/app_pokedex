package com.example.marsphotos.ui.screens.components

import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.marsphotos.R
import com.example.marsphotos.ui.screens.home.Home
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopAppBar(
    currentScreen: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = currentScreen,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = {
            if(currentScreen != Home.Title.value) {
                IconButton(onClick = navigateBack ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                        id = R.string.button_back
                    ))
                }
            }

        },
        modifier = modifier
    )

}