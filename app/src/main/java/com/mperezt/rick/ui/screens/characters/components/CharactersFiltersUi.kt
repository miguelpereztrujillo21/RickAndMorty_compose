package com.mperezt.rick.ui.screens.characters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mperezt.rick.domain.models.CharacterFilter
import com.mperezt.rick.domain.models.Gender
import com.mperezt.rick.domain.models.Status
import com.mperezt.rick.ui.components.GenericDropdown
import com.mperezt.rick.ui.theme.Elevation
import com.mperezt.rick.ui.theme.Padding

@Composable
fun CharactersFiltersUi(
    initialFilter: CharacterFilter = CharacterFilter(),
    onApplyFilter: (CharacterFilter) -> Unit
) {
    var showFilters by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf(initialFilter) }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                // Lógica para manejar la búsqueda
                onApplyFilter(filter.copy(name = searchQuery.ifEmpty { null }))
            },
            onToggleFilters = { showFilters = !showFilters }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.Base)
                .clickable { showFilters = !showFilters },
            elevation = CardDefaults.cardElevation(Elevation.Small)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.Base),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (showFilters) "Ocultar filtros" else "Mostrar filtros",
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    imageVector = if (showFilters) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        }

        // Panel de filtros expandible
        AnimatedVisibility(visible = showFilters) {
            FiltersPanel(
                filter = filter,
                onFilterChange = { filter = it },
                onApply = {
                    onApplyFilter(filter)
                    showFilters = false
                },
                onClear = {
                    filter = CharacterFilter()
                    onApplyFilter(CharacterFilter())
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onToggleFilters: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.Base),
        placeholder = { Text("Buscar personajes...") },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
        },
        trailingIcon = {
            IconButton(onClick = onToggleFilters) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Filtros")
            }
        },
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
            onDone = { onSearch() }
        )
    )
}

@Composable
private fun FiltersPanel(
    filter: CharacterFilter,
    onFilterChange: (CharacterFilter) -> Unit,
    onApply: () -> Unit,
    onClear: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Filtros", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            StatusDropdown(
                selectedStatus = filter.status,
                onStatusSelected = { onFilterChange(filter.copy(status = it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            FilterTextField(
                label = "Especie",
                value = filter.species ?: "",
                onValueChange = { onFilterChange(filter.copy(species = it.ifEmpty { null })) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            FilterTextField(
                label = "Tipo",
                value = filter.type ?: "",
                onValueChange = { onFilterChange(filter.copy(type = it.ifEmpty { null })) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            GenderDropdown(
                selectedGender = filter.gender,
                onGenderSelected = { onFilterChange(filter.copy(gender = it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onClear) {
                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Limpiar")
                }

                Spacer(modifier = Modifier.width(8.dp))

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
    selectedStatus: Status?,
    onStatusSelected: (Status?) -> Unit
) {
    val statusOptions = listOf(null) + Status.values().toList()
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
    selectedGender: Gender?,
    onGenderSelected: (Gender?) -> Unit
) {
    val genderOptions = listOf(null) + Gender.entries
    GenericDropdown(
        label = "Género",
        options = genderOptions,
        selectedOption = selectedGender,
        onOptionSelected = onGenderSelected,
        optionLabel = { it?.name ?: "Todos" }
    )
}
@Composable
private fun FilterDropdown(
    selectedGender: Gender?,
    onGenderSelected: (Gender?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf(null) + Gender.values().toList()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Género", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Box {
            OutlinedTextField(
                value = selectedGender?.name ?: "Todos",
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Expandir")
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                genderOptions.forEach { gender ->
                    DropdownMenuItem(
                        text = { Text(gender?.name ?: "Todos") },
                        onClick = {
                            onGenderSelected(gender)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}