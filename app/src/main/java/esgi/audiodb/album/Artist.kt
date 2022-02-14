package esgi.audiodb.album

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Artist constructor(
    val idArtist: String,
    val strArtist: String,
    val strCountry: String,
    val strArtistThumb: String
): Parcelable {
}