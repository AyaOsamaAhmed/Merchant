package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details


import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.rbt.merchant.domain.use_case.ui_models.chat.MessagesModel
import com.rbt.merchant.utils.common.CommonFunction
import java.io.File
import java.io.IOException
import java.util.*

private const val SENDER_LAYOUT = 1
private const val RECEIVER_LAYOUT = 2
private const val TIME_LAYOUT = 3
private const val TAG = "ChatDetailsViewModel"
class ChatDetailsViewModel : ViewModel(){
    private var messagesList = ArrayList<MessagesModel>()
    var adapter: MessagesAdapter

    // recorder
    private var output: String? = null
    private var fileName: String? = null
    private var mediaRecorder: MediaRecorder? = null
    var isRecording: MutableLiveData<Boolean> = MutableLiveData(false)
    private val path = "/Merchant/recorded"
    init {
        mediaRecorder = MediaRecorder()
        adapter = MessagesAdapter()
        initialList()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun startRecording(micRecordingBtn: LottieAnimationView, micBtn: ImageView) {
        try {
            fileName = "/recorded${Date()}.mp3"
            output = Environment.getExternalStorageDirectory().absolutePath + fileName
            val appDir = File(Environment.getExternalStorageDirectory().path + path)
            appDir.mkdirs()
            if(appDir.exists()){
                Log.d(TAG, "startRecording: dir is exist")
                output = appDir.path + fileName
            }else{
                Log.d(TAG, "startRecording: dir is not exist")
            }
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder?.setOutputFile(output)
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            isRecording.value = true
            Log.d(TAG, "startRecording: REORDERING..")
            micRecordingBtn.visibility = View.VISIBLE
            micBtn.visibility = View.GONE
        } catch (e: IllegalStateException) {
            Log.d(TAG, "startRecording: IllegalStateException:  ${e.message} ")
        } catch (e: IOException) {
            Log.d(TAG, "startRecording: IOException: ${e.message}")
        }
    }
     fun stopRecording(micRecordingBtn: LottieAnimationView, micBtn: ImageView,messagesList: RecyclerView) {
        if (isRecording.value == true) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = MediaRecorder()
            isRecording.value = false
            micBtn.visibility = View.VISIBLE
            micRecordingBtn.visibility = View.GONE
            addVoiceMessageToList(messagesList)
        } else {
            Log.d(TAG, "stopRecording: You are not recording right now!")
        }
    }
    private fun initialList(){
        messagesList.add(MessagesModel(viewType = TIME_LAYOUT, timeStamp = "Sun,3-2-2021"))
        messagesList.add(
            MessagesModel(viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:00pm",
                timeStamp = Date().toString()))
        messagesList.add(
            MessagesModel(viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:01pm",
                timeStamp = Date().toString()))
        messagesList.add(
            MessagesModel(viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:10pm",
                timeStamp = Date().toString()))
        messagesList.add(
            MessagesModel(viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:30pm",
                timeStamp = Date().toString()))
        messagesList.add(
            MessagesModel(viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:40pm",
                timeStamp = Date().toString()))
        messagesList.add(
            MessagesModel(viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:57pm",
                timeStamp = Date().toString()))
        messagesList.add(
            MessagesModel(viewType = RECEIVER_LAYOUT,
               // message = "This is Receiver",
                voicePath = "/storage/emulated/0/Merchant/recorded/recordedTue Aug 30 17:08:07 GMT+02:00 2022.mp3",
                messageTime = "02:57pm",
                timeStamp = Date().toString()))
        adapter.submitList(messagesList)
    }
    private fun addVoiceMessageToList( messagesList: RecyclerView) {
        this.messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                voicePath = output,
                messageTime = CommonFunction.getCurrentTime()))
        adapter.notifyDataSetChanged()
        messagesList.scrollToPosition(this.messagesList.size - 1)
    }
     fun addTextMessageToList(message:String ,messageEditText:EditText, messagesList: RecyclerView) {
        this.messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = message,
                messageTime = CommonFunction.getCurrentTime()))
        adapter.notifyDataSetChanged()
        messagesList.scrollToPosition(this.messagesList.size - 1)
        messageEditText.text.clear()
    }
     fun addImageMessageToList(imgURL: String, messagesList: RecyclerView) {
        this.messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                messageTime = CommonFunction.getCurrentTime(),
                imageURL = imgURL))
        adapter.notifyDataSetChanged()
        messagesList.scrollToPosition(this.messagesList.size - 1)
    }
    fun initialRecyclerView(context:Context,messagesList:RecyclerView){
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.stackFromEnd = true
        messagesList.layoutManager = linearLayoutManager
        messagesList.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}