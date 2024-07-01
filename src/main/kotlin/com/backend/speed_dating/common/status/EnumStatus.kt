package com.backend.speed_dating.common.status

enum class Gender(val desc : String){
    MAN("MAN"),
    WOMAN("WOMAN"),
}

enum class ResultCode(val msg : String) {
    SUCCESS("성공적으로 수행되었습니다."),
    ERROR("에러가 발생했습니다.")
}

enum class Role{
    MEMBER,
}