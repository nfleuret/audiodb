package esgi.audiodb.dao

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Artist(@PrimaryKey @ColumnInfo(name = "id") val name: String, val image: String)

@Dao
interface DbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtist(artist: Artist)

    @Update
    fun updateArtist(artist: Artist)

    @Delete
    fun deleteArtist(artist: Artist)

    @Query("SELECT * FROM Artist")
    fun listArtists() : Flow<List<Artist>>
}

@Database(entities = [Artist::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animalDao(): DbDao
}

class DatabaseManager(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "db.sqlite",
    ).build()


    fun addAnimal(artist: Artist) {
        db.animalDao().addArtist(artist)
    }

    suspend fun listenToArtistsChanges(): Flow<List<Artist>> {
        return db.animalDao().listArtists()
    }
}
