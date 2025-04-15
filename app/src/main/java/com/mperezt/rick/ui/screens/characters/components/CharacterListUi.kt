package com.mperezt.rick.ui.screens.characters.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mperezt.rick.ui.models.CharacterUi
import com.mperezt.rick.ui.theme.BlueLight
import com.mperezt.rick.ui.theme.Elevation
import com.mperezt.rick.ui.theme.IconSize
import com.mperezt.rick.ui.theme.LightBlue
import com.mperezt.rick.ui.theme.Padding
import com.mperezt.rick.ui.theme.Stroke

@Composable
fun CharacterListItem(character: CharacterUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.Medium)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        elevation = CardDefaults.cardElevation(Elevation.Medium),
    ) {
        Row(
            modifier = Modifier.padding(Padding.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen con indicador de estado
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(IconSize.Massive)
                        .clip(CircleShape)
                        .border(Stroke.Small,BlueLight, CircleShape),
                    contentScale = ContentScale.Crop
                )

                // Indicador de estado (vivo, muerto, etc.)
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            when (character.status) {
                                "Alive" -> Color.Green
                                "Dead" -> Color.Red
                                else -> Color.Gray
                            },
                            CircleShape
                        )
                        .border(1.dp, Color.White, CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(Padding.Base))

            // Información del personaje
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = character.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(Padding.Small))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = character.status,
                        style = MaterialTheme.typography.bodyMedium,
                        color = when (character.status) {
                            "Alive" -> Color.Green.copy(alpha = 0.8f)
                            "Dead" -> Color.Red.copy(alpha = 0.8f)
                            else -> Color.Gray
                        }
                    )
                    Text(text = " • ")
                    Text(
                        text = character.species,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (character.type.isNotEmpty()) {
                    Text(
                        text = character.type,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
