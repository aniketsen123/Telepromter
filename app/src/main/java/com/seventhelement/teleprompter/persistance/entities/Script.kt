package com.seventhelement.teleprompter.persistance.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "script")
class Script : Parcelable {
    @get:NonNull
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @get:Nullable
    @Nullable
    @ColumnInfo(name = "title")
    var title: String? = null

    @get:Nullable
    @Nullable
    @ColumnInfo(name = "uri")
    var uri: String? = null

    @get:Nullable
    @Nullable
    @ColumnInfo(name = "body")
    var body: String? = null

    @get:NonNull
    @NonNull
    @ColumnInfo(name = "date_created_milliseconds")
    var dateInMilli: Long = 0

    constructor()
    internal constructor(`in`: Parcel) {
        id = `in`.readInt()
        title = `in`.readString()
        uri = `in`.readString()
        body = `in`.readString()
        dateInMilli = `in`.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeString(uri)
        dest.writeString(body)
        dest.writeLong(dateInMilli)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Script?> = object : Parcelable.Creator<Script?> {
            override fun createFromParcel(`in`: Parcel): Script? {
                return Script(`in`)
            }

            override fun newArray(size: Int): Array<Script?> {
                return arrayOfNulls(size)
            }
        }
    }
}
