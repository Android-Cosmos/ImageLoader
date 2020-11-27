package me.rosuh.library

import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * 加载认证 icon
 */
fun ImageView?.loadIcon(
    url: String?,
    placeHolderResId: Int = ImageLoader.placeholderIcon,
    errorResId: Int = ImageLoader.placeholderIcon,
) {
    if (this == null) {
        return
    }
    Glide.with(context)
        .load(url)
        .apply(buildIconRequestOptions(placeHolderResId, errorResId))
        .into(this)
}

/**
 * 加载头像
 */
fun ImageView?.loadAvatar(
    avatarUrl: String?,
    placeHolderResId: Int = ImageLoader.placeholderIcon,
    errorResId: Int = ImageLoader.placeholderIcon,
) {
    if (this == null) {
        return
    }
    Glide.with(this)
        .asBitmap()
        .load(avatarUrl)
        .apply(avatarRequestOptions(placeHolderResId, errorResId))
        .into(this)
}

/**
 * 加载背景、banner
 */
fun ImageView?.loadBg(
    bgUrl: String? = null,
    placeHolderResId: Int = ImageLoader.placeholderIcon,
    errorResId: Int = ImageLoader.placeholderIcon,
) {
    if (this == null) {
        return
    }
    Glide.with(this)
        .load(bgUrl)
        .apply(bannerRequestOptions(placeHolderResId, errorResId))
        .into(this)
}

/**
 * 加载背景、banner
 */
fun ImageView?.loadCover(
    bgUrl: String? = null,
    placeHolderResId: Int = ImageLoader.placeholderIcon,
    errorResId: Int = ImageLoader.placeholderIcon,
) {
    if (this == null) {
        return
    }
    Glide.with(this)
        .load(bgUrl)
        .apply(bannerRequestOptions())
        .into(this)
}

fun ImageView?.load(
    url: String? = null
) {
    if (this == null) {
        return
    }
    Glide.with(this)
        .load(url)
        .into(this)
}


fun ImageView?.startDrawable() {
    if (this == null || this.drawable !is AnimationDrawable) {
        return
    }
    (this.drawable as? AnimationDrawable)?.start()
}

fun ImageView?.stopDrawable() {
    if (this == null || this.drawable !is AnimationDrawable) {
        return
    }
    (this.drawable as? AnimationDrawable)?.stop()
}

fun ImageView?.loadChatImg(
    url: String? = null,
    maxEdge: Float = 500f,
    placeHolderResId: Int = ImageLoader.placeholderIcon,
    errorResId: Int = ImageLoader.placeholderIcon,
    isLongImage: (Boolean) -> Unit = {}
) {
    if (this == null) {
        return
    }
    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(cardRequestOptions(placeHolderResId, errorResId))
        .into(object : CustomViewTarget<ImageView?, Bitmap?>(this) {
            override fun onResourceLoading(placeholder: Drawable?) {
                this@loadChatImg.setImageDrawable(placeholder)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                this@loadChatImg.setImageDrawable(errorDrawable)
            }

            override fun onResourceCleared(placeholder: Drawable?) {
                this@loadChatImg.setImageDrawable(null)
            }

            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap?>?) {
                var w = bitmap.width
                var h = bitmap.height
                val widthNeedScale: Boolean =
                    w > maxEdge
                val heightNeedScale: Boolean =
                    h > maxEdge
                val widthRatio: Float = w / maxEdge
                val heightRatio: Float = h / maxEdge
                val srcRatio = (w + 0.0f) / h
                val isLongImg = h > 1920 && heightNeedScale
                // 设置长图提示
                isLongImage.invoke(isLongImg)
                //宽图以宽为基准做变换计算，大于imageMaxEdgeDip则设置imageMaxEdgeDip，宽随高变化
                if (w > h) {
                    w = if (widthNeedScale) maxEdge.toInt() else w
                    h = if (widthNeedScale) (h / widthRatio).toInt() else h

                    this@loadChatImg.updateLayoutParams {
                        this.height = h
                        this.width = w
                    }
                    this@loadChatImg.setImageBitmap(bitmap)
                } else {
                    // 对于超长图，直接等比例剪裁顶部部分显示即可
                    h =
                        if (heightNeedScale) maxEdge.toInt() else h
                    w = (h * srcRatio).toInt()
                    // 先用原始比例计算宽度
                    if (w > maxEdge) {
                        // 超宽用最大宽度
                        w = maxEdge.toInt()
                    } else if (w <= maxEdge / 2) {
                        // 太小则限制最小为 IMAGE_MAX_EDGE_DP / 2
                        w = maxEdge.toInt() / 2
                    }
                    this@loadChatImg.updateLayoutParams {
                        this.width = w
                        this.height = h
                    }
                    if (isLongImg) {
                        // 长图使用系统方法缩略显示，引导用户点开查看
                        val thumbnailBitmap = ThumbnailUtils.extractThumbnail(bitmap, w, h)
                        this@loadChatImg.setImageBitmap(thumbnailBitmap)
                    } else {
                        // 非长图直接显示
                        this@loadChatImg.setImageBitmap(bitmap)
                    }
                }
            }
        })
}

fun buildIconRequestOptions(
    placeHolderResId: Int = ImageLoader.placeholderIcon,
    errorResId: Int = ImageLoader.errorIcon
): RequestOptions {
    return buildReqOptions(placeHolderResId, errorResId)
}

fun avatarRequestOptions(
    placeHolderResId: Int = ImageLoader.placeholderAvatar,
    errorResId: Int = ImageLoader.errorAvatar
): RequestOptions {
    return buildReqOptions(placeHolderResId, errorResId)
}

fun bannerRequestOptions(
    placeHolderResId: Int = ImageLoader.placeholderBanner,
    errorResId: Int = ImageLoader.errorBanner
): RequestOptions {
    return buildReqOptions(placeHolderResId, errorResId)
}

fun cardRequestOptions(
    placeHolderResId: Int = ImageLoader.placeholderCardView,
    errorResId: Int = ImageLoader.errorCardView
): RequestOptions {
    return buildReqOptions(placeHolderResId, errorResId)
}

private fun buildReqOptions(placeHolderResId: Int, errorResId: Int): RequestOptions {
    return RequestOptions()
        .centerCrop()
        .placeholder(placeHolderResId)
        .error(errorResId)
}