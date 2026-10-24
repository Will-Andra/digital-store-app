package com.example.digitalstore.features.users.presentation.components

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

@Composable
fun UserSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search by name or email...") },
        label = { Text(text = "User Name") },
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
fun UserSearchBarPreview(){
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UserSearchBar(
                query = "Common User",
                onQueryChange = {}
            )
        }
    }

}