package com.mperezt.rick.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

class LogInterceptor : Interceptor {
    private val TAG = "ApiClient"
    private val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestTime = System.nanoTime()

        val requestLog = StringBuilder().apply {
            append("┌────── Request ──────\n")
            append("│ URL: ${request.url}\n")
            append("│ Method: ${request.method}\n")
            append("│ Headers:\n")
            request.headers.forEach {
                append("│   ${it.first}: ${it.second}\n")
            }

            request.body?.let {
                append("│ Body:\n")
                val buffer = Buffer()
                it.writeTo(buffer)
                append("│ ${buffer.readString(UTF8)}\n")
            }
            append("└───────────────────")
        }
        Log.d(TAG, requestLog.toString())

        val response = chain.proceed(request)
        val responseTime = System.nanoTime()

        val responseLog = StringBuilder().apply {
            append("┌────── Response ──────\n")
            append("│ URL: ${response.request.url}\n")
            append("│ Code: ${response.code}\n")
            append("│ Time: ${(responseTime - requestTime) / 1_000_000} ms\n")
            append("│ Headers:\n")
            response.headers.forEach {
                append("│   ${it.first}: ${it.second}\n")
            }

            response.body?.let { responseBody ->
                val source = responseBody.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer.clone()
                append("│ Body:\n")
                append("│ ${buffer.readString(UTF8)}\n")
            }
            append("└───────────────────")
        }
        Log.d(TAG, responseLog.toString())

        return response
    }
}