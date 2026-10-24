package com.example.digitalstore.features.products.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalstore.features.users.presentation.components.UserSearchBar

@Composable
fun ProductsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search by product or category...") },
        label = { Text(text = "Product name") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        } ,
        trailingIcon = {
            if ( query.isNotEmpty()) {
                IconButton(onClick = {onQueryChange("")}) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }

        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )

}
@Preview(showBackground = true)
@Composable
fun ProductSearchBarPreview(){
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UserSearchBar(
                query = "Product name",
                onQueryChange = {}
            )
        }
    }

}