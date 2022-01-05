package id.co.cpm.myrating.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.co.cpm.myrating.datasource.MainRepository
import id.co.cpm.myrating.datasource.RequestModel
import id.co.cpm.myrating.utils.ResourceApiModel
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getInformation(requestModel: RequestModel) = liveData(Dispatchers.IO) {
        emit(ResourceApiModel.loading(data = null))
        try {
            emit(ResourceApiModel.success(data = mainRepository.getInformation(requestModel)))
        } catch (e: Exception) {
            emit(
                ResourceApiModel.error(
                    data = null,
                    message = e.localizedMessage ?: "Something Went Wrong"
                )
            )
        }
    }

    fun saveRating(requestModel: RequestModel) = liveData(Dispatchers.IO) {
        emit(ResourceApiModel.loading(data = null))
        try {
            emit(ResourceApiModel.success(data = mainRepository.saveRating(requestModel)))
        } catch (e: Exception) {
            emit(
                ResourceApiModel.error(
                    data = null,
                    message = e.localizedMessage ?: "Something Went Wrong"
                )
            )
        }
    }

}