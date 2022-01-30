package esgi.audiodb.song

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Song constructor(
    val name: String,
): Parcelable {

}

