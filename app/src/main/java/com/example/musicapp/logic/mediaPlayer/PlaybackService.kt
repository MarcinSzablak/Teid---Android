package com.example.musicapp.logic.mediaPlayer

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlaybackService : MediaSessionService() {
    override fun onCreate() {
        super.onCreate()
        AppExoPlayer.createPlayer(context = this)
        AppMediaSession.setUpMediaSession(context = this, player = AppExoPlayer.player!!)
    }

    override fun onDestroy() {
        AppMediaSession.mediaSession?.run {
            player.release()
            release()
            AppMediaSession.mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return AppMediaSession.mediaSession
    }
}