package com.alzpal.dashboardactivity.chatpal.feature.multimodal

sealed interface PhotoReasoningUiState {
    data object Initial : PhotoReasoningUiState
    data object Loading : PhotoReasoningUiState
    data class Success(val outputText: String) : PhotoReasoningUiState
    data class Error(val errorMessage: String) : PhotoReasoningUiState
}
