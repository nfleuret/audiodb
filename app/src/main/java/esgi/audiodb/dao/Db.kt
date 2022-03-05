package esgi.audiodb.dao

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Artist(@PrimaryKey @ColumnInfo(name = "id") val name: String, val image: String, val descriptionEn: String, val descriptionFr: String?, val country: String, val artistId: String)

@Entity
data class Album(@PrimaryKey @ColumnInfo(name = "id") val name: String, val image: String, val idAlbum: String, val yearReleased: Int,val scoresVote: String?, val votesNumber: String?,  val descriptionEn: String, val descriptionFr: String?, val artistName: String)

@Dao
interface DbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtist(artist: Artist)

    @Delete
    fun deleteArtist(artist: Artist)

    @Query("SELECT * FROM Artist")
    fun listArtists() : Flow<List<Artist>>

    @Query("SELECT * FROM Artist WHERE id = :artistName")
    fun artistByName(artistName: String): Flow<List<Artist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbum(artist: Album)

    @Delete
    fun deleteAlbum(album: Album)

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
        AppDatabase::class.java, "audiodb5.sqlite",
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

    fun deleteAlbum(album: Album) {
        db.artistDao().deleteAlbum(album);
    }

    fun listenToArtistByName(artistName: String): Flow<List<Artist>> {
        return db.artistDao().artistByName(artistName)
    }

    fun listenToAlbumByName(albumName: String): Flow<List<Album>> {
        return db.artistDao().albumByName(albumName)
    }

    fun listenToArtistsChanges(): Flow<List<Artist>> {
        return db.artistDao().listArtists()
    }

     fun listenToAlbumsChanges(): Flow<List<Album>> {
        return db.artistDao().listAlbums()
    }
}