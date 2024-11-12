@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.ui.login.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbotao.swechallenge.components.ErrorComponent
import com.gabrielbotao.swechallenge.components.LoadingComponent
import com.gabrielbotao.swechallenge.navigation.RoutesEnum
import com.gabrielbotao.swechallenge.ui.login.uistate.LoginUIState
import com.gabrielbotao.swechallenge.ui.login.viewmodel.LoginViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import swechallenge.composeapp.generated.resources.Res
import swechallenge.composeapp.generated.resources.button_logo

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val dataState = viewModel.loginState.collectAsState()
    val username by remember { mutableStateOf("") } // "emilys"
    val password by remember { mutableStateOf("") } // "emilyspass"
    var showLoading by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        dataState.value.let { state ->
            when (state) {
                LoginUIState.Loading ->
                    if (showLoading)
                        LoadingComponent()
                    else
                        LayoutLogin(
                            username = username,
                            password = password,
                            modifier = Modifier
                                .padding(innerPadding),
                            onClickEnter = { user, pws ->
                                viewModel.login(user, pws)
                                showLoading = true
                            }
                        )

                is LoginUIState.Login -> {
                    showLoading = false
                    viewModel.saveLoggedInStatus(true)
                    navController.navigate(RoutesEnum.MAIN_SCREEN.key) {
                        popUpTo(RoutesEnum.LOGIN.key) { inclusive = true }
                    }
                }

                LoginUIState.Logout -> {
                    showLoading = false
                    viewModel.saveLoggedInStatus(false)
                    LayoutLogin(
                        username = username,
                        password = password,
                        modifier = Modifier
                            .padding(innerPadding),
                        onClickEnter = { user, pws ->
                            viewModel.login(user, pws)
                            showLoading = true
                        }
                    )
                }

                LoginUIState.Error -> {
                    showLoading = false
                    viewModel.saveLoggedInStatus(false)
                    ErrorComponent(
                        errorMessage = state.toString(),
                        modifier = Modifier,
                        onClickTryAgain = {
                            viewModel.logout()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LayoutLogin(
    username: String,
    password: String,
    modifier: Modifier,
    onClickEnter: (username: String, password: String) -> Unit
) {
    val buttonText by remember { mutableStateOf("Enter") }
    var usernameUpdate by remember { mutableStateOf(username) }
    var passwordUpdate by remember { mutableStateOf(password) }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    var showPassword by remember { mutableStateOf(false) }
    val icon = if (showPassword) Icons.Filled.Close else Icons.Filled.CheckCircle

    val customSelectionColors = TextSelectionColors(
        handleColor = Color.Black,
        backgroundColor = Color.Gray
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Box(modifier = modifier.size(48.dp))
        Image(
            painter = painterResource(Res.drawable.button_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(128.dp)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customSelectionColors) {
            OutlinedTextField(
                isError = usernameError,
                singleLine = true,
                maxLines = 1,
                value = usernameUpdate,
                onValueChange = { usernameUpdate = it },
                placeholder = { Text("User") },
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    cursorColor = Color.Gray
                ),
            )
            OutlinedTextField(
                isError = passwordError,
                singleLine = true,
                maxLines = 1,
                value = passwordUpdate,
                onValueChange = { passwordUpdate = it },
                placeholder = { Text("Password") },
                visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (getInputUserError(usernameUpdate) && getInputPwsError(passwordUpdate)) {
                            usernameError = false
                            passwordError = false
                            onClickEnter.invoke(usernameUpdate, passwordUpdate)
                        }
                        usernameError = !getInputUserError(usernameUpdate)
                        passwordError = !getInputPwsError(passwordUpdate)
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword },
                        content = {
                            if (getInputPwsError(passwordUpdate)) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Show Password"
                                )
                            }
                        }
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    cursorColor = Color.Gray
                )
            )
        }

        Button(
            onClick = {
                if (getInputUserError(usernameUpdate) && getInputPwsError(passwordUpdate)) {
                    usernameError = false
                    passwordError = false
                    onClickEnter.invoke(usernameUpdate, passwordUpdate)
                }
                usernameError = !getInputUserError(usernameUpdate)
                passwordError = !getInputPwsError(passwordUpdate)
            },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                color = Color.White,
                text = buttonText
            )
        }
    }
}

fun getInputUserError(
    username: String
): Boolean = username.isNotEmpty()

fun getInputPwsError(
    password: String
): Boolean = password.isNotEmpty()