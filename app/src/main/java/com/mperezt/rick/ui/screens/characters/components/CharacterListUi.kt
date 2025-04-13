package com.mperezt.rick.ui.screens.characters.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mperezt.rick.ui.models.CharacterUi
import com.mperezt.rick.ui.theme.Padding
import com.mperezt.rick.ui.theme.Space


@Composable
fun CharacterListItem(character: CharacterUi, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.Base)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(Space.Base)
    ) {
        Image(painter = rememberAsyncImagePainter(character.image), contentDescription = null, modifier = Modifier.size(50.dp))
        Column {
            Text(text = character.name, fontWeight = FontWeight.Bold)
            Text(text = character.species)
        }
    }
}
