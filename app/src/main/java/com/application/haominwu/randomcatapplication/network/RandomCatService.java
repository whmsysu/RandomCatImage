package com.application.haominwu.randomcatapplication.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;


public interface RandomCatService {

    @GET("meow")
    Observable<ResponseBody> randomACat();
}
