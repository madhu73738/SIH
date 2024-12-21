package com.example.agro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(navController: NavController) {
    Column {
        var text by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }
        var items = remember {
            mutableStateListOf(
                "Farm 1",
                "Farm 2",
                "Farm 3"
            )
        }
        val filteredItems by remember {
            derivedStateOf {
                if (text.isEmpty()) items else items.filter { it.contains(text, ignoreCase = true) }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill the entire screen
                .background(Color(0xfefeff))
        ) {

//-------------------------------------------------------------------------------------------

            DockedSearchBar(
                query = text,
                onQueryChange = { text = it },
                onSearch = {

                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Search Farms") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {

                                text = ""
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )

                    }
                },


                modifier = Modifier.align(Alignment.TopCenter)
            ) {


                filteredItems.forEach { item ->
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                text = item
                                active = false

                                when (item) {
                                    "Farm 1" -> navController.navigate("Screen2")

                                    else -> navController.navigate("Screen2")
                                }
                            }
                        ,
                        headlineContent = { Text(item) }
                    )
                }
            }


            androidx.compose.material3.Button(
                onClick = {
                    active = true


                    if (text.isNotEmpty() && !items.contains(text)) {
                        items.add(text)
                        text = ""
                    }
                },
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF000000), // Green background
                contentColor = Color.White,),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("Add Farms")
            }


        }
    }

}

