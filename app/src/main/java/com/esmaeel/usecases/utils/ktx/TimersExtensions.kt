package com.esmaeel.usecases.utils.ktx

import android.os.CountDownTimer

fun doIn(inTime: Long, givenFun: () -> Unit) {
    object : CountDownTimer(inTime, inTime) {
        override fun onFinish() {
            givenFun.invoke()
        }

        override fun onTick(p0: Long) {}
    }.start()
}
