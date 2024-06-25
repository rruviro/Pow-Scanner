package com.application.qrscanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.qrscanner.R.*
import com.application.qrscanner.databinding.FragmentHBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class HFragment : Fragment(){

    private lateinit var binding: FragmentHBinding

    private lateinit var db : DatabaseReference
    private lateinit var userRecycleView : Adapter
    private lateinit var userArrayList : ArrayList<DataHistory>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHBinding.inflate(inflater, container, false)
//        settingsFrag = FragmentSettingsBinding.inflate(inflater, container, false)


        binding.fab.setOnClickListener {
            showBottomSheet()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.settings -> {
                    findNavController().navigate(R.id.action_HFragment_to_settingsFragment)
                    true
                }

                else -> {
                    false
                }

            }
        }

//        userRecycleView = Adapter(userlist = userArrayList)
//        binding.pending.layoutManager = LinearLayoutManager(context)
//        binding.pending.adapter = userRecycleView

//        userRecycleView = findViewById(R.id.use)
//        userRecycleView.layoutManager = LinearLayoutManager(requireContext())
//        userRecycleView.setHasFixedSize(true)

        return binding.root

    }

    private fun showBottomSheet() {
        val view = layoutInflater.inflate(layout.bsheet_nav, null)
        val dialog = BottomSheetDialog(this.requireContext())
        dialog.setContentView(view)

        var camera = dialog.findViewById<LinearLayout>(R.id.camera)
        var image = dialog.findViewById<LinearLayout>(R.id.image)
        var generate = dialog.findViewById<LinearLayout>(R.id.generate)

        camera?.setOnClickListener {
            checkPermissionCamera(this.requireContext())
        }

        image?.setOnClickListener {
            if (checkStoragePermission()) {
                pickImageGallery()
            } else {

            }
        }

        generate?.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_HFragment_to_generateFragment)
        }

        dialog.show()

    }

    //    Qr code image
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
//            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        } else {
//            Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkStoragePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickImageGallery()
        }
        return true
    }

//    Qr Code Camera
    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {

            }
        }

    private fun showCamera() {

        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Scan Qr code")
        options.setCameraId(0)

//        if(!settingsFrag.beepi.isChecked){
//            options.setBeepEnabled(false)
//        } else {
//            options.setBeepEnabled(true)
//        }

        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        scanLauncher.launch(options)

    }

    private val scanLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            run {
                if (result.contents == null) {
//                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
//                    setResult(result.contents)
                }
            }
        }

}