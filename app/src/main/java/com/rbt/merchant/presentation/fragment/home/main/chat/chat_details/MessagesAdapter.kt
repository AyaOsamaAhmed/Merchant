package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rbt.merchant.databinding.ChatItemLeftBinding
import com.rbt.merchant.databinding.ChatItemRightBinding
import com.rbt.merchant.databinding.ChatTimeItemBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.MessagesModel
import java.io.File
import java.io.FileInputStream


private const val SENDER_LAYOUT = 1
private const val RECEIVER_LAYOUT = 2
private const val TIME_LAYOUT = 3
private const val TAG = "MessagesAdapter"

class MessagesAdapter :
    ListAdapter<MessagesModel, RecyclerView.ViewHolder>(MessagesModelDiffCallback()) {
    private lateinit var listener: OnItemClickListener
    private lateinit var bindingSend: ChatItemRightBinding
    private lateinit var bindingReceive: ChatItemLeftBinding
    private lateinit var bindingTime: ChatTimeItemBinding

    // player
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false
    private var seekbarHandler: Handler? = null
    private var updateSeekbar: Runnable? = null
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        bindingSend = ChatItemRightBinding.inflate(layoutInflater, parent, false)
        bindingReceive = ChatItemLeftBinding.inflate(layoutInflater, parent, false)
        bindingTime = ChatTimeItemBinding.inflate(layoutInflater, parent, false)
        seekbarHandler = Handler()
        mediaPlayer = MediaPlayer()
        return when (viewType) {
            SENDER_LAYOUT -> {
                // Log.d(TAG, "onCreateViewHolder: SENDER_LAYOUT")
                SenderViewHolder(bindingSend)
            }
            RECEIVER_LAYOUT -> {
                //Log.d(TAG, "onCreateViewHolder: RECEIVER_LAYOUT")
                ReceiverViewHolder(bindingReceive)
            }
            TIME_LAYOUT -> {
                //Log.d(TAG, "onCreateViewHolder: TIME_LAYOUT")
                TimeViewHolder(bindingTime)
            }
            else -> {
                //Log.d(TAG, "onCreateViewHolder: view from else")
                SenderViewHolder(bindingSend)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when (getItem(position).viewType) {
            SENDER_LAYOUT -> {
                return SENDER_LAYOUT
            }
            RECEIVER_LAYOUT -> {
                return RECEIVER_LAYOUT
            }
            TIME_LAYOUT -> {
                return TIME_LAYOUT
            }
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).viewType) {
            SENDER_LAYOUT -> {
                (holder as SenderViewHolder).bind(getItem(position))
            }
            RECEIVER_LAYOUT -> {
                (holder as ReceiverViewHolder).bind(getItem(position))
            }
            TIME_LAYOUT -> {
                (holder as TimeViewHolder).bind(getItem(position))
            }
        }

    }

    class MessagesModelDiffCallback : DiffUtil.ItemCallback<MessagesModel>() {
        override fun areItemsTheSame(oldItem: MessagesModel, newItem: MessagesModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: MessagesModel, newItem: MessagesModel): Boolean {
            return oldItem == newItem
        }
    }

    // inner class impl for 3 layouts
    inner class SenderViewHolder(binding: ChatItemRightBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ChatItemRightBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            if (!item.voicePath.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.VISIBLE
                itemRowBinding.imgCard.visibility = View.GONE
                itemRowBinding.sendMessageTv.visibility = View.GONE
                itemRowBinding.voicePlay.setOnClickListener {
                    initializeMediaPlayer(item.voicePath,itemRowBinding.voicePlay,itemRowBinding.voicePause)
                    Log.d(TAG, "bind: filePath: ${item.voicePath}")
                    /*itemRowBinding.voicePause.visibility = View.VISIBLE
                    itemRowBinding.voicePlay.visibility = View.GONE*/
                    itemRowBinding.voiceSeekBar.max = mediaPlayer?.duration!!
                    itemRowBinding.voiceSeekBar.postDelayed(updateSeekbar, 0)
                    updateRunnable(itemRowBinding.voiceSeekBar)
                }
                itemRowBinding.voicePause.setOnClickListener {
                    /*itemRowBinding.voicePause.visibility = View.GONE
                    itemRowBinding.voicePlay.visibility = View.VISIBLE*/
                    seekbarHandler?.removeCallbacks(updateSeekbar!!)
                    stopPlayRecord(itemRowBinding.voicePlay,itemRowBinding.voicePause)
                }
            }
            if (!item.imageURL.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.GONE
                itemRowBinding.imgCard.visibility = View.VISIBLE
                itemRowBinding.sendMessageTv.visibility = View.GONE
                Glide.with(context!!).load(item.imageURL).into(itemRowBinding.sendMessageImg)
            }
            if (!item.message.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.GONE
                itemRowBinding.imgCard.visibility = View.GONE
                itemRowBinding.sendMessageTv.visibility = View.VISIBLE
            }
            itemRowBinding.executePendingBindings()
        }
    }

    inner class ReceiverViewHolder(binding: ChatItemLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ChatItemLeftBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            if (!item.voicePath.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.VISIBLE
                itemRowBinding.imgCard.visibility = View.GONE
                itemRowBinding.receiveMessageTv.visibility = View.GONE
                itemRowBinding.voicePlay.setOnClickListener {
                    initializeMediaPlayer(item.voicePath,itemRowBinding.voicePlay,itemRowBinding.voicePause)
                    Log.d(TAG, "bind: filePath: ${item.voicePath}")
                  /*  itemRowBinding.voicePause.visibility = View.VISIBLE
                    itemRowBinding.voicePlay.visibility = View.GONE*/
                    itemRowBinding.voiceSeekBar.max = mediaPlayer!!.duration
                    updateRunnable(itemRowBinding.voiceSeekBar)
                    itemRowBinding.voiceSeekBar.postDelayed(updateSeekbar, 0)
                }
                itemRowBinding.voicePause.setOnClickListener {
                    /*itemRowBinding.voicePause.visibility = View.GONE
                    itemRowBinding.voicePlay.visibility = View.VISIBLE*/
                    seekbarHandler?.removeCallbacks(updateSeekbar!!)
                    stopPlayRecord(itemRowBinding.voicePlay,itemRowBinding.voicePause)
                }

            }
            if (!item.imageURL.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.GONE
                itemRowBinding.imgCard.visibility = View.VISIBLE
                itemRowBinding.receiveMessageTv.visibility = View.GONE
                Glide.with(context!!).load(item.imageURL).into(itemRowBinding.receiverMessageImg)
            }
            if (!item.message.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.GONE
                itemRowBinding.imgCard.visibility = View.GONE
                itemRowBinding.receiveMessageTv.visibility = View.VISIBLE
            }
            itemRowBinding.executePendingBindings()
        }
    }

    inner class TimeViewHolder(binding: ChatTimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ChatTimeItemBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            itemRowBinding.executePendingBindings()
        }
    }

    private fun updateRunnable(playerSeekbar: SeekBar) {
        updateSeekbar = Runnable {
            mediaPlayer!!.reset()
            playerSeekbar.progress = mediaPlayer!!.currentPosition
            seekbarHandler!!.postDelayed(updateSeekbar!!, 0)
        }
    }

    private fun initializeMediaPlayer(fileURL: String,play_btn:ImageView,pauseBtn:ImageView) {
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()
        )
        mediaPlayer!!.setOnCompletionListener { mp ->
            if (mp != null) {
                stopPlayRecord(play_btn,pauseBtn)
            }
        }
        try {
            startPlayRecord(fileURL,play_btn,pauseBtn)
        } catch (e: Exception) {
            Log.d(TAG, "initializeMediaPlayer: ERROR: ${e.message}")
        }
    }

    private fun stopPlayRecord(play_btn:ImageView,pauseBtn:ImageView) {
        if (isPlaying) {
            pauseBtn.visibility = View.GONE
            play_btn.visibility = View.VISIBLE
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            mediaPlayer = MediaPlayer()
            isPlaying = false
        }
    }

    private fun startPlayRecord(fileURL: String,play_btn:ImageView,pauseBtn:ImageView) {
        val tempFile = File(fileURL)
        val fis = FileInputStream(tempFile)
        pauseBtn.visibility = View.VISIBLE
        play_btn.visibility = View.GONE
        if (!isPlaying) {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(fis.fd)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            isPlaying = true
        }
    }

    interface OnItemClickListener {
        fun onClick(item: MessagesModel)
    }
}