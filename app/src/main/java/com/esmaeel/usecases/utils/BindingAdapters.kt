package com.esmaeel.usecases.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("android:loadUrl")
fun loadUrl(image: ImageView, url: String?) {
//    image.load(url)
    image.load("https://images.unsplash.com/photo-1611262588024-d12430b98920?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aWNvbnxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80")
}