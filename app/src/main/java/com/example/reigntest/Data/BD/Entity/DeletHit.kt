package com.example.reigntest.Data.BD.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "DeletHit")
data class DeletHit(
    @ColumnInfo(name = "objectID") @NotNull val objectID: String,
)  {
    companion object {
        const val TABLE_NAME = "DeletHit"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hit_id") var hitId: Int = 0
}