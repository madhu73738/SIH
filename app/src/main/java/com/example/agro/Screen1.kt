package com.example.agro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Screen1(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {




        val query = remember { mutableStateOf("") }
        val names = listOf("Region 1", "Region 2", "Region 3")
        val searchResults = names.filter { it.contains(query.value, ignoreCase = true) }


        OutlinedTextField(
            value = query.value,
            onValueChange = { query.value = it },
            label = { Text("Search") },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )


        LazyColumn {
            items(searchResults) { name -> // corrected here
                ListItem(
                    modifier = Modifier
                        .clickable {
                            when (name) {
                                "Region 1" -> navController.navigate("Screen2")
                                "Region 2" -> navController.navigate("Screen2")
                                "Region 3" -> navController.navigate("Screen2")
                            }
                        }
                        .padding(8.dp),
                    headlineContent = { Text(name) } // `text` replaced by `headlineContent`
                )

            }
        }
    }



}