package com.pixiedia.pixicommerce.testUtils

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

fun <T : Activity> goto(to: Class<T>): ActivityScenario<T> {
    return ActivityScenario.launch(to)
}

fun KTextView.getText(): String {
    var text = String()
    this.view.interaction.perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController, view: View) {
            text = (view as TextView).text.toString()
        }
    })
    return text
}

fun KButton.getText(): String {
    var text = String()
    this.view.interaction.perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController, view: View) {
            text = (view as Button).text.toString()
        }
    })
    return text
}