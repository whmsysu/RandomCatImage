package com.application.haominwu.randomcatapplication.presenter;

import com.application.haominwu.randomcatapplication.contract.CatDisplayContract;
import com.application.haominwu.randomcatapplication.model.Cat;
import com.application.haominwu.randomcatapplication.util.DataAgent;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class CatImagePresenter implements CatDisplayContract.Presenter{

    private Cat cat;
    private CatDisplayContract.View mView;

    @Inject
    public CatImagePresenter(CatDisplayContract.View view) {
        mView = view;
    }

    /**
     * Fetch a random cat and then fetch another random cat
     */
    public void fetchARandomCatOneByOne() {
        //Link call
        mView.showLoading();
        DataAgent.getInstance().getACat().flatMap(new Function<Cat, Observable<Cat>>() {
            @Override
            public Observable<Cat> apply(Cat cat) throws Exception {
                return DataAgent.getInstance().getACat();
            }
        }).subscribe(new Observer<Cat>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Cat nowCat) {
                cat = nowCat;
                mView.updateCatImage(cat.getFile());
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Fetch two random cats, choose one to display
     */
    public void fetchARandomCatByTwoApiCall(){
//        Merge call
        mView.showLoading();
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
                ToastUtils.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {
                Random random = new Random();
                int index = random.nextInt(1);
                cat = cats.get(index);
                mView.updateCatImage(cat.getFile());
            }
        });

    }

    @Override
    public void dropView() {
        mView = null;
    }
}
