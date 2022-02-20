package esgi.audiodb.dao

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity
data class Artist(@PrimaryKey @ColumnInfo(name = "id") val name: String, val image: String)

@Entity
data class Album(@PrimaryKey @ColumnInfo(name = "id") val name: String, val image: String, val artistName: String)

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

    @Query("SELECT * FROM Artist WHERE id = :artistName")
    fun artistByName(artistName: String): Flow<List<Artist>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbum(artist: Album)

    @Update
    fun updateAlbum(artist: Album)

    @Delete
    fun deleteAlbum(artist: Album)

    @Query("SELECT * FROM Album")
    fun listAlbums() : Flow<List<Album>>

    @Query("SELECT * FROM Album WHERE  id = :label")
    fun albumByName(label: String): Flow<List<Album>>
}

@Database(entities = [Artist::class, Album::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): DbDao
}

class DatabaseManager(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "audiodb.sqlite",
    ).build()


    fun addArtist(artist: Artist) {
        db.artistDao().addArtist(artist)
    }

    fun addAlbum(album: Album) {
        db.artistDao().addAlbum(album)
    }

    fun deleteArtist(artist: Artist) {
        db.artistDao().deleteArtist(artist);
    }

    fun listAlbum() {
        db.artistDao().listAlbums()
    }

    fun listenToArtistByName(artistName: String): Flow<List<Artist>> {
        return db.artistDao().artistByName(artistName)
    }

    fun listenToAlbumByName(albumName: String): Flow<List<Album>> {
        return db.artistDao().albumByName(albumName)
    }

    suspend fun listenToArtistsChanges(): Flow<List<Artist>> {
        return db.artistDao().listArtists()
    }
}