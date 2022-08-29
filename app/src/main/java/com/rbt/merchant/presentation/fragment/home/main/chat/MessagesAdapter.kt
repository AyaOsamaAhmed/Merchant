package com.rbt.merchant.presentation.fragment.home.main.chat

import android.R.attr.path
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return when (viewType) {
            SENDER_LAYOUT -> {
               // Log.d(TAG, "onCreateViewHolder: SENDER_LAYOUT")
                SenderViewHolder(bindingSend)
            }
            RECEIVER_LAYOUT -> {
             //   Log.d(TAG, "onCreateViewHolder: RECEIVER_LAYOUT")
                ReceiverViewHolder(bindingReceive)
            }
            TIME_LAYOUT -> {
            //    Log.d(TAG, "onCreateViewHolder: TIME_LAYOUT")
                TimeViewHolder(bindingTime)
            }
            else -> {
                Log.d(TAG, "onCreateViewHolder: view from else")
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

    class MessagesModelDiffCallback :
        DiffUtil.ItemCallback<MessagesModel>() {
        override fun areItemsTheSame(oldItem: MessagesModel, newItem: MessagesModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: MessagesModel, newItem: MessagesModel): Boolean {
            return oldItem == newItem
        }
    }


    inner class SenderViewHolder(binding: ChatItemRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ChatItemRightBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            if (!item.voicePath.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.VISIBLE
                itemRowBinding.imgCard.visibility = View.GONE
                itemRowBinding.sendMessageTv.visibility = View.GONE
                itemRowBinding.voicePlay.setOnClickListener {
                    seekbarHandler = Handler()
                    mediaPlayer = MediaPlayer()
                    initializeMediaPlayer(item.voicePath)
                    Log.d(TAG, "bind: filePath: ${item.voicePath}")
                    // mediaPlayer?.setDataSource(item.voicePath)
                    // mediaPlayer?.prepare()
                    itemRowBinding.voicePause.visibility = View.VISIBLE
                    itemRowBinding.voicePlay.visibility = View.GONE
                    // mediaPlayer?.start()
                    itemRowBinding.voiceSeekBar.max = mediaPlayer?.duration!!
                    itemRowBinding.voiceSeekBar.postDelayed(updateSeekbar, 0)
                    updateRunnable(itemRowBinding.voiceSeekBar)
                }
                itemRowBinding.voicePause.setOnClickListener {
                    itemRowBinding.voicePause.visibility = View.GONE
                    itemRowBinding.voicePlay.visibility = View.VISIBLE
                    seekbarHandler?.removeCallbacks(updateSeekbar!!)
                    mediaPlayer!!.reset()
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
                    mediaPlayer = null
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

    inner class ReceiverViewHolder(binding: ChatItemLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ChatItemLeftBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            if (!item.voicePath.isNullOrEmpty()) {
                itemRowBinding.messageVoiceLayout.visibility = View.VISIBLE
                itemRowBinding.imgCard.visibility = View.GONE
                itemRowBinding.receiveMessageTv.visibility = View.GONE
                itemRowBinding.voicePlay.setOnClickListener {
                    seekbarHandler = Handler()
                    mediaPlayer = MediaPlayer()
                    initializeMediaPlayer(item.voicePath)
                    Log.d(TAG, "bind: filePath: ${item.voicePath}")
                    /*mediaPlayer?.setDataSource(item.voicePath)
                    mediaPlayer?.prepare()*/
                    itemRowBinding.voicePause.visibility = View.VISIBLE
                    itemRowBinding.voicePlay.visibility = View.GONE
                    //mediaPlayer?.start()
                    itemRowBinding.voiceSeekBar.max = mediaPlayer!!.duration
                    updateRunnable(itemRowBinding.voiceSeekBar)
                    itemRowBinding.voiceSeekBar.postDelayed(updateSeekbar, 0)
                }
                itemRowBinding.voicePause.setOnClickListener {
                    itemRowBinding.voicePause.visibility = View.GONE
                    itemRowBinding.voicePlay.visibility = View.VISIBLE
                    seekbarHandler?.removeCallbacks(updateSeekbar!!)
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    mediaPlayer?.release()
                    mediaPlayer = null
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

    inner class TimeViewHolder(binding: ChatTimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

    private fun initializeMediaPlayer(fileURL: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
        } else {
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
        try {
            mediaPlayer!!.setOnPreparedListener { mp ->
                if (mp != null) {
                    when (mp.isPlaying) {
                        true -> {
                            mp.stop()
                            mp.reset()
                            mp.release()
                        }
                        false -> {
                            mp.start()
                        }
                    }
                }
            }
            val tempFile: File = File(fileURL)
            val fis = FileInputStream(tempFile)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(fis.fd)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: Exception) {
            Log.d(TAG, "initializeMediaPlayer: ")
            e.printStackTrace()
        }
    }

    interface OnItemClickListener {
        fun onClick(item: MessagesModel)
    }
}