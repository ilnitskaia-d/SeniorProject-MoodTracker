package com.example.senproject.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senproject.data.models.Message
import com.example.senproject.databinding.MessageItemBinding
import com.example.senproject.utils.Constant.RECEIVE_ID
import com.example.senproject.utils.Constant.SEND_ID

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    var messageList = mutableListOf<Message>()

    inner class ViewHolder(private val bindingItem: MessageItemBinding): RecyclerView.ViewHolder(bindingItem.root)  {
        fun bindMessage(message: Message) {
            bindingItem.tvMessage.apply {
                text = message.message
                visibility = View.VISIBLE
            }
            bindingItem.tvBotMessage.visibility = View.GONE
        }

        fun bindBotMessage(message: Message) {
            bindingItem.tvBotMessage.apply {
                text = message.message
                visibility = View.VISIBLE
            }
            bindingItem.tvMessage.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MessageItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        when (message.sender) {
            SEND_ID -> holder.bindMessage(message)
            RECEIVE_ID -> holder.bindBotMessage(message)
        }
    }

    fun insertMessage(message: Message) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
    }
}