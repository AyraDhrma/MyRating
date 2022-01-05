package id.co.cpm.myrating.utils

data class ResourceApiModel<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(data: T?): ResourceApiModel<T> =
            ResourceApiModel(status = Status.LOADING, data = data, message = null)

        fun <T> success(data: T?): ResourceApiModel<T> =
            ResourceApiModel(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ResourceApiModel<T> =
            ResourceApiModel(status = Status.ERROR, data = data, message = message)
    }
}