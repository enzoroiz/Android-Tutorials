package com.enzoroiz.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val retrofitInstance = RetrofitInstance.getRetrofitInstance()
    private val albumsService = retrofitInstance.create(AlbumsService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getAlbum(3)
//        getAlbums()
//        getAlbums(10)
        createAlbum(AlbumItem(0, "Album Title Custom", 1))
    }

    private fun getAlbums(userId: Int? = null) {
        liveData {
            userId?.let {
                emit(albumsService.getAlbumsByUserId(it))
            } ?: emit(albumsService.getAlbums())
        }.also {
            it.observe(this, Observer { response ->
                response.body()?.listIterator()?.let { iterator ->
                    while (iterator.hasNext()) {
                        val album = iterator.next()
                        with(album) {
                            Log.e(this.javaClass.simpleName, toString())
                            txtAlbums.append(getAlbumData(this))
                        }
                    }
                }
            })
        }
    }

    private fun getAlbum(id: Int) {
        liveData {
            emit(albumsService.getAlbum(id))
        }.also {
            it.observe(this, Observer { response ->
                response.body()?.let { album ->
                    with(album) {
                        Log.e(this.javaClass.simpleName, toString())
                        txtAlbums.append(getAlbumData(this))
                    }
                }
            })
        }
    }

    private fun createAlbum(albumItem: AlbumItem) {
        liveData {
            emit(albumsService.createAlbum(albumItem))
        }.also {
            it.observe(this, Observer { response ->
                response.body()?.let { album ->
                    with(album) {
                        Log.e(this.javaClass.simpleName, toString())
                        txtAlbums.append(getAlbumData(this))
                    }
                }
            })
        }
    }

    private fun getAlbumData(album: AlbumItem): String =
        "Id: ${album.id} - UserId: ${album.userId} - Title: ${album.title}\n\n"
}