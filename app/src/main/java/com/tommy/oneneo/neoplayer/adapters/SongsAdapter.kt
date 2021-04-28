package com.tommy.oneneo.neoplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tommy.oneneo.neoplayer.R
import com.tommy.oneneo.neoplayer.music.Music
import com.tommy.oneneo.neoplayer.music.MusicUtils
import com.tommy.oneneo.neoplayer.player.MediaPlayerHolder
import com.tommy.oneneo.neoplayer.uihelpers.UIUtils
import kotlinx.android.synthetic.main.song_item.view.*

class SongsAdapter(music: MutableList<Music>) : RecyclerView.Adapter<SongsAdapter.SongsHolder>() {

    var onSongClick: ((Music) -> Unit)? = null

    private var mMusic = music

    init {
        mMusic.sortBy { it.track }
    }

    fun swapSongs(music: MutableList<Music>) {
        mMusic = music
        mMusic.sortBy { it.track }
        notifyDataSetChanged()
    }

    fun randomPlaySelectedAlbum(mediaPlayerHolder: MediaPlayerHolder) {
        val currentAlbum = mMusic
        currentAlbum.shuffle()
        val song = currentAlbum[0]
        mediaPlayerHolder.setCurrentSong(song, mMusic)
        mediaPlayerHolder.initMediaPlayer(song)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsHolder {
        return SongsHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mMusic.size
    }

    override fun onBindViewHolder(holder: SongsHolder, position: Int) {
        val track = mMusic[holder.adapterPosition].track
        val title = mMusic[holder.adapterPosition].title
        val duration = mMusic[holder.adapterPosition].duration

        holder.bindItems(track, title, duration)
    }

    inner class SongsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(track: Int, title: String, duration: Long) {
            itemView.track.text = MusicUtils.formatSongTrack(track).toString()
            itemView.title.text = title
            itemView.duration.text = MusicUtils.formatSongDuration(duration)
            itemView.setOnClickListener { onSongClick?.invoke(mMusic[adapterPosition]) }
            UIUtils.setHorizontalScrollBehavior(itemView, itemView.title)
        }
    }
}