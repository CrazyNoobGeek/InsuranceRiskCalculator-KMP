package com.yahyabahloul.riskcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yahyabahloul.riskcalculator.domain.model.DriverProfile
import com.yahyabahloul.riskcalculator.domain.usecase.CalculatePremiumUseCase
import com.yahyabahloul.riskcalculator.domain.usecase.PremiumResult

// Couleurs MMA (Approximatives pour le style)
val MMA_Blue = Color(0xFF004B9B)
val MMA_Orange = Color(0xFFF47920)

@Composable
fun App() {
    MaterialTheme {
        // Surface gère le fond blanc propre
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F5F5) // Gris
        ) {
            // Le state
            var ageText by remember { mutableStateOf("") }
            var licenseYearsText by remember { mutableStateOf("") }
            var resultMessage by remember { mutableStateOf("") }
            var resultColor by remember { mutableStateOf(Color.Gray) }

            val calculateUseCase = remember { CalculatePremiumUseCase() }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    //  empêche le texte d'être caché sous l'encoche iPhone :
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                // Titre
                Text(
                    text = "Risk Simulator",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MMA_Blue
                )
                Text(
                    text = "Module de tarification KMP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Carte du Formulaire (Effet "Elevé")
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Champ Âge
                        OutlinedTextField(
                            value = ageText,
                            onValueChange = { ageText = it },
                            label = { Text("Âge du conducteur") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Champ Permis
                        OutlinedTextField(
                            value = licenseYearsText,
                            onValueChange = { licenseYearsText = it },
                            label = { Text("Années de permis") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                val age = ageText.toIntOrNull() ?: 0
                                val years = licenseYearsText.toIntOrNull() ?: 0
                                val profile = DriverProfile(age, years)
                                val result = calculateUseCase(profile)

                                when (result) {
                                    is PremiumResult.YoungDriver -> {
                                        resultMessage = "Surprime Appliquée\n${result.basePrice}€ / an"
                                        resultColor = MMA_Orange // Orange
                                    }
                                    is PremiumResult.Experienced -> {
                                        resultMessage = "Tarif Bonus\n${result.basePrice}€ / an"
                                        resultColor = Color(0xFF2E7D32) // Vert
                                    }
                                }
                            },
                            enabled = ageText.isNotEmpty() && licenseYearsText.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(containerColor = MMA_Blue),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Calculer ma prime", fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Résultat
                if (resultMessage.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = resultColor.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = resultMessage,
                            style = MaterialTheme.typography.headlineSmall,
                            color = resultColor,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(24.dp).fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}