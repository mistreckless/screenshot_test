package ru.alfabank.sreenshot_library.service

data class MatchResponse(
    val status: MatchStatus,
    val difference: Double
)

enum class MatchStatus {
    SUCCESS, FAILED, WRONG_DIMENSION, WRONG_IMAGE_ID
}
