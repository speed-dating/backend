package com.backend.speed_dating.dating.controller

import com.backend.speed_dating.common.dto.BaseResponse
import com.backend.speed_dating.dating.dto.payload.DatingCreationPayload
import com.backend.speed_dating.dating.model.DatingResponseModel
import com.backend.speed_dating.dating.service.DatingService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/v1/datings")
class DatingController(
    private val datingService: DatingService,
){
    @PostMapping("")
    fun createDating(@RequestBody @Valid payload: DatingCreationPayload) : ResponseEntity<BaseResponse<Unit>> {

        val response = BaseResponse<Unit>(
            resultCode = "SUCCESS",
            data = null,
            message = "ok!"
        )
        return ResponseEntity(response,  HttpStatus.CREATED)
    }


    @GetMapping("")
    fun getDatings(
        @RequestParam(required = false, defaultValue = "0") lastId: Long?,
        @RequestParam(required = false, defaultValue = "10") limit: Int?,
    ) : ResponseEntity<BaseResponse<List<DatingResponseModel>>>{
        val data : List<DatingResponseModel> = listOf(DatingResponseModel.fixture())
        val response = BaseResponse<List<DatingResponseModel>>(
            resultCode = "SUCCESS",
            data = data,
            message = "ok!!"
        )
        return ResponseEntity(response,HttpStatus.OK)
    }

    @GetMapping("/me")
    fun getMyDatings(
        @RequestParam(required = false, defaultValue = "0") lastId: Long?,
        @RequestParam(required = false, defaultValue = "10") limit: Int?,
    ) : ResponseEntity<BaseResponse<List<DatingResponseModel>>> {
        val data : List<DatingResponseModel> = listOf(DatingResponseModel.fixture())
        val response = BaseResponse<List<DatingResponseModel>>(
            resultCode = "SUCCESS",
            data = data,
            message = "ok!!"
        )
        return ResponseEntity(response,HttpStatus.OK)
    }
}