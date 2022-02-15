package esgi.audiodb.album

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Album
constructor(
    val strAlbum: String,
    val intYearReleased: Int,
    val strAlbumThumb: String
): Parcelable {

}