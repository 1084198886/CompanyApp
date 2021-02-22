package com.supwisdom.orderlib.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author gqy
 * @date 2019/8/23
 * @since 1.0.0
 * @see
 * @desc  我的动态
 */

class MyDynamic() : Parcelable {
    var date: String? = null
    var image: String? = null
    var content: String? = null

    constructor(parcel: Parcel) : this() {
        date = parcel.readString()
        image = parcel.readString()
        content = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(image)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyDynamic> {
        override fun createFromParcel(parcel: Parcel): MyDynamic {
            return MyDynamic(parcel)
        }

        override fun newArray(size: Int): Array<MyDynamic?> {
            return arrayOfNulls(size)
        }
    }
}
