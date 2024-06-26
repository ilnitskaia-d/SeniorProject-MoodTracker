package com.example.senproject.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.senproject.R
import com.example.senproject.data.MoodState
import com.example.senproject.data.models.MemoryEntry
import com.example.senproject.data.models.Message
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.databinding.FragmentChatBinding
import com.example.senproject.ui.adapters.MessageAdapter
import com.example.senproject.ui.viewmodels.StatisticsViewModel
import com.example.senproject.utils.Constant.RECEIVE_ID
import com.example.senproject.utils.Constant.SEND_ID
import com.example.senproject.utils.NotificationReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
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
import java.time.Duration

class Chat : Fragment() {
    val MIN_EIGHT = Duration.ofSeconds(8)
    val MIN_SEVEN = Duration.ofSeconds(7)
    val MIN_FOUR = Duration.ofSeconds(4)

    private lateinit var memoryEntry: MemoryEntry
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

    //toDo: delete later and pass as an argument
    val onStartRun = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //send argument of how to start the conversation
        if(onStartRun) {

            var listEntry = listOf<MoodEntry>()
            viewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
            viewModel.getAllMoodEntries.observe(viewLifecycleOwner) {
                listEntry = it
            }

//            val mood = Statistics.getLastWeekEntries(listEntry).first().second
            val mood = MoodState.V_GOOD
            binding.llLayoutBar.visibility = View.GONE

            startChat(mood)
        } else {
            adapter.insertMessage(Message("Hello, what do you want to talk about?", RECEIVE_ID))

            //Bad mood
            //Panic
            //Good mood
            onButtonStart()

//            binding.btnSend.setOnClickListener {
//                if (binding.etMessage.text.isNotEmpty()) {
//                    val question = binding.etMessage.text.toString()
//
//                    adapter.insertMessage(Message(question, SEND_ID))
//                    binding.etMessage.text = null
//                    getResponse(question) { response ->
//                        requireActivity().runOnUiThread {
//                            adapter.insertMessage(Message(response.trim(), RECEIVE_ID))
//                        }
//                    }
        }
    }

    private fun onButtonStart() {
        binding.apply {
            llLayoutBar.visibility = View.GONE
            gridTableAnswers.visibility = View.GONE
            linearTableAnswers.visibility = View.VISIBLE

            btnSame.visibility = View.VISIBLE
            btnBetter.visibility = View.VISIBLE
            btnWorse.visibility = View.VISIBLE
            btnNoTalk.visibility = View.VISIBLE

            btnSame.text = "Panic help"
            btnBetter.text = "Good mood"
            btnWorse.text = "Bad mood"
            btnNoTalk.text = "No, thank you"

            btnSame.setOnClickListener { panicChat() }
            btnBetter.setOnClickListener { chatResponseGoodMood(1, "Same1") }
            btnWorse.setOnClickListener {
                adapter.insertMessage(Message("I am sorry to hear that. Let's discuss your current mood.", SEND_ID))
                chatResponseBadMood(1, "Same1")
            }
            btnNoTalk.setOnClickListener {
                adapter.insertMessage(Message("Ok, see you later.", SEND_ID))
                endChat()
            }
        }
    }

    private fun panicChat() {
        binding.apply {
            linearTableAnswers.visibility = View.VISIBLE
            btnSame.visibility = View.VISIBLE
            btnBetter.visibility = View.GONE
            btnWorse.visibility = View.GONE
            btnNoTalk.visibility = View.GONE

            btnSame.text = "Stop"

            CoroutineScope(Dispatchers.Main).launch {
                breathing(7)
            }
            //breathing
            //notice things
            //meditation

            btnSame.visibility = View.VISIBLE
            btnBetter.visibility = View.VISIBLE
            btnWorse.visibility = View.VISIBLE
            btnNoTalk.visibility = View.VISIBLE
        }
    }

    private suspend fun breathing(mode: Int) {
        var exercise = true

        binding.btnSame.setOnClickListener {
            exercise = false
            onButtonStart()
        }

        var i = 2
        while (exercise) {
            adapter.insertMessage(Message("Breath in for 4 sec", RECEIVE_ID))

            while (i < 4) {
                delay(Duration.ofSeconds(1))
                adapter.insertMessage(Message(i.toString(), RECEIVE_ID))
                i++
            }

            delay(Duration.ofSeconds(1))
            i = 2
            adapter.insertMessage(Message("Hold for 7", RECEIVE_ID))
            while (i < 7) {
                delay(Duration.ofSeconds(1))
                adapter.insertMessage(Message(i.toString(), RECEIVE_ID))
                i++
            }

            delay(Duration.ofSeconds(1))
            i = 2
            adapter.insertMessage(Message("Breath out for 8 sec", RECEIVE_ID))
            while (i < 8) {
                delay(Duration.ofSeconds(1))
                adapter.insertMessage(Message(i.toString(), RECEIVE_ID))
                i++
            }

            i = 2
            delay(Duration.ofSeconds(1))
        }
    }

    private fun getResponse(question: String, callback: (String) -> Unit) {
         val apiKey = "sk-proj-3pVYREgxPQvs8NGz9QAhT3BlbkFJPWggvB2hL0i8VnUaeVGt"
         val url = "https://api.openai.com/v1/completions"

         val requestBody = """
             {
             "model": "gpt-3.5-turbo-instruct",
             "prompt": "$question",
             "max_tokens": 300,
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
        if (lastMood == MoodState.V_BAD ||
            lastMood == MoodState.BAD) {
            chatResponseBadMood(0, "onStart")
        } else {
            chatResponseGoodMood(0, "onStart")
        }
    }
    private fun endChat() {
        binding.apply {
            gridTableAnswers.visibility = View.GONE
            linearTableAnswers.visibility = View.VISIBLE

            btnSame.visibility = View.GONE
            btnBetter.visibility = View.GONE
            btnWorse.visibility = View.VISIBLE

            btnWorse.text = "Exit"
            btnWorse.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun chatResponseBadMood(stage: Int, answer: String) {
        when (stage) {
            0 -> {

                adapter.insertMessage(Message("Hello! I noticed that you have been feeling down lately. How are you feeling today?", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.VISIBLE

                    btnSame.setOnClickListener { chatResponseBadMood(1, "Same") }
                    btnWorse.setOnClickListener { chatResponseBadMood(1, "Worse") }
                    btnBetter.setOnClickListener { chatResponseBadMood(1, "Better") }

                    btnNoTalk.setOnClickListener {
                        adapter.insertMessage(Message(btnNoTalk.text.toString(), SEND_ID))
                        adapter.insertMessage(Message("That's ok, see you next time, take care!", RECEIVE_ID))
                        endChat()
                    }
                }
            }
            1 -> {
                if(answer != "Same1") adapter.insertMessage(Message(answer, SEND_ID))

                if(answer == "Same" || answer == "Worse" || answer == "Same1" ) {
                    binding.linearTableAnswers.visibility = View.GONE
                    adapter.insertMessage(Message("How can you describe your prevalent emotion right now?", RECEIVE_ID))

                    binding.apply {
                        linearTableAnswers.visibility = View.GONE
                        gridTableAnswers.visibility = View.VISIBLE

                        btn7.visibility = View.GONE
                        btn8.visibility = View.GONE
                        btn9.visibility = View.GONE

                        val buttons = listOf(btn1, btn2, btn3, btn4, btn5, btn6)

                        for(btn in buttons) {
                            btn.setOnClickListener {
                                adapter.insertMessage(Message(btn.text.toString(), SEND_ID))
                                chatResponseBadMood(2, btn.text.toString())
                            }
                        }

                        btnNotSure.setOnClickListener {
                            adapter.insertMessage(Message(btnNotSure.text.toString(), SEND_ID))

                            adapter.insertMessage(Message("It's okay to feel unsure about your emotions sometimes." +
                                    " Emotions can be complex, and it's normal to have mixed feelings or to feel uncertain at times. ",
                                RECEIVE_ID))

                            adapter.insertMessage(Message("Do you want advice on how to manage difficult emotions?", RECEIVE_ID))

                            for(btn in buttons) {
                                it.visibility = View.GONE
                            }
                            gridTableAnswers.columnCount = 2

                            btnYes.visibility = View.VISIBLE
                            btnNo.visibility = View.VISIBLE

                            btnYes.setOnClickListener {
                                adapter.insertMessage(Message(btnYes.text.toString(), SEND_ID))
                                getResponse("Give advice on managing not certain difficult emotions") { response ->
                                    requireActivity().runOnUiThread {
                                        adapter.insertMessage(Message(response, RECEIVE_ID))
                                    }
                                }
                            }

                            btnNo.setOnClickListener {
                                adapter.insertMessage(Message(btnNo.text.toString(), SEND_ID))
                                adapter.insertMessage(Message("Ok, I understand. It's important to take breaks when you need them.Take care and have a wonderful rest of a day!", RECEIVE_ID))
                                endChat()
                            }
                        }
                    }
                } else {
                    binding.apply{
                        btnNoTalk.setOnClickListener { chatResponseBadMood(5, "") }
                    }

                    adapter.insertMessage(
                        Message(
                            "I am happy to hear it. " +
                                    "Remember, it's okay to have rough days sometimes, " +
                                    "but it's also important to celebrate the good days like today." +
                                    " Is there anything specific that helped improve your mood?" +
                                    " You can make an Entry and write those things down there." +
                                    "\nLet's keep the positivity going!", RECEIVE_ID
                        )
                    )
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
                    btn8.text = "Loneliness"
                    btn9.text = "Other"

                    btn7.visibility = View.VISIBLE
                    btn8.visibility = View.VISIBLE
                    btn9.visibility = View.VISIBLE

                    val buttons = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)

                    for (btn in buttons) {
                        btn.setOnClickListener {
                            adapter.insertMessage(Message(btn.text.toString(), SEND_ID))
                            chatResponseBadMood(3, (answer + " because of "+ btn.text))
                        }
                    }
                }
            }
            3 -> {
                adapter.insertMessage(Message("I am sorry to hear that you are troubled. Do you want to see advice?", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.VISIBLE

                    btnBetter.visibility = View.GONE
                    btnSame.text = "Yes"
                    btnWorse.text = "No"

                    btnSame.setOnClickListener {
                        adapter.insertMessage(Message(btnSame.text.toString(), SEND_ID))
                        getResponse("I feel $answer, give advice") { response ->
                            requireActivity().runOnUiThread {
                                adapter.insertMessage(Message(response, RECEIVE_ID))
                            }
                        }

                        btnSame.text = "Continue"
                        btnWorse.text = "Finish"

                        btnSame.setOnClickListener {
                            chatResponseBadMood(4, "")
                        }
                        btnWorse.setOnClickListener {
                            adapter.insertMessage(Message("See you later!", RECEIVE_ID))
                            endChat()
                        }
                    }

                    btnWorse.setOnClickListener {
                        adapter.insertMessage(Message(btnWorse.text.toString(), SEND_ID))

                        adapter.insertMessage(Message("Sure, if you change your mind you can come back when you feel so", RECEIVE_ID))
                        endChat()
                    }
                }
            }
            4 -> {
                adapter.insertMessage(Message("Are you experiencing any suicidal thoughts?", RECEIVE_ID))
                binding.apply {
                    btnBetter.visibility = View.VISIBLE
                    btnBetter.text = "Sometimes"
                    btnSame.text = "Yes"
                    btnWorse.text = "No"

                    btnBetter.setOnClickListener {
                        adapter.insertMessage(Message(btnBetter.text.toString(), SEND_ID))

                        adapter.insertMessage(Message("I am sorry to hear it. You are not alone in this. It is important to remember this and take care of yourself and reach out to the close ones or professionals. \n" +
                                "Do you want to hear advices how to cope with this thoughts?", RECEIVE_ID))

                        btnBetter.visibility = View.GONE

                        btnSame.setOnClickListener {
                            adapter.insertMessage(Message(btnSame.text.toString(), SEND_ID))
                            getResponse("I have suicidal ideation, how to cope with those") { response ->
                                requireActivity().runOnUiThread {
                                    adapter.insertMessage(Message(response, RECEIVE_ID))
                                }
                            }
                        }
                        btnWorse.setOnClickListener {
                            adapter.insertMessage(Message(btnWorse.text.toString(), SEND_ID))

                            adapter.insertMessage(Message("I'm really sorry to hear that you're feeling this way." +
                                    " It's important to me that you know you're not alone. " +
                                    "If you change your mind and decide you'd like to talk or need support, I'm here for you." +
                                    " Please take care of yourself, and remember that there are people who care about you and want to help. " +
                                    "If things feel too overwhelming, consider reaching out to a trusted friend, family member, " +
                                    "or mental health professional.", RECEIVE_ID))
                            endChat()
                        }
                    }

                    btnSame.setOnClickListener {
                        adapter.insertMessage(Message(btnSame.text.toString(), SEND_ID))

                        adapter.insertMessage(Message("I am sorry to hear it. You are not alone in this. It is important to remember this and take care of yourself and reach out to the close ones or professionals. \n" +
                                "Do you want to hear advices how to cope with this thoughts?", RECEIVE_ID))

                        btnBetter.visibility = View.GONE

                        btnSame.setOnClickListener {
                            adapter.insertMessage(Message(btnSame.text.toString(), SEND_ID))

                            getResponse("Sometimes, I have suicidal ideation, how to cope with those") { response ->
                                requireActivity().runOnUiThread {
                                    adapter.insertMessage(Message(response, RECEIVE_ID))
                                }
                            }
                        }
                        btnWorse.setOnClickListener {
                            adapter.insertMessage(Message(btnWorse.text.toString(), SEND_ID))

                            adapter.insertMessage(Message("I'm really sorry to hear that you're feeling this way." +
                                    " It's important to me that you know you're not alone. " +
                                    "If you change your mind and decide you'd like to talk or need support, I'm here for you." +
                                    " Please take care of yourself, and remember that there are people who care about you and want to help. " +
                                    "If things feel too overwhelming, consider reaching out to a trusted friend, family member, " +
                                    "or mental health professional.", RECEIVE_ID))
                            endChat()
                        }

                    }
                    btnWorse.setOnClickListener {
                        adapter.insertMessage(Message(btnWorse.text.toString(), SEND_ID))

                        adapter.insertMessage(Message("I'm really glad to hear that you're not experiencing those thoughts." +
                                " It's important to know that if you or someone you know ever needs support, there are resources available." +
                                " Organizations like the National Suicide Prevention Lifeline (1-800-273-8255) and Crisis Text Line (text HELLO to 741741) are always there to help. " +
                                "Remember, reaching out for support is a sign of strength, and these resources are available 24/7 for anyone in need. Take care, and I'm here if you ever want to talk about anything else.\" ", RECEIVE_ID))
                        endChat()
                    }
                }
            }
        }
    }

    private fun chatResponseGoodMood(stage: Int, answer: String) {
        when (stage) {
            0 -> {
                adapter.insertMessage(Message("Hello! I noticed that you had a good day yesterday. " +
                                "How are you feeling today?", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.VISIBLE
                    btnSame.setOnClickListener { chatResponseGoodMood(1, "Same") }
                    btnBetter.setOnClickListener { chatResponseGoodMood(1, "Better") }
                    btnWorse.setOnClickListener { chatResponseGoodMood(1, "Worse") }

                    btnNoTalk.setOnClickListener {
                        linearTableAnswers.visibility = View.GONE
                        adapter.insertMessage(Message(btnNoTalk.text.toString(), SEND_ID))
                        adapter.insertMessage(
                            Message(
                                "That's ok, see you next time, take care!",
                                RECEIVE_ID
                            )
                        )
                        endChat()
                    }
                }
            }

            1 -> {
                if(answer != "Same1") adapter.insertMessage(Message(answer, SEND_ID))

                memoryEntry = MemoryEntry(id = 0)
                binding.apply {
                    if (answer == "Same" || answer == "Better" || answer == "Same1") {
                        adapter.insertMessage(
                            Message(
                                "That's wonderful! Do you want to speak about your good mood?" +
                                        " It is important to remember those days to look back to, when we are down. What do you say?",
                                RECEIVE_ID)
                        )

                        btnSame.visibility = View.GONE
                        btnWorse.visibility = View.GONE
                        btnBetter.text = "Yes"
                        btnNoTalk.text = "No"

                        btnBetter.setOnClickListener {
                            adapter.insertMessage(Message(btnBetter.text.toString(), SEND_ID))
                            chatResponseGoodMood(2, "")
                        }

                        btnNoTalk.setOnClickListener {
                            adapter.insertMessage(Message(btnNoTalk.text.toString(), SEND_ID))

                            adapter.insertMessage(Message("Ok, I am happy that you are having a good mood today. " +
                                    "Take care and have a wonderful rest of a day!", RECEIVE_ID))
                            endChat()
                        }
                    } else {
                        adapter.insertMessage(Message("That's ok. Mood is always changing. Do you want to talk about your current mood," +
                                        "or we can embark and remember what a day you had yesterday?", RECEIVE_ID))

                        btnSame.visibility = View.GONE
                        btnWorse.visibility = View.GONE
                        btnBetter.text = "Today"
                        btnNoTalk.text = "Yesterday"

                        //today
                        btnBetter.setOnClickListener {
                            adapter.insertMessage(Message(btnBetter.text.toString(), SEND_ID))

                            chatResponseBadMood(1, "Same1") }
                        //yesterday
                        btnNoTalk.setOnClickListener {
                            adapter.insertMessage(Message(btnNoTalk.text.toString(), SEND_ID))
                            chatResponseGoodMood(2, "") }
                    }
                }
            }
            2 -> {
                adapter.insertMessage(Message("Ok Let's start", RECEIVE_ID))
                adapter.insertMessage(Message("What's bring you joy in that good day", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.GONE
                    llLayoutBar.visibility = View.VISIBLE
                    btnSend.text = "Ready"

                    btnSend.setOnClickListener {
                        memoryEntry.joy = etMessage.text.toString()
                        adapter.insertMessage(Message(etMessage.text.toString(), SEND_ID))
                        etMessage.text = null
                        chatResponseGoodMood(3, "")
                    }
                }
            }
            3 -> {
                adapter.insertMessage(Message("Wonderful!", RECEIVE_ID))
                adapter.insertMessage(Message("What's a recent success or achievement you're proud of?", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.GONE
                    llLayoutBar.visibility = View.VISIBLE
                    btnSend.text = "Ready"

                    btnSend.setOnClickListener {
                        memoryEntry.smile = etMessage.text.toString()
                        adapter.insertMessage(Message(etMessage.text.toString(), SEND_ID))
                        etMessage.text = null
                        chatResponseGoodMood(4, "")
                    }
                }
            }
            4 -> {
                adapter.insertMessage(Message("Now, what made you smile today?", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.GONE
                    llLayoutBar.visibility = View.VISIBLE
                    btnSend.text = "Ready"

                    btnSend.setOnClickListener {
                        memoryEntry.smile = etMessage.text.toString()
                        adapter.insertMessage(Message(etMessage.text.toString(), SEND_ID))
                        etMessage.text = null
                        chatResponseGoodMood(5, "")
                    }
                }
            }
            5 -> {
                adapter.insertMessage(Message("Describe everything that you are grateful for today", RECEIVE_ID))

                binding.apply {
                    gridTableAnswers.visibility = View.GONE
                    linearTableAnswers.visibility = View.GONE
                    llLayoutBar.visibility = View.VISIBLE
                    btnSend.text = "Ready"

                    btnSend.setOnClickListener {
                        memoryEntry.gratitude = etMessage.text.toString()
                        adapter.insertMessage(Message(etMessage.text.toString(), SEND_ID))
                        etMessage.text = null
                        adapter.insertMessage(Message("Grate! I wrote everything down for you and will remind about that wonderful day later when you will feeling down", RECEIVE_ID))
                        endChat()

                        //ToDo: Add to the RoomDatabase
                    }
                }
            }
        }
    }
}