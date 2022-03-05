package esgi.audiodb

import esgi.audiodb.artist.SearchArtist

data class SearchResponse(
    val artists: List<SearchArtist>,
)
