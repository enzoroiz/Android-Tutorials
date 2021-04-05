package com.enzoroiz.newsapp.repository

import com.enzoroiz.newsapp.data.api.NewsService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileInputStream

private const val RESPONSE_FILE_PATH = "src/test/resources/repository/news.json"

class NewsServiceTest {
    private lateinit var service: NewsService
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(server.url(""))
            .build()
            .create(NewsService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should request news headlines`() {
        runBlocking {
            enqueueRequestResponse(RESPONSE_FILE_PATH)
            val response = service.getNewsHeadlines(1, "br")
            val request = server.takeRequest()
            assertNotNull(response)
            assertEquals("/v2/top-headlines?page=1&country=br&apiKey=fd58eacaa9ef46c693b192596f1fe54d", request.path)

            assertNotNull(response.body())
            val articles = response.body()!!.articles
            assertEquals(20, articles.size)

            val firstArticle = articles.first()
            assertEquals("UOL", firstArticle.author)
            assertEquals("Botafogo faz gol relâmpago, mas empata com Portuguesa com um a mais - UOL Esporte", firstArticle.title)
            assertEquals("Jogando no estádio Giulite Coutinho, em Mesquita, por causa da medida da prefeitura do Rio de Janeiro, o Botafogo empatou com a Portuguesa por 1 a 1, com gol relâmpago de Felipe Ferreira no primeiro minuto e com Chay empatando para os visitantes.O resulta", firstArticle.description)
        }
    }

    private fun enqueueRequestResponse(filePath: String) {
        val buffer = FileInputStream(filePath).source().buffer()
        val mockResponse = MockResponse().apply { setBody(buffer.readString(Charsets.UTF_8)) }
        server.enqueue(mockResponse)
    }
}