package com.mperezt.rick.ui.screens.characters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mperezt.rick.R
import com.mperezt.rick.ui.models.CharacterFilterUi
import com.mperezt.rick.ui.models.GenderUi
import com.mperezt.rick.ui.models.StatusUi
import com.mperezt.rick.ui.components.GenericDropdown
import com.mperezt.rick.ui.theme.Elevation
import com.mperezt.rick.ui.theme.FontSize
import com.mperezt.rick.ui.theme.Padding
import com.mperezt.rick.ui.theme.Space

@Composable
fun CharactersFiltersUi(
    initialFilter: CharacterFilterUi = CharacterFilterUi(),
    onApplyFilter: (CharacterFilterUi) -> Unit
) {
    var showFilters by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf(initialFilter) }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                onApplyFilter(filter.copy(name = searchQuery.ifEmpty { null }))
            }
        )
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .padding(Padding.Base, Padding.None,Padding.None, Padding.Base)
                .clickable { showFilters = !showFilters },
            elevation = CardDefaults.cardElevation(Elevation.Small)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(Padding.Small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (showFilters)  stringResource(R.string.characters_hide_filters)else  stringResource(R.string.characters_show_filters),
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    imageVector = if (showFilters) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(visible = showFilters) {
            FiltersPanel(
                filter = filter,
                onFilterChange = { filter = it },
                onApply = {
                    onApplyFilter(filter)
                    showFilters = false
                },
                onClear = {
                    filter = CharacterFilterUi()
                    onApplyFilter(CharacterFilterUi())
                }
            )
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.Base),
        elevation = CardDefaults.cardElevation(Elevation.Small)
    ) {
        TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.characters_search_placeholder)) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
        },
        keyboardActions = KeyboardActions(
            onDone = { onSearch() }
        )
    )}

}

@Composable
private fun FiltersPanel(
    filter: CharacterFilterUi,
    onFilterChange: (CharacterFilterUi) -> Unit,
    onApply: () -> Unit,
    onClear: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Base)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Padding.Base)
                .fillMaxWidth()
        ) {
            Text(text = "Filtros", style = MaterialTheme.typography.titleMedium,
               fontSize = FontSize.Large , modifier = Modifier.padding(Padding.None, Padding.SmallMedium))

            Spacer(modifier = Modifier.height(Space.Base))

            StatusDropdown(
                selectedStatus = filter.status,
                onStatusSelected = { onFilterChange(filter.copy(status = it)) }
            )

            Spacer(modifier = Modifier.height(Space.Small))

            FilterTextField(
                label = stringResource(R.string.characters_filter_species),
                value = filter.species ?: "",
                onValueChange = { onFilterChange(filter.copy(species = it.ifEmpty { null })) }
            )

            Spacer(modifier = Modifier.height(Space.Small))

            FilterTextField(
                label = stringResource(R.string.characters_filter_type),
                value = filter.type ?: "",
                onValueChange = { onFilterChange(filter.copy(type = it.ifEmpty { null })) }
            )

            Spacer(modifier = Modifier.height(Space.Small))

            GenderDropdown(
                selectedGender = filter.gender,
                onGenderSelected = { onFilterChange(filter.copy(gender = it)) }
            )

            Spacer(modifier = Modifier.height(Space.Base))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onClear) {
                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                    Spacer(modifier = Modifier.width(Space.Small))
                    Text("Limpiar")
                }

                Spacer(modifier = Modifier.width(Space.Small))

                Button(onClick = onApply) {
                    Text("Aplicar")
                }
            }
        }
    }
}

@Composable
private fun FilterTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun StatusDropdown(
    selectedStatus: StatusUi?,
    onStatusSelected: (StatusUi?) -> Unit
) {
    val statusOptions = listOf(null) + StatusUi.values().toList()
    GenericDropdown(
        label = "Estado",
        options = statusOptions,
        selectedOption = selectedStatus,
        onOptionSelected = onStatusSelected,
        optionLabel = { it?.name ?: "Todos" }
    )
}

@Composable
fun GenderDropdown(
    selectedGender: GenderUi?,
    onGenderSelected: (GenderUi?) -> Unit
) {
    val genderOptions = listOf(null) + GenderUi.entries
    GenericDropdown(
        label = "Género",
        options = genderOptions,
        selectedOption = selectedGender,
        onOptionSelected = onGenderSelected,
        optionLabel = { it?.name ?: "Todos" }
    )
}