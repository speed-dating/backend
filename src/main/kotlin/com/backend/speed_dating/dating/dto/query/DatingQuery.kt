package com.backend.speed_dating.dating.dto.query

import com.fasterxml.jackson.annotation.JsonProperty

data class CursorBasedQuery(
    @JsonProperty("lastId")
    val lastId: Long? = null,

    @JsonProperty("limit")
    val limit: Int = 10
)