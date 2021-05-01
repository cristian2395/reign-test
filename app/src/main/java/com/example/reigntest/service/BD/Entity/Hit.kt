package com.example.reigntest.service.BD.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Hit")
data class Hit(
    @SerializedName("author") @ColumnInfo(name = "author") val author: String?,
    @SerializedName("comment_text") @ColumnInfo(name = "comment_text") val commentText: String?,
    @SerializedName("created_at") @ColumnInfo(name = "created_at") val createdAt: String?,
    @SerializedName("created_at_i") @ColumnInfo(name = "created_at_i") val createdAtI: Int?,
    @SerializedName("num_comments") @ColumnInfo(name = "num_comments") val numComments: String?,
    @SerializedName("objectID") @ColumnInfo(name = "objectID") val objectID: String?,
    @SerializedName("parent_id") @ColumnInfo(name = "parent_id") val parentId: Int?,
    @SerializedName("points") @ColumnInfo(name = "points") val points: String?,
    @SerializedName("story_id") @ColumnInfo(name = "story_id") val storyId: Int?,
    @SerializedName("story_text") @ColumnInfo(name = "story_text") val storyText: String?,
    @SerializedName("story_title") @ColumnInfo(name = "story_title") val storyTitle: String?,
    @SerializedName("story_url") @ColumnInfo(name = "story_url") val storyUrl: String?,
    @SerializedName("title") @ColumnInfo(name = "title") val title: String?,
    @SerializedName("url") @ColumnInfo(name = "url") val url: String?
)  {
    companion object {
        const val TABLE_NAME = "Hit"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hit_id") var hitId: Int = 0
}