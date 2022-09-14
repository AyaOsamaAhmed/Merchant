package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.databinding.FragmentChatDetailsBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.common.Permissions
import com.rbt.merchant.utils.common.PermissionsRequestCode
import com.rbt.merchant.utils.permissionRequestList
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import java.util.*

private const val TAG = "ChatDetailsFragment"
class ChatDetailsFragment : Fragment() {
    private lateinit var binding: FragmentChatDetailsBinding
    private val viewModel:ChatDetailsViewModel by lazy {
        ViewModelProvider(this)[ChatDetailsViewModel::class.java]
    }
    private val chatSettingViewModel:ChatSettingViewModel by lazy {
        ViewModelProvider(this)[ChatSettingViewModel::class.java]
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isRecordPermissionGranted = false
    private var isCameraPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isReadPermissionGranted = false

    private lateinit var adapter: MessagesAdapter
    private lateinit var relatedOrderAdapter: RelatedOrderAdapter
    private var isRecording: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailsBinding.inflate(inflater, container, false)
        (activity as MainActivity?)!!.showNavBottom(false)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showToolBar(false)
        val chat = arguments?.getParcelable<Chat>("Chat")
        binding.model = chat
        binding.viewModel = viewModel
        adapter = viewModel.adapter
        relatedOrderAdapter = RelatedOrderAdapter()
        binding.toolBoxFragmentLayout.viewModel = chatSettingViewModel
        viewModel.isRecording.observe(viewLifecycleOwner){
            isRecording = it
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(binding.chatDetailsDrawerLayout.isOpen){
                        binding.chatDetailsDrawerLayout.close()
                    }else{
                        findNavController().navigate(ChatDetailsFragmentDirections.actionChatDetailsFragmentToChatFragment())
                    }

                }
            })
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
        viewModel.initialRecyclerView(requireContext(),binding.messagesList)
        binding.openToolBoxImg.setOnClickListener {
            if(binding.chatDetailsDrawerLayout.isOpen) {
                binding.chatDetailsDrawerLayout.close()
            }else{
                binding.chatDetailsDrawerLayout.open()
            }
        }
        chatSettingViewModel.requestRelatedOrdersLiveData.observe(viewLifecycleOwner){ orders ->
            val data = orders as ArrayList<String>
            relatedOrderAdapter.submitList(data)
            binding.toolBoxFragmentLayout.relatedOrdersRv.adapter = relatedOrderAdapter
        }
        chatSettingViewModel.requestRelatedComplaintsLiveData.observe(viewLifecycleOwner){ complaints ->
            val data = complaints as ArrayList<String>
            relatedOrderAdapter.submitList(data)
            binding.toolBoxFragmentLayout.relatedComplaintsRv.adapter = relatedOrderAdapter
        }
        chatSettingViewModel.requestRelatedInquiriesLiveData.observe(viewLifecycleOwner){ inquiries ->
            val data = inquiries as ArrayList<String>
            relatedOrderAdapter.submitList(data)
            binding.toolBoxFragmentLayout.relatedInquiriesRv.adapter = relatedOrderAdapter
        }

        binding.toolBoxFragmentLayout.relatedOrdersTxtLabel.setOnClickListener {
            switchRV(0)
        }
        binding.toolBoxFragmentLayout.relatedComplaintsTxtLabel.setOnClickListener {
            switchRV(1)
        }
        binding.toolBoxFragmentLayout.relatedInquiriesTxtLabel.setOnClickListener {
            switchRV(2)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val list: ArrayList<ImageFile> =
                    result.data?.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE)!!
                Log.d(TAG, "onViewCreated: image selected: ${list.size}")
                for(img in list){
                    viewModel.addImageMessageToList(img.path,binding.messagesList)
                }
            }
        }
        binding.addNewMessageImg.setOnClickListener {
            val message = binding.messageEditText.text
            if (!message.isNullOrEmpty()) {
                Log.d(TAG, "onViewCreated: $message")
                viewModel.addTextMessageToList(message.toString(),binding.messageEditText,binding.messagesList)
            }
        }
        binding.micBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: $isRecording")
            if (Permissions.isRecordingAndStorageOk(context, PermissionsRequestCode.RecordAndStoragePermissionList)) {
                Log.d(TAG, "onViewCreated: micBtn clicked and has recorded permission")
                if (Permissions.isRecordingOk(requireContext()) && Permissions.isStorageOk(requireContext())) {
                    if (isRecording == true) {
                        viewModel.stopRecording(binding.micRecordingBtn,binding.micBtn,binding.messagesList)
                    } else {
                        viewModel.startRecording(binding.micRecordingBtn,binding.micBtn)
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
                    }.create().show()
            }
        }
        binding.cameraBtn.setOnClickListener {
            if (Permissions.isCameraAndStorageOk(context, PermissionsRequestCode.CameraAndStoragePermissionList)) {
                if (Permissions.isCameraOk(requireContext()) && Permissions.isStorageOk(requireContext())) {
                    val intent1 = Intent(context, ImagePickActivity::class.java)
                    intent1.putExtra(ImagePickActivity.IS_NEED_CAMERA, true)
                    intent1.putExtra(Constant.MAX_NUMBER, 9)
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
                    }.create().show()
            }
        }
        binding.micRecordingBtn.setOnClickListener {
            if (isRecording == true) {
                viewModel.stopRecording(binding.micRecordingBtn,binding.micBtn,binding.messagesList)
            } else {
                viewModel.startRecording(binding.micRecordingBtn, binding.micBtn)
            }
        }
    }

    // fetch permissions needed at this fragment
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
    fun switchRV(rvId:Int):Boolean{
        when(rvId){
            0 ->{
                if(binding.toolBoxFragmentLayout.relatedOrdersRv.isVisible){
                    binding.toolBoxFragmentLayout.relatedOrdersRv.visibility = View.GONE
                }else{
                    binding.toolBoxFragmentLayout.relatedOrdersRv.visibility = View.VISIBLE
                    binding.toolBoxFragmentLayout.relatedInquiriesRv.visibility = View.GONE
                    binding.toolBoxFragmentLayout.relatedComplaintsRv.visibility = View.GONE
                }
                return true
            }
            1 ->{
                if(binding.toolBoxFragmentLayout.relatedComplaintsRv.isVisible){
                    binding.toolBoxFragmentLayout.relatedComplaintsRv.visibility = View.GONE
                }else{
                    binding.toolBoxFragmentLayout.relatedComplaintsRv.visibility = View.VISIBLE
                    binding.toolBoxFragmentLayout.relatedInquiriesRv.visibility = View.GONE
                    binding.toolBoxFragmentLayout.relatedOrdersRv.visibility = View.GONE
                }
                return true
            }
            2 ->{
                if(binding.toolBoxFragmentLayout.relatedInquiriesRv.isVisible){
                    binding.toolBoxFragmentLayout.relatedInquiriesRv.visibility = View.GONE
                }else{
                    binding.toolBoxFragmentLayout.relatedInquiriesRv.visibility = View.VISIBLE
                    binding.toolBoxFragmentLayout.relatedComplaintsRv.visibility = View.GONE
                    binding.toolBoxFragmentLayout.relatedOrdersRv.visibility = View.GONE
                }
                return true
            }
            else -> {
                return false
            }
        }
    }
}