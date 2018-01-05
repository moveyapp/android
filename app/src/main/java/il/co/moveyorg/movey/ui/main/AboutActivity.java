package il.co.moveyorg.movey.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.base.BaseActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends BaseActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.app_desc))
                .setImage(R.drawable.logo__blue)
                .addItem(new Element().setTitle("Version 0.0.1"))
                .addEmail("shlomi.al90@gmail.com")
                .addWebsite("http://softpillow.me/")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format("Open source licences", Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(v -> Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show());
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}