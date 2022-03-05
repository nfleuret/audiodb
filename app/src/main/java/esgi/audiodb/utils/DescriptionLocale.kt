package esgi.audiodb.utils

import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import java.util.*

class DescriptionLocale {

    companion object {
        fun getDescriptionOfArtist(artist: Artist): String {
            var description = artist.strBiographyEN;
            if(Locale.getDefault().getISO3Language() === "fre" && artist.strBiographyFR !== null) {
                description = artist.strBiographyFR;
            }

            return description;
        }

        fun getDescriptionOfAlbum(album: Album): String {
            var description = album.strDescriptionEN;
            if(Locale.getDefault().getISO3Language() === "fre" && album.strDescriptionFR !== null) {
                description = album.strDescriptionFR;
            }

            return description;
        }
    }
}