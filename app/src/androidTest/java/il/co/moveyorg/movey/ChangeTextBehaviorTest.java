package il.co.moveyorg.movey;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ChangeTextBehaviorTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.example.android.testing.uiautomator.BasicSample";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() throws UiObjectNotFoundException {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);

        UiObject menu_media = mDevice.findObject(new UiSelector()
                .resourceId("menu_media"));
        UiObject menu_notifications = mDevice.findObject(new UiSelector()
                .resourceId("menu_notifications"));
        UiObject menu_profile = mDevice.findObject(new UiSelector()
                .resourceId("menu_profile"));
        UiObject menu_feed = mDevice.findObject(new UiSelector()
                .resourceId("menu_feed"));

        // Simulate a user-click on the media button, if found.
        if(menu_media.exists() && menu_media.isEnabled()) {
            menu_media.click();
        }

        // Simulate a user-click on the notifications button, if found.
        if(menu_notifications.exists() && menu_notifications.isEnabled()) {
            menu_notifications.click();
        }

        // Simulate a user-click on the profile button, if found.
        if(menu_profile.exists() && menu_profile.isEnabled()) {
            menu_profile.click();
        }

        // Simulate a user-click on the click button, if found.
        if(menu_feed.exists() && menu_feed.isEnabled()) {
            menu_feed.click();
        }
    }
}