package example.fussen.baselibrary.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.fussen.baselibrary.R;
import example.fussen.baselibrary.base.presenter.PresenterLife;
import example.fussen.baselibrary.base.view.BaseView;
import example.fussen.baselibrary.utils.AppUtil;
import rx.Subscription;

/**
 * Created by Fussen on 2017/1/9.
 * mvp fragment，rxjava的订阅与反订阅，内存的释放、fargment异常关闭状态保存、butterknife解绑、用户界面等等。
 */

public abstract class MvpFragment<CV extends View, P extends PresenterLife, V extends BaseView> extends Fragment implements BaseView {

    private static final String TAG = "[Fragment]";
    // butterknife 绑定解绑管理器
    private Unbinder mUnbinder;
    // p 层对象，在子类 BaseFrgment 中使用 Daggler2 注入
    protected P mPresenter;
    // rxjava 管理类，在当前 Fragment 销毁时用于解除 rxjava 的订阅关系
    protected Subscription mSubscription;
    // 保存当前Fragment状态
    protected Bundle mBundle;
    // 成功、失败、加载中等的界面
    protected View loadingView;
    protected CV contentView;
    protected TextView errorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取bundle,并保存起来
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }
    }

    /**
     * 保存当前 fragment 异常关闭的一些状态
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("bundle", mBundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initComponent();

        super.onViewCreated(view, savedInstanceState);
        // 加载中、加载成功、加载失败等界面
        loadingView = view.findViewById(R.id.loadingView);
        contentView = (CV) view.findViewById(R.id.contentView);
        errorView = (TextView) view.findViewById(R.id.errorView);

        if (errorView != null)
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorViewClicked();
                }
            });
        // 接收注入返回值，用户fragment销毁时解绑使用
        mUnbinder = ButterKnife.bind(this, view);

        initView(view);

        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    /**
     * Fragment 的方法
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "======hidden:=====" + hidden);
    }

    /**
     * Fragment 中的方法，销毁当前 Fragment 的 View 时回调
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();     // butterknife 解绑

        loadingView = null;
        contentView = null;
        errorView = null;

        if (mPresenter != null) {
            // 销毁
            mPresenter.onDestroy();
            mPresenter = null;
        }
        // rxjava 解除订阅/发布
        AppUtil.cancelSubscription(mSubscription);

        Log.e(TAG, "======onDestroyView:=====");
    }


    protected void onErrorViewClicked() {
        // 加载错误后点击重新加载
        loadData(false);
    }

    protected abstract void initComponent();

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view);


}
