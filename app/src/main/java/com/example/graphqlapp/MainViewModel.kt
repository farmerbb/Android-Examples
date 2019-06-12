package com.example.graphqlapp

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import ArtistSearchQuery
import ArtistDetailsQuery
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import type.CustomType

class MainViewModel: ViewModel() {
    private val disposables = CompositeDisposable()

    private val okHttp = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor.Builder()
                    .loggable(true)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("GraphQL-Example")
                    .response("GraphQL-Example")
                    .build()
            ).build()

    private val apolloClient = ApolloClient.builder()
            .serverUrl(BuildConfig.GRAPHQL_ENDPOINT)
            .okHttpClient(okHttp)
            .addCustomTypeAdapter(CustomType.URLSTRING, URLStringAdapter())
            .addCustomTypeAdapter(CustomType.MBID, MBIDAdapter())
            .addCustomTypeAdapter(CustomType.DATE, DateAdapter())
            .build()

    val searchResults = MutableLiveData<ArtistSearchQuery.Data>()
    val detailsResults = MutableLiveData<ArtistDetailsQuery.Data>()

    fun search(query: String) {
        disposables.apply {
            clear()
            add(Rx2Apollo.from(apolloClient.query(
                    ArtistSearchQuery(input = query)
            )).firstElement()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        searchResults.value = it.data()
                    }, {
                        it.printStackTrace()
                    })
            )
        }
    }

    fun details(mbid: String) {
        disposables.apply {
            clear()
            add(Rx2Apollo.from(apolloClient.query(
                    ArtistDetailsQuery(mbid = mbid)
            )).firstElement()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        detailsResults.value = it.data()
                    }, {
                        it.printStackTrace()
                    })
            )
        }
    }

    override fun onCleared() = disposables.clear()

    class URLStringAdapter: CustomTypeAdapter<Uri> {
        override fun encode(value: Uri) = CustomTypeValue.fromRawValue(value.toString())
        override fun decode(value: CustomTypeValue<*>) = Uri.parse(value.value.toString())
    }

    class MBIDAdapter: CustomTypeAdapter<String> {
        override fun encode(value: String) = CustomTypeValue.fromRawValue(value)
        override fun decode(value: CustomTypeValue<*>) = value.value.toString()
    }

    class DateAdapter: CustomTypeAdapter<String> {
        override fun encode(value: String) = CustomTypeValue.fromRawValue(value)
        override fun decode(value: CustomTypeValue<*>) = value.value.toString()
    }
}