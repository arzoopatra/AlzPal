package com.alzpal.dashboardactivity.chatpal.feature.chat

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    generativeModel: GenerativeModel
) : ViewModel() {
    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") { text("Hello, I am an Alzheimer patient using the AlzPal app, where you are a feature called 'ChatPal'. AlzPal has features like Reminders, Tracker, ChatPal, Memory Game, Music Therapy, and Caregiver Corner. I'm here to engage with you, share my feelings, and discuss what I feel. Please keep me engaged, and if I ask you to remind me later, help me remember since people with Alzheimer’s often forget things") },
            content(role = "model") { text("No worries, Feel Free to Talk Anytime. Let me know if you need any Help. I'm a friend to Alzheimer patients, so I'm your friend too") }
        )
    )

    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState(chat.history.map { content ->
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))
    val uiState: StateFlow<ChatUiState> =
        _uiState.asStateFlow()

    @RequiresApi(35)
    fun sendMessage(userMessage: String) {
        _uiState.value.addMessage(
            ChatMessage(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(userMessage)

                _uiState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = modelResponse,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = e.localizedMessage,
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }
}
