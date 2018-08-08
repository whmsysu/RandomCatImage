package com.application.haominwu.randomcatapplication.presenter;

import com.application.haominwu.randomcatapplication.model.Cat;
import com.application.haominwu.randomcatapplication.util.DataAgent;
import com.application.haominwu.randomcatapplication.view.CatImageDisplayView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class CatImagePresenter {

    private Cat cat;
    private CatImageDisplayView baseView;

    public CatImagePresenter(CatImageDisplayView baseView) {
        this.baseView = baseView;
    }

    public void onDestroy() {
        this.baseView = null;
    }

    /**
     * Fetch two random cats, choose one to display
     */
    public void fetchARandomCat(){
        //Link call
//        this.baseView.showLoading();
//        DataAgent.getInstance().getACat().flatMap(new Function<Cat, Observable<Cat>>() {
//            @Override
//            public Observable<Cat> apply(Cat cat) throws Exception {
//                return DataAgent.getInstance().getACat();
//            }
//        }).subscribe(new Observer<Cat>(){
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Cat nowCat) {
//                cat = nowCat;
//                baseView.updateImage(cat.getFile());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        Merge call
        this.baseView.showLoading();
        final List<Cat> cats = new ArrayList<>();
        Observable<Object> merge = Observable.merge(DataAgent.getInstance().getACat(), DataAgent.getInstance().getACat());
        merge.subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                cats.add((Cat)o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Random random = new Random();
                int index = random.nextInt(1);
                cat = cats.get(index);
                baseView.updateImage(cat.getFile());
            }
        });

    }
}
