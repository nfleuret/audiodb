package esgi.audiodb.album

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Album
constructor(
    val name: String,
    val year: Int,
): Parcelable {

}