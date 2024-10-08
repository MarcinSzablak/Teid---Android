package com.example.musicapp

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.example.musicapp.bottomBar.BottomBarCustom
import com.example.musicapp.musicFilesUsage.Album
import com.example.musicapp.musicFilesUsage.DBHelper
import com.example.musicapp.musicFilesUsage.GetDirectory
import com.example.musicapp.musicFilesUsage.setUpDatabase
import com.example.musicapp.searchBar.SearchBar
import com.example.musicapp.searchBar.SearchShadow
import com.example.musicapp.topAppBar.TopAppBarCustom
import com.example.musicapp.ui.theme.MusicAppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Preview(
    showBackground = true,
    widthDp = 300,
    heightDp = 650,
)
@Composable
fun App(){
    val isPlaying: Boolean = true

    val context = LocalContext.current

    val database = setUpDatabase(context)

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            database.openDatabase()
        }
    }

    MusicAppTheme {
        Scaffold(
            topBar = { TopAppBarCustom() },
            bottomBar = { if(isPlaying) BottomBarCustom() },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                SearchBar(
                    modifier = Modifier
                        .zIndex(1f)
                )

                GetDirectory(
                    database = database
                )
            }
        }
    }
}