import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity
import com.example.app.dicodingeventsapp.data.remote.response.Event
import com.example.app.dicodingeventsapp.data.remote.response.ListEventsItem
import com.example.app.dicodingeventsapp.domain.repository.LocalRepository
import com.example.app.dicodingeventsapp.domain.useCase.GetEventDetailUseCase
import com.example.app.dicodingeventsapp.domain.useCase.GetFinishedEventsUseCase
import com.example.app.dicodingeventsapp.domain.useCase.GetUpcomingEventsUseCase
import com.example.app.dicodingeventsapp.domain.useCase.SearchEventsUseCase
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState
import kotlinx.coroutines.launch

class EventViewModel(
    private val getUpcomingEventsUseCase: GetUpcomingEventsUseCase,
    private val getFinishedEventsUseCase: GetFinishedEventsUseCase,
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val searchEventsUseCase: SearchEventsUseCase,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _upcomingEvents = MutableLiveData<ResponseState<List<ListEventsItem>>>()
    val upcomingEvents: LiveData<ResponseState<List<ListEventsItem>>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<ResponseState<List<ListEventsItem>>>()
    val finishedEvents: LiveData<ResponseState<List<ListEventsItem>>> = _finishedEvents

    private val _eventDetail = MutableLiveData<ResponseState<Event>>()
    val eventDetail: LiveData<ResponseState<Event>> = _eventDetail

    private val _searchResults = MutableLiveData<ResponseState<List<ListEventsItem>>>()
    val searchResults: LiveData<ResponseState<List<ListEventsItem>>> = _searchResults


    fun getUpcomingEvents() {
        _upcomingEvents.postValue(ResponseState.Loading)
        viewModelScope.launch {
            val result = getUpcomingEventsUseCase.invoke()
            _upcomingEvents.postValue(result)
        }
    }

    fun getFinishedEvents() {
        _finishedEvents.postValue(ResponseState.Loading)
        viewModelScope.launch {
            val result = getFinishedEventsUseCase.invoke()
            _finishedEvents.postValue(result)
        }
    }

    fun getEventDetail(eventId: String) {
        _eventDetail.postValue(ResponseState.Loading)
        viewModelScope.launch {
            val result = getEventDetailUseCase.invoke(eventId)
            _eventDetail.postValue(result)
        }
    }

    fun searchEvents(query: String) {
        _searchResults.postValue(ResponseState.Loading)
        viewModelScope.launch {
            val result = searchEventsUseCase.invoke(query)
            _searchResults.postValue(result)
        }
    }

    fun addFavoriteEvent(event: FavoriteEventEntity) {
        viewModelScope.launch {
            localRepository.insertFavoriteEvent(event)
        }
    }

    fun removeFavoriteEvent(eventId: String) {
        viewModelScope.launch {
            localRepository.deleteFavoriteEvent(eventId)
        }
    }

    fun isEventFavorite(eventId: String): LiveData<Boolean> {
        val favoriteStatus = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val isFavorite = localRepository.checkFavoriteEvent(eventId)
            favoriteStatus.postValue(isFavorite)
        }
        return favoriteStatus
    }



    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> {
        return localRepository.getAllFavoriteEvents()
    }
}
