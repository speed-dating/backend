package com.backend.speed_dating.common.service

import com.vonage.client.VonageClient
import com.vonage.client.sms.MessageStatus
import com.vonage.client.sms.SmsSubmissionResponse
import com.vonage.client.sms.messages.TextMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SmsService(){
    @Value("\${VONAGE_API_KEY}")
    private lateinit var apiKey : String

    @Value("\${VONAGE_API_SECRET_KEY}")
    private lateinit var apiSecret : String

    fun sendSms(phoneNumber: String, countryCode: String,content : String){
        val client = VonageClient.builder()
            .apiKey(apiKey)
            .apiSecret(apiSecret)
            .build()

        // SMS 메시지 생성
        val message = TextMessage(
            "Vonage APIs",
            "821099645997",
            content,
        )

        val response: SmsSubmissionResponse = client.smsClient.submitMessage(message)

        if (response.messages[0].status == MessageStatus.OK) {

        } else {
            println("Message failed with error: ${response.messages[0].errorText}")
        }
    }
}