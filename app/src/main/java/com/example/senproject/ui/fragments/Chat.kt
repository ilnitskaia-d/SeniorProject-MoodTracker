package com.example.senproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.senproject.data.models.Message
import com.example.senproject.databinding.FragmentChatBinding
import com.example.senproject.ui.adapters.MessageAdapter
import com.example.senproject.utils.Constant.RECEIVE_ID
import com.example.senproject.utils.Constant.SEND_ID
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class Chat : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: MessageAdapter
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        adapter = MessageAdapter()
        binding.rvMessages.adapter = adapter
        return binding.root
    }

    private val badEmotions = listOf<String>(
        "Sadness",
        "Anger",
        "Anxiety",
        "Frustration",
        "Guilt",
        "Shame",
        "Loneliness",
        "Disappointment",
        "Stress",
        "Envy/Jealousy"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSend.setOnClickListener {
            if(binding.etMessage.text.isNotEmpty()) {
                val question = binding.etMessage.text.toString()

                adapter.insertMessage(Message(question, SEND_ID))
                binding.etMessage.text = null
                getResponse(question) {response ->
                    requireActivity().runOnUiThread{
                        adapter.insertMessage(Message(response, RECEIVE_ID))
                    }
                }
            }
        }


    }

     private fun getResponse(question: String, callback: (String) -> Unit) {
         val apiKey = "sk-proj-3pVYREgxPQvs8NGz9QAhT3BlbkFJPWggvB2hL0i8VnUaeVGt"
         val url = "https://api.openai.com/v1/completions"

         val requestBody = """
             {
             "model": "gpt-3.5-turbo-instruct",
             "prompt": "$question",
             "max_tokens": 10,
             "temperature": 0
             }
         """.trimIndent()

         val request = Request.Builder()
             .url(url)
             .addHeader("Content-Type", "application/json")
             .addHeader("Authorization", "Bearer $apiKey")
             .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
             .build()

         client.newCall(request).enqueue(object : Callback {
             override fun onFailure(call: Call, e: IOException) {
                 Log.e("ApiFailure", "Fail on newCall", e)
             }

             override fun onResponse(call: Call, response: Response) {
                 val body = response.body?.string()
                 if (body != null) {
                     Log.v("data", body)
                 } else {
                     Log.v("data", "empty data")

                 }

                 val jsonObject = JSONObject(body!!)
                 val jsonArray: JSONArray=jsonObject.getJSONArray("choices")
                 val textRes = jsonArray.getJSONObject(0).getString("text")
                 callback(textRes)
             }
         })
    }
}