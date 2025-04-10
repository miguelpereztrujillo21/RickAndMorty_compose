package com.mperezt.rick.ui.screens.characters.components

import androidx.compose.runtime.Composable



@Composable
fun CharactersFiltersUi(
/*    initialFilters: CharacterFilters = CharacterFilters(),
    onApplyFilters: (CharacterFilters) -> Unit*/
) {/*{
    var showFilters by remember { mutableStateOf(false) }
    var filters by remember { mutableStateOf(initialFilters) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Barra de búsqueda con botón de filtro
        SearchBar(
            query = filters.name,
            onQueryChange = { filters = filters.copy(name = it) },
            onSearch = { onApplyFilters(filters) },
            onToggleFilters = { showFilters = !showFilters }
        )

        // Panel de filtros expandible
        AnimatedVisibility(visible = showFilters) {
            FiltersPanel(
                filters = filters,
                onFiltersChange = { filters = it },
                onApply = {
                    onApplyFilters(filters)
                    showFilters = false
                },
                onClear = {
                    filters = CharacterFilters()
                    onApplyFilters(CharacterFilters())
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
            .padding(16.dp),
        placeholder = { Text("Buscar personajes...") },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        },
        trailingIcon = {
            IconButton(onClick = onToggleFilters) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtros"
                )
            }
        },
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
            onDone = { onSearch() }
        )
    )
}

@Composable
private fun FiltersPanel(
    filters: CharacterFilters,
    onFiltersChange: (CharacterFilters) -> Unit,
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
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Status filter (dropdown)
            FilterDropdown(
                label = "Estado",
                options = listOf("", "alive", "dead", "unknown"),
                selectedOption = filters.status,
                onOptionSelected = { onFiltersChange(filters.copy(status = it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Species filter (text field)
            FilterTextField(
                label = "Especie",
                value = filters.species,
                onValueChange = { onFiltersChange(filters.copy(species = it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Type filter (text field)
            FilterTextField(
                label = "Tipo",
                value = filters.type,
                onValueChange = { onFiltersChange(filters.copy(type = it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gender filter (dropdown)
            FilterDropdown(
                label = "Género",
                options = listOf("", "female", "male", "genderless", "unknown"),
                selectedOption = filters.gender,
                onOptionSelected = { onFiltersChange(filters.copy(gender = it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
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
private fun FilterDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Box {
            OutlinedTextField(
                value = if (selectedOption.isEmpty()) "Todos" else selectedOption,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Expandir"
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(if (option.isEmpty()) "Todos" else option)
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }*/
}