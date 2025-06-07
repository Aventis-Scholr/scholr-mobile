package com.example.aventurape_androidmobile.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R

@Composable

fun Drawer(nav: NavHostController){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4C542))
    ) {
        Text(
            text = "Scholr",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Email,
                    contentDescription = null,
                    tint = Color.Black

                )
            },
            label = {
                Text(
                    text = "Bandeja",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            },
            selected = false,
            onClick = { nav.navigate("bandeja_apoderado_screen") }
        )

        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Face,
                    contentDescription = null,
                    tint = Color.Black

                )
            },
            label = {
                Text(
                    text = "Tutorial",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            },
            selected = false,
            onClick = { nav.navigate("tutorial_screen") }
        )

        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            label = {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            },
            selected = false,
            onClick = {
                PreferenceManager.clearUser(context)
                nav.navigate("login_screen") {
                    popUpTo(0) // Limpia el backstack
                }
            }
        )

        /*
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = Color.Black

                )
            },
            label = {
                Text(
                    text = "Close",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            },
            selected = false,
            onClick = {}
        )*/
    }
}
