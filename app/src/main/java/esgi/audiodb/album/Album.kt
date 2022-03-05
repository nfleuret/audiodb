package esgi.audiodb.album

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Album
constructor(
    val idAlbum: String,
    val strAlbum: String,
    val intYearReleased: Int,
    val strAlbumThumb: String,
    val intScore: String?,
    val intScoreVotes: String?,
    val strDescriptionEN: String,
    val strDescriptionFR: String?,
    val strArtist: String
): Parcelable {

}