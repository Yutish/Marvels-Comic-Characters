package com.example.marvelscomiccharacters.database

import android.content.Context
import androidx.room.*

@Dao
interface CharacterDao {
    @Query("select * from DatabaseCharacter")
    fun getCharacters(): List<DatabaseCharacter>

    @Query("select * from DatabaseCharacter where DatabaseCharacter.id = :id")
    fun getCharactersById(id: Int): List<DatabaseCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg characters: DatabaseCharacter)

    @Query("select * from BookmarkedCharacter")
    fun getAllBookmarkedCharacters(): List<BookmarkedCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(vararg characters: BookmarkedCharacter)
}

@Database(entities = [DatabaseCharacter::class, BookmarkedCharacter::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}

private lateinit var INSTANCE: CharactersDatabase

fun getDatabase(context: Context): CharactersDatabase {
    synchronized(CharactersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CharactersDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}