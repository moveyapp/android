package il.co.moveyorg.movey.ui.main.feed;

import javax.inject.Inject;

import il.co.moveyorg.movey.ui.base.BasePresenter;
import il.co.moveyorg.movey.util.RxEventBus;

/**
 * Created by eladk on 11/27/17.
 */

public class FeedPresenter extends BasePresenter<FeedMvpView> {

    @Inject
    RxEventBus eventBus;

    @Inject
    FeedPresenter(){

    }


}
