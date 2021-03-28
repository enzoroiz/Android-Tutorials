package com.enzoroiz.top10downloader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception
import java.util.*

class XMLApplicationsParser {
    val applications = arrayListOf<FeedEntry>()

    fun parse(xmlData: String): Boolean {
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentEntry = FeedEntry()

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name?.toLowerCase(Locale.US)

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == "entry") inEntry = true
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
                        if (inEntry) {
                            when (tagName) {
                                "entry" -> {
                                    applications.add(currentEntry)
                                    inEntry = false
                                    currentEntry = FeedEntry()
                                }

                                "name" -> currentEntry.name = textValue
                                "artist" -> currentEntry.artist = textValue
                                "releasedate" -> currentEntry.releaseDate = textValue
                                "summary" -> currentEntry.summary = textValue
                                "image" -> currentEntry.imageURL = textValue
                            }
                        }
                    }
                }

                eventType = xpp.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}