package com.application.haominwu.randomcatapplication.util;

import com.application.haominwu.randomcatapplication.model.Cat;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DataAgent {
    private static final DataAgent ourInstance = new DataAgent();

    public static DataAgent getInstance() {
        return ourInstance;
    }

    private DataAgent() {
    }

    public Observable getACat(){

        Observable observable = Observable.create((ObservableOnSubscribe<Cat>) emitter -> HttpUtil.getInstance().fetchACatApiCall().subscribe(new Observer<JSONObject>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {
                Cat cat = GsonUtil.getInstance().fromJson(jsonObject.toString(), Cat.class);
                emitter.onNext(cat);
                emitter.onComplete();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                
            }
        })).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
