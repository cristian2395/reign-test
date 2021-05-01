package com.example.reigntest.Data.Models

import com.example.reigntest.Data.BD.Entity.Hit
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("hits") val hits: List<Hit>?,
    @SerializedName("nbHits") val nbHits: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("nbPages") val nbPages: Int?,
    @SerializedName("hitsPerPage") val hitsPerPage: Int?,
    @SerializedName("exhaustiveNbHits") val exhaustiveNbHits: Boolean?,
    @SerializedName("query") val query: String?,
    @SerializedName("params") val params: String?,
    @SerializedName("processingTimeMS") val processingTimeMS: Int?
)