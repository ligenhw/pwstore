package cn.bestlang.pwstore.core

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface PwDao {
    @Query("SELECT * FROM pw")
    fun getAll(): LiveData<List<Pw>>

    @Query("SELECT * FROM pw WHERE id = :id LIMIT 1")
    fun findById(id: Int): Pw

    @Insert
    fun insert(pw: Pw)

    @Update
    fun update(pw: Pw)

    @Delete
    fun delete(pw: Pw)
}

class DateConverter {
    @TypeConverter
    fun revertDate(value: Long) = Date(value)

    @TypeConverter
    fun converterDate(value: Date) = value.time

}

@Database(entities = arrayOf(Pw::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pwDao(): PwDao
}

class DbRepo: Repo<Pw> {

    lateinit var dao: PwDao

    fun init(applicationContext: Context) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "pw.db"
        ).build()
        dao = db.pwDao()
    }

    override fun add(t: Pw) {
        dao.insert(t)
    }

    override fun del(id: Int) {
        dao.delete(dao.findById(id))
    }

    override fun update(t: Pw) {
        dao.update(t)
    }

    override fun read(id: Int): Pw {
        return dao.findById(id)
    }

    override fun all(): LiveData<List<Pw>> {
        return dao.getAll()
    }

}