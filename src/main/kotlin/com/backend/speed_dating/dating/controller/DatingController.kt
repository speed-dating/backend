package com.backend.speed_dating.dating.controller

import com.backend.speed_dating.dating.service.DatingService
import org.springframework.stereotype.Controller

@Controller
class DatingController(
    private val datingService: DatingService,
){


}