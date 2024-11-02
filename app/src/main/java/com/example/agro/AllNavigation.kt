package com.example.agro

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AllNavigation(){
    val NavController= rememberNavController()
    NavHost(navController = NavController, startDestination = "Screen1"){
        composable("Screen1"){
            Screen1(navController = NavController)
        }
        composable("Screen2"){
            Screen2()
        }}}