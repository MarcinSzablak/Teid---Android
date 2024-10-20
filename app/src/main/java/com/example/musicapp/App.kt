package com.example.musicapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.example.musicapp.bottomBar.BottomBarCustom
import com.example.musicapp.mainContent.AlbumsList
import com.example.musicapp.mainContent.DirectorySelectionUi
import com.example.musicapp.musicFilesUsage.AlbumsWhichExists
import com.example.musicapp.musicFilesUsage.setUpDatabase
import com.example.musicapp.onStartApp.changeNotValidDirectoryPathToUri
import com.example.musicapp.onStartApp.getAlbumsFromDirectory
import com.example.musicapp.onStartApp.synchronizeAlbums
import com.example.musicapp.searchBar.SearchBar
import com.example.musicapp.settings.SettingsDataStore
import com.example.musicapp.topAppBar.TopAppBarCustom
import com.example.musicapp.ui.theme.MusicAppTheme
import getAlbumsFromDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "Range"
)
@Composable
fun App(){
    val context = LocalContext.current

    val uri = remember { mutableStateOf<Uri?>(null)}

    GlobalScope.launch {
        val settings = SettingsDataStore(context)

        settings.directoryPathFlow.collect { directoryPath ->
            uri.value = changeNotValidDirectoryPathToUri(directoryPath)
            if(uri.value!=null){
                val albumsFromDatabase = getAlbumsFromDatabase(context = context)
                AlbumsWhichExists.saveToList(albumsFromDatabase)
                val albumsInDirectory = getAlbumsFromDirectory(
                    context = context,
                    uri = uri.value,
                )
                AlbumsWhichExists.saveToList(albumsInDirectory)
                synchronizeAlbums(
                    albumsFromDatabase = albumsFromDatabase,
                    albumsInDirectory = albumsInDirectory,
                    context = context,
                )
            }
        }
    }

    MusicAppTheme {
        Scaffold(
            topBar = { TopAppBarCustom() },
            bottomBar = { if(true) BottomBarCustom() },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                SearchBar(
                    modifier = Modifier
                        .zIndex(1f)
                )
                //uri.value == null = false
                if(uri.value.toString() == "null"){
                    DirectorySelectionUi(uri = uri)
                }
                else{
                    AlbumsList()
                }
            }
        }
    }
}