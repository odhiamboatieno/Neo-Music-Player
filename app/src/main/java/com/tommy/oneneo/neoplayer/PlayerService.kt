package com.tommy.oneneo.neoplayer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.tommy.oneneo.neoplayer.player.MediaPlayerHolder
import com.tommy.oneneo.neoplayer.player.MusicNotificationManager

class PlayerService : Service() {

    //service
    private val mIBinder = LocalBinder()
    var isRestoredFromPause = false

    //media player
    var mediaPlayerHolder: MediaPlayerHolder? = null
    lateinit var musicNotificationManager: MusicNotificationManager

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayerHolder != null) {
            mediaPlayerHolder!!.registerNotificationActionsReceiver(false)
            mediaPlayerHolder!!.release()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        if (mediaPlayerHolder == null) {
            mediaPlayerHolder = MediaPlayerHolder(this)
            musicNotificationManager = MusicNotificationManager(this)
            mediaPlayerHolder!!.registerNotificationActionsReceiver(true)
        }
        return mIBinder
    }

    inner class LocalBinder : Binder() {
        val instance: PlayerService
            get() = this@PlayerService
    }
}