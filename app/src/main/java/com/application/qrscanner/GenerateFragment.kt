package com.application.qrscanner

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.application.qrscanner.databinding.FragmentGenerateBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlin.random.Random

class GenerateFragment : Fragment() {

    private lateinit var binding: FragmentGenerateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGenerateBinding.inflate(inflater, container, false)

        binding.generate.setOnClickListener {
            findNavController().navigate(R.id.action_generateFragment_to_HFragment)
        }

        binding.generate.setOnClickListener {

            val data = binding.code.text.toString().trim()
            if (data.isEmpty()) {
            } else {

                val write = QRCodeWriter()
                try {
                    val bitMatrix = write.encode(data, BarcodeFormat.QR_CODE, 350, 350)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until height) {
                        for (y in 0 until height) {
                            bmp.setPixel(x, y, if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    binding.QRimage.setImageBitmap(bmp)
                } catch (e: WriterException) {
                    e.printStackTrace()
                }

            }

        }

        binding.randomize.setOnClickListener {

            val idList = "0123456789abcdefghijklmnopqrstuvwxynz"
            val generator: Random = Random
            val x = (generator.nextInt(idList.length)).toString()
            binding.code.setText(x)

            val write = QRCodeWriter()
            try {
                val bitMatrix = write.encode(x, BarcodeFormat.QR_CODE, 350, 350)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until height) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                    }
                }
                binding.QRimage.setImageBitmap(bmp)
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }

        binding.Back.setOnClickListener {
            findNavController().navigate(R.id.action_generateFragment_to_HFragment)
        }

        return binding.root

    }

}

