package com.droid.loginsignup.fragments

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.droid.loginsignup.databinding.FragmentAIBinding
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AIFragment : Fragment() {

    private lateinit var binding: FragmentAIBinding
    private val client = OkHttpClient()
    private val API_KEY = "sk-HCIwkavfyHIFEdbmqRHTI4m1AUKdJH892HX5fCM8WPtxvPGZ"  // Replace with your actual Stability API Key
    private val API_URL = "https://api.stability.ai/v1/generation/stable-diffusion-xl-1024-v1-0/text-to-image"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAIBinding.inflate(inflater, container, false)

        binding.btnGenerate.setOnClickListener { generateImage() }
        binding.btnDownload.setOnClickListener { saveImageToGallery() }

        return binding.root
    }

    private fun generateImage() {
        val promptText = binding.etPrompt.text.toString().trim()
        if (promptText.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a prompt!", Toast.LENGTH_SHORT).show()
            return
        }

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etPrompt.windowToken, 0)

        // Show progress bar and disable buttons
        binding.progressBar.visibility = View.VISIBLE
        binding.btnGenerate.isEnabled = false
        binding.btnDownload.visibility = View.GONE
        binding.ivGeneratedImage.visibility = View.GONE
        binding.tvPlaceholder.visibility = View.VISIBLE

        val jsonRequest = """
        {
            "text_prompts": [{ "text": "$promptText", "weight": 1.0 }],
            "steps": 30,
            "width": 1024,
            "height": 1024,
            "cfg_scale": 7,
            "samples": 1
        }
        """.trimIndent()

        val request = Request.Builder()
            .url(API_URL)
            .post(RequestBody.create(MediaType.parse("application/json"), jsonRequest))
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.btnGenerate.isEnabled = true
                    Toast.makeText(requireContext(), "Network Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()?.string() ?: "{}"
                Log.d("API_RESPONSE", "Code: ${response.code()}, Body: $responseBody")

                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.btnGenerate.isEnabled = true

                    if (!response.isSuccessful) {
                        Toast.makeText(requireContext(), "API Error: ${response.code()} - ${response.message()}", Toast.LENGTH_LONG).show()
                        return@runOnUiThread
                    }

                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val artifacts = jsonResponse.optJSONArray("artifacts")
                        if (artifacts == null || artifacts.length() == 0) {
                            Toast.makeText(requireContext(), "No image generated. Try again!", Toast.LENGTH_LONG).show()
                            return@runOnUiThread
                        }

                        val firstArtifact = artifacts.getJSONObject(0)
                        val base64Image = firstArtifact.optString("base64", "")
                        if (base64Image.isEmpty()) {
                            Toast.makeText(requireContext(), "Error: No image data found!", Toast.LENGTH_LONG).show()
                            return@runOnUiThread
                        }

                        val decodedImage = Base64.decode(base64Image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.size)

                        binding.tvPlaceholder.visibility = View.GONE
                        binding.ivGeneratedImage.setImageBitmap(bitmap)
                        binding.ivGeneratedImage.visibility = View.VISIBLE
                        binding.btnDownload.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Parsing Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun saveImageToGallery() {
        val drawable = binding.ivGeneratedImage.drawable ?: return
        val bitmap = (drawable as BitmapDrawable).bitmap
        val fileName = "AI_Image_${System.currentTimeMillis()}.png"

        try {
            val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AI_Images")
            if (!directory.exists()) directory.mkdirs()

            val file = File(directory, fileName)
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()

            // Notify the system about the new image
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(file.absolutePath),
                arrayOf("image/png"),
                null
            )

            Toast.makeText(requireContext(), "Image saved to Gallery!", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to save image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}
