package fussen.yu.news.modules.course.presenter;

import java.util.Map;

import example.fussen.baselibrary.base.presenter.PresenterLife;


/**
 * Created by Fussen on 2016/12/28.
 */

public interface CoursePresenter extends PresenterLife {
    void getAllCourseType(Map<String, String> params,boolean pullToRefresh);
}
