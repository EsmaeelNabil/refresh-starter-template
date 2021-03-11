package com.pixiedia.pixicommerce.ui.customViews

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.use
import androidx.core.widget.TextViewCompat
import com.pixiedia.pixicommerce.R
import java.util.*

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    val BG_TYPE_NORMAL = 0
    val BG_TYPE_DARK = 1
    val BG_TYPE_CUSTOM = 2

    private val dp = resources.displayMetrics.density

    private val tvLabel: AppCompatTextView
    private val vLoading: View

    private var bgType = 0
    private var isLoading = false

    private var cornersRadius = floatArrayOf()

    init {
        LayoutInflater.from(context).inflate(R.layout.loading_button, this, true)

        tvLabel = this.findViewById(R.id.tv_loading_btn)
        vLoading = this.findViewById(R.id.avi_loading_btn)

        context.theme.obtainStyledAttributes(attributeSet, R.styleable.LoadingButton, 0, 0).use {
            setIsLoading(it.getBoolean(R.styleable.LoadingButton_loading, isLoading))
            isEnabled = it.getBoolean(R.styleable.LoadingButton_android_enabled, isEnabled)

            bgType = it.getInt(R.styleable.LoadingButton_bg_type, 0)

            setCornersRadius(
                it.getDimension(R.styleable.LoadingButton_cornerRadius_topLeft, 0f),
                it.getDimension(R.styleable.LoadingButton_cornerRadius_topRight, 0f),
                it.getDimension(R.styleable.LoadingButton_cornerRadius_bottomRight, 0f),
                it.getDimension(R.styleable.LoadingButton_cornerRadius_bottomLeft, 0f)
            )

            setButtonBackground(
                bgType,
                it.getColor(R.styleable.LoadingButton_android_background, 0),
                it.getDimension(R.styleable.LoadingButton_corner_radius, 6f * dp),
                it.getDimension(R.styleable.LoadingButton_stroke_width, 0f).toInt(),
                it.getColor(R.styleable.LoadingButton_stroke_color, 0),
                it.getColor(R.styleable.LoadingButton_ripple_color, Color.LTGRAY)
            )

            setDrawable(
                it.getDrawable(R.styleable.LoadingButton_drawableStartCompat),
                it.getDrawable(R.styleable.LoadingButton_drawableTopCompat),
                it.getDrawable(R.styleable.LoadingButton_drawableEndCompat),
                it.getDrawable(R.styleable.LoadingButton_drawableBottomCompat)
            )

            setDrawablePadding(
                it.getDimension(
                    R.styleable.LoadingButton_android_drawablePadding, 0f
                ).toInt()
            )

            setDrawableTint(it.getColor(R.styleable.LoadingButton_drawableTint, 0))

            setTexTAlignment(it.getInt(R.styleable.LoadingButton_android_textAlignment, 0))

            setText(it.getText(R.styleable.LoadingButton_android_text).toString())

            setTextAllCaps(
                it.getBoolean(
                    R.styleable.LoadingButton_android_textAllCaps, true
                )
            )

            setTextSize(it.getDimension(R.styleable.LoadingButton_android_textSize, 0f), true)

            setTextColor(
                try {
                    it.getColorOrThrow(R.styleable.LoadingButton_android_textColor)
                } catch (iE: IllegalArgumentException) {
                    Color.BLACK
                }
            )
        }

        isClickable = true
        isFocusable = true
    }

    fun setCornersRadius(
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomRightRadius: Float,
        bottomLeftRadius: Float,
    ) {
        if (topLeftRadius == 0f &&
            topRightRadius == 0f &&
            bottomRightRadius == 0f &&
            bottomLeftRadius == 0f
        ) return

        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR) {
            cornersRadius = floatArrayOf(
                topLeftRadius,
                topLeftRadius,
                topRightRadius,
                topRightRadius,
                bottomRightRadius,
                bottomRightRadius,
                bottomLeftRadius,
                bottomLeftRadius
            )
        } else {
            cornersRadius = floatArrayOf(
                topRightRadius,
                topRightRadius,
                topLeftRadius,
                topLeftRadius,
                bottomLeftRadius,
                bottomLeftRadius,
                bottomRightRadius,
                bottomRightRadius
            )
        }
    }

    // TODO: 2/3/21 remote config
    fun setButtonBackground(
        bgType: Int = BG_TYPE_NORMAL,
//        bgColor: Int = Color.parseColor(RemoteConfig.Colors.appConfigurationsAppColorsBase),
        bgColor: Int = Color.parseColor("#F5EEE6"),
        cornerRadius: Float = 6f * dp,
        strokeWidth: Int = 0,
        strokeColor: Int = 0,
        rippleColor: Int = Color.LTGRAY,
    ) {
        val contentDrawable = GradientDrawable().apply {
            if (cornersRadius.size < 8) {
                this.cornerRadius = cornerRadius
            } else {
                cornerRadii = cornersRadius
            }
            setStroke(strokeWidth, strokeColor)
            color = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_enabled),
                    intArrayOf()
                ),
                intArrayOf(
                    when (bgType) {
                        BG_TYPE_CUSTOM -> { // custom
                            Color.TRANSPARENT
                        }
                        else -> { // default
                            ContextCompat.getColor(context, R.color.colorDisable)
                        }
                    },
                    when (bgType) {
                        BG_TYPE_NORMAL -> { // default
                            // TODO: 2/3/21 remote config
                            //Color.parseColor(RemoteConfig.Colors.appConfigurationsAppColorsBase)
                            Color.parseColor("#F5EEE6")
                        }
                        BG_TYPE_DARK -> { // dark
                            ContextCompat.getColor(context, R.color.colorDark)
                        }
                        else -> { // custom
                            try {
                                bgColor
                            } catch (e: Resources.NotFoundException) {
                                Color.TRANSPARENT
                            }
                        }
                    }
                )
            )
        }

        val maskDrawable = GradientDrawable().apply {
            if (cornersRadius.size < 8) {
                this.cornerRadius = cornerRadius
            } else {
                cornerRadii = cornersRadius
            }
            color = ColorStateList(
                arrayOf(
                    intArrayOf()
                ),
                intArrayOf(
                    Color.WHITE
                )
            )
        }

        val rippleDrawable = RippleDrawable(
            ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_pressed)
                ),
                intArrayOf(
                    rippleColor
                )
            ),
            contentDrawable,
            maskDrawable
        )

        background = rippleDrawable
    }

    fun setDrawable(
        startDrawable: Drawable? = null,
        topDrawable: Drawable? = null,
        endDrawable: Drawable? = null,
        bottomDrawable: Drawable? = null,
    ) {
        tvLabel.setCompoundDrawablesRelativeWithIntrinsicBounds(
            startDrawable,
            topDrawable,
            endDrawable,
            bottomDrawable
        )
    }

    fun setDrawablePadding(padding: Int) {
        tvLabel.compoundDrawablePadding = (padding)
    }

    fun setDrawableTint(tint: Int = 0) {
        if (tint == 0) return

        TextViewCompat.setCompoundDrawableTintList(
            tvLabel, ColorStateList(
                arrayOf(
                    intArrayOf()
                ),
                intArrayOf(
                    tint
                )
            )
        )
    }

    fun setTextColor(color: Int = Color.BLACK) {
        tvLabel.setTextColor(
            ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_activated),
                    intArrayOf()
                ),
                intArrayOf(
                    Color.TRANSPARENT,
                    when (bgType) {
//                        BG_TYPE_NORMAL -> Color.parseColor(RemoteConfig.Colors.appConfigurationsAppColorsSecondary)
                        BG_TYPE_NORMAL -> Color.parseColor("#000000")
                        BG_TYPE_DARK -> Color.WHITE
                        else -> color
                    }
                )
            )
        )
    }

    fun setTextSize(size: Float, isDimension: Boolean = false) {
        if (isDimension) {
            if (size != 0f) {
                tvLabel.textSize = size / dp
            } else {
                tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            }
        } else {
            tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        }
    }

    fun setTextAllCaps(textAllCaps: Boolean) {
        if (textAllCaps) {
            tvLabel.text = tvLabel.text.toString().toUpperCase(Locale.US)
        }
    }

    fun setText(text: String) {
        tvLabel.text = text
    }

    fun setTexTAlignment(ta: Int) {
        tvLabel.textAlignment = ta
    }

    fun setIsLoading(loading: Boolean) {
        isLoading = loading

        isEnabled = !loading // background & clicks
        tvLabel.isActivated = !loading // text color
        alpha = if (loading && bgType == 2) .4f else 1f // custom background
        vLoading.visibility = if (loading) View.VISIBLE else View.GONE // loading
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        tvLabel.isEnabled = enabled
    }
}