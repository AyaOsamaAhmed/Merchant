package com.rbt.merchant.presentation.fragment.home.main.chat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.InsetDrawable
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentChatDetailsBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.chat.MessagesModel
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.common.CommonFunction
import com.rbt.merchant.utils.common.Permissions
import com.rbt.merchant.utils.permissionRequestList
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

private const val SENDER_LAYOUT = 1
private const val RECEIVER_LAYOUT = 2
private const val TIME_LAYOUT = 3
private const val TAG = "ChatDetailsFragment"

class ChatDetailsFragment : Fragment() {
    private lateinit var binding: FragmentChatDetailsBinding
    private var messagesList = ArrayList<MessagesModel>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isRecordPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isReadPermissionGranted = false
    private lateinit var adapter: MessagesAdapter

    // recorder
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailsBinding.inflate(inflater, container, false)
        (activity as MainActivity?)!!.showNavBottom(false)
        (activity as MainActivity?)!!.showNavDrawer(false)
        val chat = arguments?.getParcelable<Chat>("Chat")
        binding.model = chat
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
        mediaRecorder = MediaRecorder()
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                Log.d(TAG, "onCreateView: permissionLauncher ")
                isRecordPermissionGranted =
                    permissions[Manifest.permission.RECORD_AUDIO] ?: isRecordPermissionGranted
                isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted
                isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: isReadPermissionGranted
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesList.add(MessagesModel(viewType = TIME_LAYOUT, timeStamp = "Sun,3-2-2021"))
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:00pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:01pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:10pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:30pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:40pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:57pm"
            )
        )
        binding.addNewMessageImg.setOnClickListener {
            val message = binding.messageEditText.text
            if (!message.isNullOrEmpty()) {
                Log.d(TAG, "onViewCreated: $message")
                messagesList.add(
                    MessagesModel(
                        viewType = SENDER_LAYOUT,
                        message = message.toString(),
                        messageTime = CommonFunction.getCurrentTime()
                    )
                )
                adapter.notifyDataSetChanged()
                binding.messagesList.scrollToPosition(messagesList.size - 1)
                binding.messageEditText.text.clear()
            }
        }
        binding.micBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: $isRecording")
            if (Permissions.isRecordingOk(requireContext()) && Permissions.isStorageOk(
                    requireContext()
                )
            ) {
                if (isRecording) {
                    stopRecording()
                } else {
                    startRecording()
                }
            } else {
                Log.d(TAG, "onViewCreated: else ")
                if (!isRecordPermissionGranted)
                    fetchRecordPermission()
                else if (!isReadPermissionGranted || !isWritePermissionGranted)
                    fetchStoragePermission()
            }
        }
        binding.cameraBtn.setOnClickListener {

        }
        binding.micRecordingBtn.setOnClickListener {
            if (isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
        }
        adapter = MessagesAdapter()
        adapter.submitList(messagesList)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.stackFromEnd = true
        binding.messagesList.layoutManager = linearLayoutManager
        binding.messagesList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun startRecording() {
        try {
            output =
                Environment.getExternalStorageDirectory().absolutePath + "/recorded${Date()}.mp3"
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder?.setOutputFile(output)
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            isRecording = true
            Log.d(TAG, "startRecording: REORDERING..")
            binding.micRecordingBtn.visibility = View.VISIBLE
            binding.micBtn.visibility = View.GONE
        } catch (e: IllegalStateException) {
            Log.d(TAG, "startRecording: IllegalStateException:  ${e.message} ")
        } catch (e: IOException) {
            Log.d(TAG, "startRecording: IOException: ${e.message}")
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
            binding.micRecordingBtn.visibility = View.GONE
            binding.micBtn.visibility = View.VISIBLE
        } else {
            Log.d(TAG, "stopRecording: You are not recording right now!")
        }
    }

    private fun fetchRecordPermission() {
        isRecordPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        if (!isRecordPermissionGranted) {
            Permissions.requestRecording()
        }
        if (permissionRequestList.isNotEmpty()) {
            permissionLauncher.launch(permissionRequestList.toTypedArray())
        }
    }

    private fun fetchStoragePermission() {
        isReadPermissionGranted = Permissions.isStorageOk(requireContext())
        isWritePermissionGranted = Permissions.isStorageOk(requireContext())
        if (!isReadPermissionGranted && !isWritePermissionGranted) {
            Permissions.requestStorage()
        }
        if (permissionRequestList.isNotEmpty()) {
            permissionLauncher.launch(permissionRequestList.toTypedArray())
        }
    }
}