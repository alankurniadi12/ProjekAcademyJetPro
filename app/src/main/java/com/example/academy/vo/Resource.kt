package com.example.academy.vo

class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> succes(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): Resource<T> = Resource(Status.ERROR, data, msg)

        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
    }
}