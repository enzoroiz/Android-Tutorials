package com.enzoroiz.roomdatabase

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.enzoroiz.roomdatabase.database.Subscriber
import com.enzoroiz.roomdatabase.database.SubscriberRepository
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainActivityViewModel(
    application: Application,
    private val repository: SubscriberRepository
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private var selectedSubscriber: Subscriber? = null
    val subscribers = repository.subscribers
    val subscriberNameMutableLiveData = MutableLiveData<String?>()
    val subscriberEmailMutableLiveData = MutableLiveData<String?>()
    val buttonSaveOrUpdateTextMutableLiveData = MutableLiveData<String>()
    val buttonClearAllOrDeleteTextMutableLiveData = MutableLiveData<String>()
    private val eventMutableLiveData = MutableLiveData<Event<String>>()
    val eventLiveData: LiveData<Event<String>>
        get() = eventMutableLiveData

    init {
        buttonSaveOrUpdateTextMutableLiveData.value = context.getString(R.string.button_save_text)
        buttonClearAllOrDeleteTextMutableLiveData.value =
            context.getString(R.string.button_clear_all_text)
    }

    fun saveOrUpdate() {
        if (isFormValid().not()) return

        selectedSubscriber?.let {
            it.name = subscriberNameMutableLiveData.value!!
            it.email = subscriberEmailMutableLiveData.value!!
            update(it)
        } ?: create(
            Subscriber(
                DEFAULT_ID,
                subscriberNameMutableLiveData.value!!,
                subscriberEmailMutableLiveData.value!!
            )
        )

        resetSubscriber()
    }

    fun clearAllOrDelete() {
        selectedSubscriber?.let { delete(it) } ?: deleteAll()

        resetSubscriber()
    }

    fun setSelectedSubscriber(subscriber: Subscriber) {
        selectedSubscriber = subscriber
        subscriberNameMutableLiveData.value = subscriber.name
        subscriberEmailMutableLiveData.value = subscriber.email
        buttonSaveOrUpdateTextMutableLiveData.value = context.getString(R.string.button_update_text)
        buttonClearAllOrDeleteTextMutableLiveData.value =
            context.getString(R.string.button_delete_text)
    }

    private fun create(subscriber: Subscriber) = viewModelScope.launch {
        val recordId = repository.create(subscriber)
        if (recordId > 0) {
            eventMutableLiveData.value =
                Event(context.getString(R.string.event_subscriber_created_successfully))
        } else {
            showGenericErrorMessage()
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch {
        val updatedRecords = repository.update(subscriber)
        if (updatedRecords > 0) {
            eventMutableLiveData.value =
                Event(context.getString(R.string.event_subscriber_updated_successfully))
        } else {
            showGenericErrorMessage()
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch {
        val deletedRecords = repository.delete(subscriber)
        if (deletedRecords > 0) {
            eventMutableLiveData.value =
                Event(context.getString(R.string.event_subscriber_deleted_successfully))
        } else {
            showGenericErrorMessage()
        }
    }

    private fun deleteAll() = viewModelScope.launch {
        val deletedRecords = repository.deleteAll()
        if (deletedRecords > 0) {
            eventMutableLiveData.value =
                Event(context.getString(R.string.event_all_subscribers_deleted_successfully))
        } else {
            showGenericErrorMessage()
        }
    }

    private fun resetSubscriber() {
        subscriberNameMutableLiveData.value = null
        subscriberEmailMutableLiveData.value = null
        selectedSubscriber = null
        buttonSaveOrUpdateTextMutableLiveData.value = context.getString(R.string.button_save_text)
        buttonClearAllOrDeleteTextMutableLiveData.value =
            context.getString(R.string.button_clear_all_text)
    }

    private fun isFormValid(): Boolean {
        if (subscriberNameMutableLiveData.value.isNullOrEmpty()) {
            eventMutableLiveData.value = Event(context.getString(R.string.event_subscriber_name_empty))
            return false
        }

        if (subscriberEmailMutableLiveData.value.isNullOrEmpty()) {
            eventMutableLiveData.value = Event(context.getString(R.string.event_subscriber_email_empty))
            return false
        } else if (Patterns.EMAIL_ADDRESS.matcher(subscriberEmailMutableLiveData.value!!).matches().not()) {
            eventMutableLiveData.value = Event(context.getString(R.string.event_subscriber_email_invalid))
            return false
        }

        return true
    }

    private fun showGenericErrorMessage() {
        eventMutableLiveData.value = Event(context.getString(R.string.generic_error_message))
    }

    companion object {
        const val DEFAULT_ID = 0L
    }
}