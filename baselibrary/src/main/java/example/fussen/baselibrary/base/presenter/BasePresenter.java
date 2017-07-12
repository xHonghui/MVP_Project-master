package example.fussen.baselibrary.base.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import example.fussen.baselibrary.base.view.BaseView;
import example.fussen.baselibrary.callback.RequestCallBack;
import example.fussen.baselibrary.utils.AppUtil;
import example.fussen.baselibrary.utils.LogUtil;
import rx.Subscription;


/**
 * Created by Fussen on 2016/11/24.
 * <p>
 * 所有presenter的基类
 */

public class BasePresenter<V extends BaseView, T> implements PresenterLife, RequestCallBack<T> {

    public static final String TAG = "[BasePresenter]";

    protected Subscription mSubscription; //用来取消订阅，子类赋值

    protected WeakReference<V> mView; //使用弱引用 避免内存泄露

    @Override
    public void onCreate() {
        LogUtil.fussenLog().d(TAG + "=====Presenter====onCreate========");
    }


    /**
     * 让view和presenter绑定
     *
     * @param view
     */
    @Override
    public void onBindView(@NonNull BaseView view) {
        LogUtil.fussenLog().d(TAG + "======Presenter===onBindView========");
        mView = new WeakReference<V>((V) view);
    }


    /**
     * 通过getView拿到view的对象
     *
     * @return
     */
    @Nullable
    public V getView() {
        return mView == null ? null : mView.get();
    }

    /**
     * 判断view是否与presenter已经绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null && mView.get() != null;
    }

    /**
     * 释放 presenter
     */
    @Override
    public void onDestroy() {
        LogUtil.fussenLog().d(TAG + "=====Presenter====onDestroy========");
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        // 解绑 rxjava
        AppUtil.cancelSubscription(mSubscription);
        mSubscription = null;
    }

    @Override
    public void onStart() {
        LogUtil.fussenLog().d(TAG + "====Presenter=====onStart========");
    }

    @Override
    public void onSuccess(T data) {
        LogUtil.fussenLog().d(TAG + "=====Presenter====onSuccess========");
        if (isViewAttached()) {
            mView.get().hideProgress();
        }
    }


    @Override
    public void onError(String errorMsg, boolean pullToRefresh) {

        LogUtil.fussenLog().d(TAG + "=======Presenter==onError========" + errorMsg);
        if (isViewAttached()) {
            mView.get().hideProgress();
            mView.get().showErrorMsg(errorMsg, pullToRefresh);
        }
    }

    @Override
    public void onCompleted() {
        LogUtil.fussenLog().d(TAG + "======Presenter===onCompleted========");
    }

    @Override
    public void onProgress(long downSize, long fileSize) {

    }

    @Override
    public void dowloadSuccess(String path, String fileName, long fileSize) {
        LogUtil.fussenLog().d(TAG + "=====Presenter====dowLoadSuccess========");
        if (isViewAttached()) {
            mView.get().hideProgress();
        }
    }
}
