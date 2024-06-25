package com.application.qrscanner

data class DataSettings(
    var id: Int? = null,
    var dataTheme: Boolean? = null,
    var dataBeep: Boolean? = null,
    var dataVibrate: Boolean? = null,
    var dataScan: Boolean? = null
)

data class Beep (
    var dataBeep: Boolean? = null,
)
