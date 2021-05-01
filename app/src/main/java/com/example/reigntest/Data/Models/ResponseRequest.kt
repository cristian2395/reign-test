package com.example.reigntest.Data.Models


data class ResponseRequest<out T>(val status: Boolean, val data: T, val message: String)


