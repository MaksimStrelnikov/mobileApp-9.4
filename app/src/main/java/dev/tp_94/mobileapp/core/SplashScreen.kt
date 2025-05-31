package dev.tp_94.mobileapp.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.presentation.LoginViewModel

@Composable
fun SplashNavigationScreen(viewModel: LoginViewModel = hiltViewModel(), onResult: (User?) -> Unit) {
    val session = viewModel.session.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val user = session.value?.user
        onResult(user)
    }

    Box(modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.background)), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = colorResource(R.color.dark_text))
    }
}