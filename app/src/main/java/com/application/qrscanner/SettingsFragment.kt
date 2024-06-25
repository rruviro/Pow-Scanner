package com.application.qrscanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.qrscanner.databinding.FragmentSettingsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        binding.Back.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_HFragment)
        }

        binding.Beep.setOnClickListener {
            beepAction()
        }

        binding.Vibrate.setOnClickListener {
            if (!binding.vibratie.isChecked) {
                binding.vibratie.isChecked = true
            } else {
                binding.vibratie.isChecked = false
            }
        }

        binding.History.setOnClickListener {
            if (!binding.histories.isChecked) {
                binding.histories.isChecked = true
            } else {
                binding.histories.isChecked = false
            }
        }

        binding.OpenUrl.setOnClickListener {
            if (!binding.urls.isChecked) {
                binding.urls.isChecked = true
            } else {
                binding.urls.isChecked = false
            }
        }

        binding.Share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Testing?")
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to: "))
        }

        return binding.root
    }

    private fun beepAction() {
        Toast.makeText(context, "Process", Toast.LENGTH_SHORT).show()
        val database = FirebaseDatabase.getInstance().reference.child("users")
        database.orderByChild("databeep").equalTo(true).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()){
                    val id = database.push().key
                    val userSetting = DataSettings(id = 1, dataBeep = true)
                    database.child(id!!).setValue(userSetting)
                    Toast.makeText(context, "Present as today", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database Failed", Toast.LENGTH_SHORT).show()
            }

        })
//
//        val idList = "0123456789abcdefghijklmnopqrstuvwxynz"
//        val generator: Random = Random
//        val x = (generator.nextInt(idList.length))

//        database.child(x.toString()).setValue(DataSettings).addOnSuccessListener {
//            Toast.makeText(context, "Present as today", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//        }

    }
}