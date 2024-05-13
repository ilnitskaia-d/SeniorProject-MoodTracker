package com.example.senproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.senproject.data.MoodState
import com.example.senproject.data.models.Message
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.FragmentChatBinding
import com.example.senproject.ui.adapters.MessageAdapter
import com.example.senproject.ui.viewmodels.StatisticsViewModel
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
    private lateinit var viewModel: StatisticsViewModel
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

    //toDo: delete later and pass as an argument
    val onStartRun = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //send argument of how to start the conversation
        if(onStartRun) {
            var listEntry = listOf<MoodEntry>()
            viewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
            viewModel.getAllMoodEntries.observe(viewLifecycleOwner) {
                listEntry = it
            }
            val mood = Statistics.getLastWeekEntries(listEntry)[0].second

            binding.llLayoutBar.visibility = View.GONE

            startChat(mood)
        } else {
            binding.llLayoutBar.visibility = View.VISIBLE
            binding.stage0BadMood.visibility = View.GONE

            binding.btnSend.setOnClickListener {
                if (binding.etMessage.text.isNotEmpty()) {
                    val question = binding.etMessage.text.toString()

                    adapter.insertMessage(Message(question, SEND_ID))
                    binding.etMessage.text = null
                    getResponse(question) { response ->
                        requireActivity().runOnUiThread {
                            adapter.insertMessage(Message(response, RECEIVE_ID))
                        }
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


    fun startChat(lastMood: MoodState) {
        //if the last state was bad
        if (lastMood == MoodState.V_BAD ||
            lastMood == MoodState.BAD) {
            chatResponseBadMood(0, "")
        } else {
            //if the last state was good
        }
    }

    fun chatResponseBadMood(stage: Int, answer: String) {

        if (stage == 5) {
            //end chat
            return Unit
        }

        when (stage) {
            0 -> {
                adapter.insertMessage(Message("Hello! I noticed that you have been feeling down lately. How are you feeling today?", RECEIVE_ID))
                binding.stage0BadMood.visibility = View.VISIBLE
                binding.btnSame.setOnClickListener { chatResponseBadMood(1, "Same") }
                binding.btnWorse.setOnClickListener { chatResponseBadMood(1, "Worse") }

                binding.btnBetter.setOnClickListener { chatResponseBadMood(1, "Better") }

                binding.btnNoTalk.setOnClickListener {
                    binding.stage0BadMood.visibility = View.GONE
                    adapter.insertMessage(Message("That's ok, see you next time, take care!", RECEIVE_ID))
                    chatResponseBadMood(5, "")
                }
            }
            1 -> {
                binding.stage0BadMood.visibility = View.GONE
                if(answer == "Same" || answer == "Worse") {
                    adapter.insertMessage(Message("I am sorry to hear it. How can you describe your prevalent emotion right now?", RECEIVE_ID))
                    binding.apply {
                        stage1BadMood.visibility = View.VISIBLE
                        val buttons = listOf(
                            btn1,
                            btn2,
                            btn3,
                            btn4,
                            btn5,
                            btn6
                        )

                        for(btn in buttons) {
                            btn.setOnClickListener {
                                chatResponseBadMood(2, btn.text.toString())
                            }
                        }

                        btnNotSure.setOnClickListener {
                            adapter.insertMessage(Message("It's okay to feel unsure about your emotions sometimes." +
                                    " Emotions can be complex, and it's normal to have mixed feelings or to feel uncertain at times. ",
                                RECEIVE_ID))
                            adapter.insertMessage(Message("Do you want advice on how to manage difficult emotions?", RECEIVE_ID))
                            for(btn in buttons) {
                                it.visibility = View.GONE
                            }
                            stage1BadMood.columnCount = 2

                            btnYes.visibility = View.VISIBLE
                            btnNo.visibility = View.VISIBLE

                            btnYes.setOnClickListener {
                                getResponse("Give advice on managing not certain difficutl emotions") { response ->
                                    requireActivity().runOnUiThread {
                                        adapter.insertMessage(Message(response, RECEIVE_ID))
                                    }
                                }
                            }
                            btnNo.setOnClickListener {
                                adapter.insertMessage(Message("Ok, I hope that everything is going to be ok. Take care!", RECEIVE_ID))
                                chatResponseBadMood(5, "")
                            }
                        }
                    }
                } else {
                    binding.btnSame.visibility = View.GONE
                    binding.btnWorse.visibility = View.GONE
                    binding.btnBetter.visibility = View.GONE

                    binding.btnNoTalk.text = "Exit"
                    binding.btnNoTalk.setOnClickListener { chatResponseBadMood(5, "") }
                    adapter.insertMessage(Message("I am happy to hear it. " +
                            "Remember, it's okay to have rough days sometimes, " +
                            "but it's also important to celebrate the good days like today." +
                            " Is there anything specific that helped improve your mood?" +
                            " You can make an Entry and write those things down there." +
                            "\nLet's keep the positivity going!", RECEIVE_ID))
                }
            }
            2 -> {
                adapter.insertMessage(Message("I am sorry to hear it. What's been on your mind lately?", RECEIVE_ID))

                binding.apply{
                    btn1.text = "Work"
                    btn2.text = "School"
                    btn3.text = "Relationships"
                    btn4.text = "Family"
                    btn5.text = "Health"
                    btn6.text = "Future"
                    btn7.text = "Self-esteem"
                    btn7.visibility = View.VISIBLE
                    btn8.text = "Loneliness"
                    btn8.visibility = View.VISIBLE
                    btn9.text = "Other"
                    btn9.visibility = View.VISIBLE

                    var buttons = listOf(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8)
//
//                    for (btn in buttons) {
//                        btn.setOnClickListener {  }
//                    }
                }
            }
            3 -> {}
            4 -> {}
        }
    }
}