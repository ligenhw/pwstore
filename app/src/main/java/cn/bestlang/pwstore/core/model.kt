package cn.bestlang.pwstore.core

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pw")
data class Pw(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val account: String = "",
    val pw: String = "",
    val type: Int = 0,
    val desc: String = "",
    val time: Date = Date()
)

