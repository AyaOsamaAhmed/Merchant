package com.rbt.merchant.presentation.fragment.home.main.chat

import android.Manifest
import android.R.attr.data
import android.app.Activity
import android.app.AlertDialog
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbt.merchant.databinding.FragmentChatDetailsBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.chat.MessagesModel
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.common.CommonFunction
import com.rbt.merchant.utils.common.Permissions
import com.rbt.merchant.utils.common.PermissionsRequestCode
import com.rbt.merchant.utils.permissionRequestList
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import java.io.File
import java.io.IOException
import java.util.*


private const val SENDER_LAYOUT = 1
private const val RECEIVER_LAYOUT = 2
private const val TIME_LAYOUT = 3
private const val TAG = "ChatDetailsFragment"

class ChatDetailsFragment : Fragment() {
    private lateinit var binding: FragmentChatDetailsBinding
    private var messagesList = ArrayList<MessagesModel>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var isRecordPermissionGranted = false
    private var isCameraPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isReadPermissionGranted = false
    private lateinit var adapter: MessagesAdapter

    // recorder
    private var output: String? = null
    private var fileName: String? = null
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
                isCameraPermissionGranted =
                    permissions[Manifest.permission.CAMERA] ?: isCameraPermissionGranted
                isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted
                isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: isReadPermissionGranted
            }
       
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                //val data: Intent? = result.data
                val list: ArrayList<ImageFile> =
                    result.data?.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE)!!
                Log.d(TAG, "onViewCreated: image selected: ${list.size}")
                for(img in list){
                    addImageMessageToList(img.path)
                }
            }
        }
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
            if (Permissions.isRecordingAndStorageOk(context, PermissionsRequestCode.RecordAndStoragePermissionList)) {
                Log.d(TAG, "onViewCreated: micBtn clicked and has recorded permission")
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
            } else {
                AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setTitle("App Permission")
                    .setMessage("This app need to access some permissions")
                    .setPositiveButton("ok") { dialog, _ ->
                        Permissions.requestRecordingAndStorage(requireActivity(), PermissionsRequestCode.RecordAndStoragePermissionList)
                        dialog.cancel()
                    }
                    .setNegativeButton("cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()

            }
        }
        binding.cameraBtn.setOnClickListener {
            if (Permissions.isCameraAndStorageOk(context, PermissionsRequestCode.CameraAndStoragePermissionList)) {
                if (Permissions.isCameraOk(requireContext()) && Permissions.isStorageOk(
                        requireContext()
                    )
                ) {
                    val intent1 = Intent(context, ImagePickActivity::class.java)
                    intent1.putExtra(ImagePickActivity.IS_NEED_CAMERA, true)
                    intent1.putExtra(Constant.MAX_NUMBER, 9)
                    //startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE)
                    resultLauncher.launch(intent1)
                } else {
                    Log.d(TAG, "onViewCreated: else ")
                    if (!isCameraPermissionGranted)
                        fetchCameraPermission()
                    else if (!isReadPermissionGranted || !isWritePermissionGranted)
                        fetchStoragePermission()
                }
            } else {
                AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setTitle("App Permission")
                    .setMessage("This app need to access some permissions")
                    .setPositiveButton("ok") { dialog, _ ->
                        Permissions.requestCameraAndStorage(requireActivity(), PermissionsRequestCode.CameraAndStoragePermissionList)
                        dialog.cancel()
                    }
                    .setNegativeButton("cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
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
            fileName = "/recorded${Date()}.mp3"
            output =
                Environment.getExternalStorageDirectory().absolutePath + fileName
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
            addVoiceMessageToList()
        } else {
            Log.d(TAG, "stopRecording: You are not recording right now!")
        }
    }

    private fun addVoiceMessageToList() {
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                voicePath = getRecordingFilePath(),
                messageTime = CommonFunction.getCurrentTime()
            )
        )
        adapter.notifyDataSetChanged()
        binding.messagesList.scrollToPosition(messagesList.size - 1)
    }
    private fun addImageMessageToList(imgURL:String) {
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                messageTime = CommonFunction.getCurrentTime(),
                imageURL = imgURL
            )
        )
        adapter.notifyDataSetChanged()
        binding.messagesList.scrollToPosition(messagesList.size - 1)
    }

    private fun getRecordingFilePath(): String {
        val contextWrapper = ContextWrapper(requireContext())
        val music = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val recordedFile = File(music, fileName!!)
        return recordedFile.path
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
    private fun fetchCameraPermission() {
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (!isCameraPermissionGranted) {
            Permissions.requestCamera()
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