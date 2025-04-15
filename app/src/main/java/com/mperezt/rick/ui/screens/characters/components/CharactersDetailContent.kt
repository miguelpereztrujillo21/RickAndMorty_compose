package com.mperezt.rick.ui.screens.characters.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mperezt.rick.R
import com.mperezt.rick.ui.mappers.StatusColorMapper
import com.mperezt.rick.ui.models.CharacterUi
import com.mperezt.rick.ui.theme.IconSize
import com.mperezt.rick.ui.theme.Padding
import com.mperezt.rick.ui.theme.Radius

@Composable
fun CharacterDetailContent(
    character: CharacterUi,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Contenido principal con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Portada con gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Imagen de portada
                AsyncImage(
                    model = character.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0f,
                                endY = 600f
                            )
                        )
                )

                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier
                        .padding(Padding.Small)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = Padding.Base, bottom = Padding.Base)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.Base)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.offset(y = (-40).dp),
                        shape = RoundedCornerShape(Radius.ExtraLarge),
                        colors = CardDefaults.cardColors(StatusColorMapper.mapToColor(character.status))
                    ) {
                        Text(
                            text = character.status,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = Padding.Medium, vertical = Padding.Small)
                        )
                    }

                    Spacer(modifier = Modifier.width(Padding.Small))

                    Card(
                        modifier = Modifier
                            .size(IconSize.Massive)
                            .offset(y = (-40).dp),
                        shape = CircleShape,
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Padding.Small))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.Base),
                shape = RoundedCornerShape(Radius.Large),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Padding.Base)
                ) {
                    Text(
                        text = "Información del personaje",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = Padding.Small)
                    )

                    CharacterInfoItem(
                        icon = Icons.Default.Person,
                        title = stringResource(R.string.characters_status),
                        value = character.status
                    )

                    CharacterInfoItem(
                        icon = Icons.Default.Search,
                        title = stringResource( R.string.characters_species),
                        value = character.species
                    )

                    CharacterInfoItem(
                        icon = Icons.Default.Face,
                        title = stringResource(R.string.characters_gender),
                        value = character.gender
                    )

                    CharacterInfoItem(
                        icon = Icons.Default.Place,
                        title = stringResource(R.string.characters_origin),
                        value = character.origin.name
                    )

                    CharacterInfoItem(
                        icon = Icons.Default.LocationOn,
                        title = stringResource(R.string.characters_location),
                        value = character.location.name
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.Base, vertical = Padding.Small),
                shape = RoundedCornerShape(Radius.Large),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Padding.Base),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Filled.DateRange,
                        value = "Apariciones",
                        count = character.episode.size.toString()
                    )

                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )

                    StatItem(
                        icon = Icons.Default.Star,
                        value = "Creado",
                        count = "Hace " + character.created.substring(0, 10)
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(IconSize.Medium)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    count: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = count,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}