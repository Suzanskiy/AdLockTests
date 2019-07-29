package com.sergio.adlocktests;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class ClearAppData
{
    private static final String ADLOCK_PACKE_NAME ="com.adlock" ;




    @Before
      public void cleaning() throws IOException, InterruptedException {
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("pm clear com.adlock");


    }
    @Test
    public void DeleteOk(){
        Log.v("Test", "data successfully deleted");
    }
}
