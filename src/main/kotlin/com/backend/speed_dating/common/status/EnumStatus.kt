package com.backend.speed_dating.common.status

enum class Gender(val desc : String){
    MALE("MALE"),
    FEMALE("FEMALE"),
}

enum class ResultCode(val msg : String) {
    SUCCESS("성공적으로 수행되었습니다."),
    ALREADY_REGISTERED("인증 성공 후, 이미 회원가입이 된 상태입니다."),
    NOT_REGISTERED("인증 성공 후, 회원가입이 되지 않은 상태입니다."),
    VERIFICATION_ERROR("인증번호 오류입니다."),
    EXPIRED_REQUEST("요청 시간이 지났습니다."),
    SERVER_ERROR("서버 오류가 발생했습니다."),
    ERROR("에러가 발생했습니다.")
}

enum class Role{
    MEMBER,
}

enum class CountryEnum(val desc: String){
    KR("KR"),
}