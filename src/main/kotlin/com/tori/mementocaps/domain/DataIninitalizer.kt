package com.tori.mementocaps.domain

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile(value = ["default"])
class DataIninitalizer(
) {
}