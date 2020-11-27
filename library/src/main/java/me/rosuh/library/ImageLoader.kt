package me.rosuh.library

import androidx.annotation.IntegerRes

object ImageLoader {

    @IntegerRes
    internal var errorIcon: Int = -1

    @IntegerRes
    internal var placeholderIcon: Int = -1

    @IntegerRes
    internal var errorAvatar: Int = -1

    @IntegerRes
    internal var placeholderAvatar: Int = -1

    @IntegerRes
    internal var errorCardView: Int = -1

    @IntegerRes
    internal var placeholderCardView: Int = -1

    @IntegerRes
    internal var errorBanner: Int = -1

    @IntegerRes
    internal var placeholderBanner: Int = -1

    data class Config(
        @IntegerRes
        internal var errorAvatar: Int = -1,

        @IntegerRes
        internal var placeholderAvatar: Int = -1,

        @IntegerRes
        internal var errorCardView: Int = -1,

        @IntegerRes
        internal var placeholderCardView: Int = -1,

        @IntegerRes
        internal var errorBanner: Int = -1,

        @IntegerRes
        internal var placeholderBanner: Int = -1,
        @IntegerRes
        internal var errorIcon: Int = -1,

        @IntegerRes
        internal var placeholderIcon: Int = -1,
    ) {
        fun errorAvatar(resId: Int) = apply { this.errorAvatar = resId }

        fun placeHolderAvatar(resId: Int) = apply { this.placeholderAvatar = resId }

        fun errorCardView(resId: Int) = apply { this.errorCardView = resId }

        fun placeHolderCardView(resId: Int) = apply { this.placeholderCardView = resId }

        fun errorBanner(resId: Int) = apply { this.errorBanner = resId }

        fun placeHolderBanner(resId: Int) = apply { this.placeholderBanner = resId }

        fun errorIcon(resId: Int) = apply { this.errorIcon = resId }

        fun placeHolderIcon(resId: Int) = apply { this.placeholderIcon = resId }

        fun build(): ImageLoader {
            ImageLoader.also {
                ImageLoader.errorAvatar = this.errorAvatar
                ImageLoader.errorBanner = this.errorBanner
                ImageLoader.errorCardView = this.errorCardView
                ImageLoader.errorIcon = this.errorIcon
                ImageLoader.placeholderAvatar = this.placeholderAvatar
                ImageLoader.placeholderBanner = this.placeholderBanner
                ImageLoader.placeholderCardView = this.placeholderCardView
                ImageLoader.placeholderIcon = this.placeholderIcon
            }
            return ImageLoader
        }
    }
}