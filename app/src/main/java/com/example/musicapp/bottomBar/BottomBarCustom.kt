package com.example.musicapp.bottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.musicapp.R
import com.example.musicapp.musicFilesUsage.MediaPlayerApp

@Composable
fun BottomBarCustom(){
    val context = LocalContext.current

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            //shuffle
            BottomBarButton(
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.baseline_shuffle_24),
                contentDescription = "Play"
            )
            //previous
            BottomBarButton(
                onClick = {
                    MediaPlayerApp.previousSongPlay(
                        context = context
                    )
                },
                painter = painterResource(id = R.drawable.baseline_skip_previous_24),
                contentDescription = "Play"
            )

            BottomBarPlayPauseButton()

            //next
            BottomBarButton(
                onClick = {
                    MediaPlayerApp.nextSongPlay(
                        context = context
                    )
                },
                painter = painterResource(id = R.drawable.baseline_skip_next_24),
                contentDescription = "Play"
            )
            //loop
            BottomBarButton(
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.baseline_replay_24),
                contentDescription = "Play"
            )
        }
    }
}