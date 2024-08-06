package com.backend.speed_dating.dating.model

import com.backend.speed_dating.user.dto.UserResponseModel
import java.time.LocalDateTime

data class DatingResponseModel(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val startDate: LocalDateTime,
    val owner: UserResponseModel,
    val maleCapacity : Int,
    val femaleCapacity : Int,
    val participants: List<UserResponseModel>,
) {
    companion object {
        fun fixture(
            id: Long = 1L,
            title: String = "MockTitle",
            description: String = "Mock Description",
            imageUrl: String = "http://example.com/image.jpg",
            startDate: LocalDateTime = LocalDateTime.now().plusDays(3).plusHours(4).plusMinutes(36),
            maleCapacity: Int = 5,
            femaleCapacity: Int = 5,
            owner: UserResponseModel = UserResponseModel.fixture(),
            participants: List<UserResponseModel> = listOf(UserResponseModel.fixture())
        ): DatingResponseModel {
            return DatingResponseModel(
                id = id,
                title = title,
                description = description,
                imageUrl = imageUrl,
                startDate = startDate,
                owner = owner,
                participants = participants,
                maleCapacity = maleCapacity,
                femaleCapacity = femaleCapacity,
            )
        }
    }
}