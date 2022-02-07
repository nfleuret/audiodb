package esgi.audiodb

import esgi.audiodb.artist.Artist

data class SearchResponse(
    val artists: List<Artist>,
)
