package com.example.videofeed.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class VideoFeedEntity (@SerializedName("title") @ColumnInfo(name = "title") val title:String?,
                            @SerializedName("subtitle") @ColumnInfo(name = "subtitle") val subtitle:String?,
                            @SerializedName("source") @ColumnInfo(name = "source") val source:String?,
                            @SerializedName("thumb") @ColumnInfo(name = "thumb") val thumb:String?,
                            @SerializedName("description") @ColumnInfo(name = "description") val description: String?) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

}