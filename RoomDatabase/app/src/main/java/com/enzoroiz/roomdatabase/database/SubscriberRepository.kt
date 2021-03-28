package com.enzoroiz.roomdatabase.database

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {
    val subscribers = subscriberDAO.getAll()

    suspend fun create(subscriber: Subscriber): Long {
        return subscriberDAO.createSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int {
        return subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int {
        return subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int = subscriberDAO.deleteAll()
}