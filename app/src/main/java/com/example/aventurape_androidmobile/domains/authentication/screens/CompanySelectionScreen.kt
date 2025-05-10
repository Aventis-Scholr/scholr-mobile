package com.example.aventurape_androidmobile.domains.authentication.screens

import android.R.attr.onClick
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.SignUpViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily

@Composable
fun CompanySelectionScreen(viewModel: SignUpViewModel,navController: NavHostController) {
    val companies = listOf(
        R.drawable.backus to "backus",
        R.drawable.pepsico to "pepsico",
        R.drawable.nestle to "nestle",
        R.drawable.paraiso to "paraiso"
    )

    var selectedCompany by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDCF1F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Logo en círculo
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFF2A3D66))
                    .padding(30.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.scholrlogo),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Elija la empresa a la\nque pertenece",
                fontSize = 20.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                companies.forEach { (logoId, name) ->
                    val isSelected = selectedCompany == name
                    Button(
                        onClick = {
                            selectedCompany= name
                                viewModel.SetCompany(name)
                                viewModel.setRole("ROLE_APODERADO")
                            },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(60.dp)
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) Color(0xFF2A3D66) else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F)),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = logoId),
                            contentDescription = name,
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
            }
                Spacer(modifier = Modifier.height(16.dp))

                // Botón Continuar
                Button(
                    onClick = {
                        navController.navigate(NavScreenAdventurer.signup_screen.name)
                    },
                    enabled = selectedCompany != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCompany != null) Color(0xFF2A3D66) else Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Continuar",
                        fontSize = 18.sp,
                        fontFamily = cabinFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
