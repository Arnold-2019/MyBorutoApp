package com.example.myborutoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myborutoapp.navigation.SetUpNavGraph
import com.example.myborutoapp.ui.theme.MyBorutoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBorutoAppTheme {
                navHostController = rememberNavController()
                SetUpNavGraph(navHostController = navHostController)
            }
        }
    }
}
