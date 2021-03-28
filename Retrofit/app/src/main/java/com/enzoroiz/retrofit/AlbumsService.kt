package com.enzoroiz.retrofit

import retrofit2.Response
import retrofit2.http.*

interface AlbumsService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    @GET("/albums")
    suspend fun getAlbumsByUserId(@Query("userId") userId: Int): Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id") id: Int): Response<AlbumItem>

    @POST("/albums")
    suspend fun createAlbum(@Body albumItem: AlbumItem): Response<AlbumItem>
}