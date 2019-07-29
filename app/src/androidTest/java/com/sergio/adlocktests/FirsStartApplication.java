package com.sergio.adlocktests;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirsStartApplication {

    private static final String ADLOCK_PACKE_NAME = "com.adlock";
    private String licenceKey = "4RPN4DZTN";

    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() throws InterruptedException {
        // Initialize UiDevice instance

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();
        Thread.sleep(1000);

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(ADLOCK_PACKE_NAME);
        // Clear out any previous instances
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(ADLOCK_PACKE_NAME).depth(0)),
                LAUNCH_TIMEOUT);

    }


    @Test
    public void t01_PressCheckBoxAgreeWithTerms() throws Throwable {

        UiObject2 checkBox = device.findObject(By.res(ADLOCK_PACKE_NAME, "checkBoxAgreeWithTerms"));
        UiObject2 nextButton = device.findObject(By.res(ADLOCK_PACKE_NAME, "buttonOnboardingAgree"));

        if (checkBox != null&& checkBox.isEnabled())
            checkBox.click();
        else
            throw new Throwable("There is no checkBox active. Make sure app starts at first");

        if (!nextButton.isEnabled()) {
            Log.v("Test", "Next button isn't enabled");
            throw new Throwable("next button should be enabled");
        }
        nextButton.click();


    }

    @Test
    public void t02_ActivateDeviceFromTrialExpired() throws InterruptedException, UiObjectNotFoundException {

        UiObject2 enterKeyButton = device.findObject(By.res(ADLOCK_PACKE_NAME, "buttonActivation"));

        enterKeyButton.click();

        UiSelector licenseEditText = new UiSelector().className("android.widget.EditText");

        device.findObject(licenseEditText).setText(licenceKey);
        UiObject2 activateButton = device.findObject(By.res(ADLOCK_PACKE_NAME, "buttonActivationSubmit"));
        activateButton.click();
        device.waitForWindowUpdate(ADLOCK_PACKE_NAME, 500L);
    }

    @Test
    public void t03_EnableHtttsFiltering() throws InterruptedException {
        UiObject2 checkbox = device.findObject(By.res(ADLOCK_PACKE_NAME, "switchAdlockEnableHTTPSFilteringFragmentHome"));


        if (!checkbox.isChecked())
            checkbox.click();
        device.waitForWindowUpdate(ADLOCK_PACKE_NAME, 500);
        UiObject2 nextButton = device.findObject(By.res(ADLOCK_PACKE_NAME, "buttonAlertAgree"));
        nextButton.click();
        device.waitForWindowUpdate(ADLOCK_PACKE_NAME, 500);

       /* UiObject2 buttonOk = device.findObject(By.text("OK"));
        buttonOk.click();*/


    }

    @Test

    public void t04_DeactivateLicense() throws Exception {
      device.waitForWindowUpdate(ADLOCK_PACKE_NAME,LAUNCH_TIMEOUT);
        UiObject2 imageButtonTopMenu = device.findObject(By.res(ADLOCK_PACKE_NAME, "imageButtonTopMenu"));

        imageButtonTopMenu.click();

        UiSelector menuAbout = new UiSelector().className("android.widget.TextView").text("About");
        device.findObject(menuAbout).click();

        UiSelector deactivetaBtn = new UiSelector().className("android.widget.TextView").text("Deactivate");
        device.findObject(deactivetaBtn).click();
        UiSelector confirmBtn = new UiSelector().className("android.widget.Button").text("OK");
        device.findObject(confirmBtn).click();


        //


    }
}