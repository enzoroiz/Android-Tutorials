package com.enzoroiz.flickrbrowser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo(
    val title: String,
    val author: String,
    val authorId: String,
    val link: String,
    val tags: String,
    val image: String
) : Parcelable {
    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')\n"
    }
}