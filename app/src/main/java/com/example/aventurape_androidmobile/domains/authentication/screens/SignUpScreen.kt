package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.SignUpViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

// Define custom colors (matching login screen)
private val PrimaryBrown = Color(0xFF765532)
private val LightBrown = Color(0xFF9B7B5B)
private val DarkBrown = Color(0xFF523A23)
private val BackgroundColor = Color(0xFFDCF1F9)
private val TextPrimaryColor = Color(0xFF2D1810)
private val TextSecondaryColor = Color(0xFF5C4332)

@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavHostController) {
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (currentFocusRequester, nextFocusRequester) = remember { FocusRequester.createRefs() }
    val context = LocalContext.current

    LaunchedEffect(state.signupSuccess) {
        if (state.signupSuccess) {
            viewModel.resetRole()
            navController.navigate(NavScreenAdventurer.login_screen.name)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()), // <- permite scroll,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp) // Define el tamaño del círculo (ancho y alto deben ser iguales)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape // Usa CircleShape para la sombra
                    )
                    .clip(CircleShape) // Usa CircleShape para recortar el contenido en forma circular
                    .background(Color(0xFF2A3D66)) // Puedes usar Color(0xFF2A3D66) o DeepBlue si lo definiste
                    .padding(35.dp) // Padding dentro del círculo antes de la imagen
            ) {
                Image(
                    painter = painterResource(id = R.drawable.scholrlogo),
                    contentDescription = "Logo Scholr",
                    modifier = Modifier.fillMaxSize(), // La imagen llenará el espacio dentro del círculo (menos el padding)
                    contentScale = ContentScale.Fit // Ajusta la imagen para que quepa sin distorsión
                )
            }
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = TextSecondaryColor
                    )
                    Text(
                        text = "Crear Cuenta",
                        fontFamily = cabinFamily,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryColor,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { viewModel.inputData(it,state.compania,state.dni,state.cod_colaborador, state.password, state.confirmPassword) },
                        label = { Text("Usuario") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(currentFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.dni,
                        onValueChange = { viewModel.inputData(state.username,state.compania,it,state.cod_colaborador, state.password, state.confirmPassword) },
                        label = { Text("DNI") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(nextFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.cod_colaborador,
                        onValueChange = { viewModel.inputData(state.username,state.compania,state.dni,it,state.password, state.confirmPassword) },
                        label = { Text("Codigo de colaborador") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(nextFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { viewModel.inputData(state.username,state.compania,state.dni,state.cod_colaborador, it, state.confirmPassword) },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(nextFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = { viewModel.inputData(state.username,state.compania,state.dni,state.cod_colaborador, state.password, it) },
                        label = { Text("Confirmar Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        })
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (state.password == state.confirmPassword) {
                                viewModel.viewModelScope.launch {
                                    viewModel.signUpUser(state.username,state.compania,state.dni,state.cod_colaborador, state.password)
                                    val userRole = PreferenceManager.getUserRoles(context)
                                    when {
                                        userRole != null && userRole.contains("ROLE_ADVENTUROUS") -> {
                                            navController.navigate(NavScreenAdventurer.home_adventurer_screen.name)
                                        }
                                        userRole != null && userRole.contains("ROLE_ENTREPRENEUR") -> {
                                            navController.navigate(NavScreenAdventurer.adventure_publication_management.name)
                                        }
                                        else -> {
                                            navController.navigate(NavScreenAdventurer.error_screen.name)
                                        }
                                    }
                                }
                            } else {
                                viewModel.validatePassword(state.password, state.confirmPassword)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2A3D66)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Crear Cuenta",
                            fontSize = 18.sp,
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ClickableText(
                        text = AnnotatedString(
                            text = "¿Ya tienes una cuenta? Inicia sesión",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(
                                        color = PrimaryBrown,
                                        textDecoration = TextDecoration.Underline,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    start = 23,
                                    end = 36
                                )
                            )
                        ),
                        onClick = { offset ->
                            if (offset in 24..38) {
                                viewModel.resetRole()
                                navController.navigate(NavScreenAdventurer.login_screen.name)
                            }
                        }
                    )
                }

        }

        if (state.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { viewModel.resetStateExceptRole() },
                confirmButton = {
                    TextButton(
                        onClick = { viewModel.resetStateExceptRole() },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PrimaryBrown
                        )
                    ) {
                        Text("Entendido")
                    }
                },
                title = {
                    Text(
                        "Error de registro",
                        color = TextPrimaryColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        state.errorMessage ?: "Ha ocurrido un error desconocido.",
                        color = TextSecondaryColor
                    )
                },
                containerColor = Color.White
            )
        }
    }
}