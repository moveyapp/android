package il.co.moveyorg.movey.ui.ribot;

import java.util.List;

import il.co.moveyorg.movey.data.model.Ribot;
import il.co.moveyorg.movey.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
