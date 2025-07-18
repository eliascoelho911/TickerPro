package com.eliascoelho911.tickerpro.transaction.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eliascoelho911.tickerpro.transaction.domain.model.Asset
import com.eliascoelho911.tickerpro.transaction.domain.model.AssetType
import com.eliascoelho911.tickerpro.transaction.domain.model.TransactionType
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransactionScreen(
    onNavigateBack: () -> Unit,
    viewModel: NewTransactionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(viewModel) {
        viewModel.action.collect { action ->
            when (action) {
                NewTransactionAction.NavigateBack -> onNavigateBack()
                NewTransactionAction.TransactionSaved -> onNavigateBack()
                is NewTransactionAction.ShowError -> {
                    // TODO: Show snackbar or toast
                }
            }
        }
    }

    if (state.showConfirmation) {
        ConfirmationDialog(
            state = state,
            onIntent = viewModel::onIntent
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Transação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Asset Search Section
            AssetSearchSection(
                state = state,
                onIntent = viewModel::onIntent
            )

            // Transaction Type Section
            TransactionTypeSection(
                selectedType = state.transactionType,
                onIntent = viewModel::onIntent
            )

            // Form Fields
            TransactionFormFields(
                state = state,
                onIntent = viewModel::onIntent
            )

            // Summary
            if (state.selectedAsset != null && state.totalValue != null) {
                TransactionSummary(state = state)
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.onIntent(NewTransactionIntent.ClearForm) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpar")
                }
                
                Button(
                    onClick = { viewModel.onIntent(NewTransactionIntent.ShowConfirmation) },
                    enabled = state.isFormValid && !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssetSearchSection(
    state: NewTransactionState,
    onIntent: (NewTransactionIntent) -> Unit
) {
    Column {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { onIntent(NewTransactionIntent.SearchAssets(it)) },
            label = { Text("Buscar Ativo") },
            placeholder = { Text("Digite o código ou nome do ativo") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = state.errors.containsKey("asset"),
            supportingText = state.errors["asset"]?.let { { Text(it) } }
        )

        if (state.searchResults.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
            ) {
                LazyColumn {
                    items(state.searchResults) { asset ->
                        ListItem(
                            headlineContent = { Text(asset.symbol) },
                            supportingContent = { Text(asset.name) },
                            modifier = Modifier.clickable { 
                                onIntent(NewTransactionIntent.SelectAsset(asset))
                            }
                        )
                    }
                }
            }
        }

        state.selectedAsset?.let { asset ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItem(
                    headlineContent = { Text(asset.symbol) },
                    supportingContent = { Text(asset.name) },
                    trailingContent = { 
                        Text(
                            "Selecionado",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TransactionTypeSection(
    selectedType: TransactionType,
    onIntent: (NewTransactionIntent) -> Unit
) {
    Column {
        Text(
            text = "Tipo de Transação",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                onClick = { onIntent(NewTransactionIntent.ChangeTransactionType(TransactionType.BUY)) },
                label = { Text("Compra") },
                selected = selectedType == TransactionType.BUY,
                modifier = Modifier.weight(1f)
            )
            
            FilterChip(
                onClick = { onIntent(NewTransactionIntent.ChangeTransactionType(TransactionType.SELL)) },
                label = { Text("Venda") },
                selected = selectedType == TransactionType.SELL,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionFormFields(
    state: NewTransactionState,
    onIntent: (NewTransactionIntent) -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = state.quantity,
            onValueChange = { onIntent(NewTransactionIntent.ChangeQuantity(it)) },
            label = { Text("Quantidade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(1f),
            isError = state.errors.containsKey("quantity"),
            supportingText = state.errors["quantity"]?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.price,
            onValueChange = { onIntent(NewTransactionIntent.ChangePrice(it)) },
            label = { Text("Preço (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(1f),
            isError = state.errors.containsKey("price"),
            supportingText = state.errors["price"]?.let { { Text(it) } }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = state.date.format(dateFormatter),
            onValueChange = { },
            label = { Text("Data") },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = state.fees,
            onValueChange = { onIntent(NewTransactionIntent.ChangeFees(it)) },
            label = { Text("Taxas (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(1f),
            isError = state.errors.containsKey("fees"),
            supportingText = state.errors["fees"]?.let { { Text(it) } }
        )
    }

    OutlinedTextField(
        value = state.notes,
        onValueChange = { onIntent(NewTransactionIntent.ChangeNotes(it)) },
        label = { Text("Observações (opcional)") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 3
    )
}

@Composable
private fun TransactionSummary(state: NewTransactionState) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Resumo da Transação",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Valor Total:")
                Text(
                    text = "R$ ${state.totalValue}",
                    fontWeight = FontWeight.Medium
                )
            }
            
            if (state.feesDecimal.compareTo(java.math.BigDecimal.ZERO) > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Taxas:")
                    Text("R$ ${state.feesDecimal}")
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Custo Total:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "R$ ${state.totalCost}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun ConfirmationDialog(
    state: NewTransactionState,
    onIntent: (NewTransactionIntent) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onIntent(NewTransactionIntent.HideConfirmation) },
        title = { Text("Confirmar Transação") },
        text = {
            Column {
                Text("Confirma a ${if (state.transactionType == TransactionType.BUY) "compra" else "venda"} de:")
                Spacer(modifier = Modifier.height(8.dp))
                Text("• ${state.quantity} cotas de ${state.selectedAsset?.symbol}")
                Text("• Preço unitário: R$ ${state.price}")
                Text("• Valor total: R$ ${state.totalValue}")
                if (state.feesDecimal.compareTo(java.math.BigDecimal.ZERO) > 0) {
                    Text("• Taxas: R$ ${state.feesDecimal}")
                    Text("• Custo total: R$ ${state.totalCost}")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onIntent(NewTransactionIntent.SaveTransaction) },
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Confirmar")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onIntent(NewTransactionIntent.HideConfirmation) }) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun NewTransactionScreenPreview() {
    MaterialTheme {
        NewTransactionScreenContent(
            state = NewTransactionState(
                selectedAsset = Asset(
                    id = "PETR4",
                    symbol = "PETR4", 
                    name = "Petrobras PN",
                    type = AssetType.STOCK
                ),
                quantity = "100",
                price = "25.50",
                fees = "10.00",
                date = LocalDate.now()
            ),
            onIntent = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewTransactionScreenEmptyPreview() {
    MaterialTheme {
        NewTransactionScreenContent(
            state = NewTransactionState(),
            onIntent = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewTransactionScreenWithSearchResultsPreview() {
    MaterialTheme {
        NewTransactionScreenContent(
            state = NewTransactionState(
                searchQuery = "PETR",
                searchResults = listOf(
                    Asset("PETR4", "PETR4", "Petrobras PN", AssetType.STOCK),
                    Asset("PETR3", "PETR3", "Petrobras ON", AssetType.STOCK)
                )
            ),
            onIntent = { },
            onNavigateBack = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewTransactionScreenContent(
    state: NewTransactionState,
    onIntent: (NewTransactionIntent) -> Unit,
    onNavigateBack: () -> Unit
) {
    if (state.showConfirmation) {
        ConfirmationDialog(
            state = state,
            onIntent = onIntent
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Transação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AssetSearchSection(
                state = state,
                onIntent = onIntent
            )

            TransactionTypeSection(
                selectedType = state.transactionType,
                onIntent = onIntent
            )

            TransactionFormFields(
                state = state,
                onIntent = onIntent
            )

            if (state.selectedAsset != null && state.totalValue != null) {
                TransactionSummary(state = state)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onIntent(NewTransactionIntent.ClearForm) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpar")
                }
                
                Button(
                    onClick = { onIntent(NewTransactionIntent.ShowConfirmation) },
                    enabled = state.isFormValid && !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}