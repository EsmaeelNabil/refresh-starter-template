package com.pixiedia.pixicommerce.ui.customViews

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.pixiedia.pixicommerce.R
import com.pixiedia.pixicommerce.utils.ktx.getBitmapFromUrlWithCoil
import com.wang.avi.AVLoadingIndicatorView
import java.util.*

class LoadingImage @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    val IMG_TYPE_ICON = 0
    val IMG_TYPE_IMAGE = 1

    private val LOADING_SIZE_SMALL = 0
    private val LOADING_SIZE_MEDIUM = 1
    private val LOADING_SIZE_LARGE = 2

    val ivIcon: ImageView
    private lateinit var vLoading: AVLoadingIndicatorView

    private val dp = resources.displayMetrics.density

    private var isLoading = false
    private var type: Int? = IMG_TYPE_ICON
    private var bgColor = 0
    private var cornerRadius = 0f
    private var cornersRadius = floatArrayOf()
    private var strokeWidth = 0
    private var strokeColor = 0
    private var rippleColor = Color.LTGRAY
    private var isclickable = false

    init {
        LayoutInflater.from(context).inflate(R.layout.loading_image, this, true)

        ivIcon = this.findViewById(R.id.iv_loading_image)

        context.theme.obtainStyledAttributes(attributeSet, R.styleable.LoadingImage, 0, 0).use {
            initLoadingView(it.getString(R.styleable.LoadingImage_loading_size)?.toInt())

            setIsLoading(it.getBoolean(R.styleable.LoadingImage_loading, isLoading))
            isEnabled = it.getBoolean(R.styleable.LoadingImage_android_enabled, isEnabled)

            setImageResource(it.getResourceId(R.styleable.LoadingImage_android_src, 0))

            ivIcon.scaleType =
                ImageView.ScaleType.values()[it.getString(R.styleable.LoadingImage_android_scaleType)
                    ?.toInt() ?: 7]

            setCornersRadius(
                it.getDimension(R.styleable.LoadingImage_cornerRadius_topLeft, 0f),
                it.getDimension(R.styleable.LoadingImage_cornerRadius_topRight, 0f),
                it.getDimension(R.styleable.LoadingImage_cornerRadius_bottomRight, 0f),
                it.getDimension(R.styleable.LoadingImage_cornerRadius_bottomLeft, 0f)
            )

            setBackground(
                (it.getString(R.styleable.LoadingImage_type) ?: "0").toInt(),
                it.getColor(R.styleable.LoadingImage_android_background, 0),
                it.getDimension(R.styleable.LoadingImage_corner_radius, 0f),
                it.getDimension(R.styleable.LoadingImage_stroke_width, 0f).toInt(),
                it.getColor(R.styleable.LoadingImage_stroke_color, 0),
                it.getColor(R.styleable.LoadingImage_ripple_color, Color.LTGRAY),
                it.getBoolean(R.styleable.LoadingImage_is_clickable, false)
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

    fun setBackground(
        type: Int? = this.type,
        bgColor: Int = this.bgColor,
        cornerRadius: Float = this.cornerRadius,
        strokeWidth: Int = this.strokeWidth,
        strokeColor: Int = this.strokeColor,
        rippleColor: Int = this.rippleColor,
        isclickable: Boolean = this.isclickable
    ) {
        this.type = type
        this.bgColor = bgColor
        this.cornerRadius = cornerRadius
        this.strokeWidth = strokeWidth
        this.strokeColor = strokeColor
        this.rippleColor = rippleColor
        this.isclickable = isclickable

        val contentDrawable = GradientDrawable().apply {
            if (cornersRadius.size < 8) {
                if (type == IMG_TYPE_ICON) {
                    this.cornerRadius = 45f * dp
                } else {
                    this.cornerRadius = cornerRadius
                }
            } else {
                cornerRadii = cornersRadius
            }

            setStroke(strokeWidth, strokeColor)
            color = ColorStateList(
                arrayOf(
                    intArrayOf()
                ),
                intArrayOf(
                    try {
                        bgColor
                    } catch (e: Resources.NotFoundException) {
                        Color.TRANSPARENT
                    }
                )
            )
        }

        if (isclickable) {
            val maskDrawable = GradientDrawable().apply {
                if (cornersRadius.size < 8) {
                    if (type == IMG_TYPE_ICON) {
                        this.cornerRadius = 45f * dp
                    } else {
                        this.cornerRadius = cornerRadius
                    }
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
            return
        }

        background = contentDrawable
    }

    private fun initLoadingView(size: Int? = LOADING_SIZE_SMALL) {
        vLoading = when (size) {
            LOADING_SIZE_LARGE -> AVLoadingIndicatorView(
                context,
                null,
                R.style.AVLoadingIndicatorView_Large
            )
            LOADING_SIZE_MEDIUM -> AVLoadingIndicatorView(
                context,
                null,
                R.style.AVLoadingIndicatorView
            )
            else -> AVLoadingIndicatorView(context, null, R.style.AVLoadingIndicatorView_Small)
        }

        vLoading.setIndicator("BallScaleIndicator")
        vLoading.setIndicatorColor(Color.BLACK)

        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.startToStart = this.id
        params.endToEnd = this.id
        params.topToTop = this.id
        params.bottomToBottom = this.id
        this.addView(vLoading, params)
        vLoading.visibility = GONE
    }

    fun loadImage(url: String?) {
        url?.let {
            getBitmapFromUrlWithCoil(
                context = this.context,
                url = it,
                onStart = { setIsLoading(true) },
            ) { isSuccessful, _, bitmap ->
                setIsLoading(false)
                with(ivIcon) {
                    if (isSuccessful)
                        setImageBitmap(bitmap)
                    else setImageResource(R.drawable.placeholder)
                }
            }
        }
    }


    fun setImageResource(resId: Int) {
        ivIcon.setImageResource(resId)
    }

    fun setIsLoading(loading: Boolean) {
        isLoading = loading

        isEnabled = !loading
        vLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        ivIcon.isEnabled = enabled
        if (enabled) ivIcon.alpha = 1f
        else ivIcon.alpha = .4f
    }
}