package com.tori.mementocaps.presentation.exception

class CapsuleNotFoundException : RuntimeException {
    constructor() : super("capsuleId를 찾을 수 없습니다.")
    constructor(message: String) : super(message)
}