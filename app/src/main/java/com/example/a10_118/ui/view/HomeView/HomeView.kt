package com.example.a10_118.ui.view.HomeView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a10_118.R
import com.example.a10_118.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.delay

object DestinasiSplash : DestinasiNavigasi {
    override val route = "splash"
    override val titleRes = "tampilan splash"
}

@Composable
fun Splash(
    onTanamanClick: () -> Unit,
    onPekerjaClick: () -> Unit,
    onCatatanPanenClick: () -> Unit,
    onAktivitasPertanianClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    // Trigger animation when the view is loaded
    LaunchedEffect(Unit) {
        delay(2000) // Delay for 2 seconds
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(Color(0xFF7C4DFF), Color(0xFFB388FF))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Align content to center
        verticalArrangement = Arrangement.Center // Vertically center content
    ) {
        // Drawable Image
        Image(
            painter = painterResource(id = R.drawable.ic_agri), // Replace with your drawable resource
            contentDescription = "Agriculture Icon",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp) // Space below the image
        )

        // Title
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1000)) + scaleIn(animationSpec = tween(1000))
        ) {
            Text(
                text = "Pengelolaan Pertanian",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp) // Space below the title
            )
        }

        // Content (Buttons)
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1000)) + scaleIn(animationSpec = tween(1000))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RegularButton(text = "Tanaman", icon = Icons.Filled.Edit, onClick = onTanamanClick)
                RegularButton(text = "Pekerja", icon = Icons.Filled.Person, onClick = onPekerjaClick)
                RegularButton(text = "Catatan Panen", icon = Icons.Filled.List, onClick = onCatatanPanenClick)
                RegularButton(text = "Aktivitas Pertanian", icon = Icons.Filled.ShoppingCart, onClick = onAktivitasPertanianClick)
            }
        }
    }
}


@Composable
fun RegularButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    val scale = animateFloatAsState(targetValue = 1f, animationSpec = tween(500))

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF4CAF50) // Green
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(60.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
            .scale(scale.value)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp),
                tint = Color(0xFF4CAF50) // Green
            )
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

