package com.applitools.helloworld.android;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import com.applitools.eyes.android.common.BatchInfo;
import com.applitools.eyes.android.common.EyesRunner;
import com.applitools.eyes.android.common.Feature;
import com.applitools.eyes.android.common.config.Configuration;
import com.applitools.eyes.android.components.androidx.AndroidXComponentsProvider;
import com.applitools.eyes.android.espresso.ClassicRunner;
import com.applitools.eyes.android.espresso.Eyes;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.net.URI;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {

  @Rule
  public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

  private static final EyesRunner runner = new ClassicRunner();
  private static final Configuration suiteConfig = new Configuration();
  private Eyes eyes;

  @BeforeClass
  public static void beforeTestSuite() {
    String apiKey = BuildConfig.APPLITOOLS_API_KEY;
    String appName = "EKB Example : hello world";
    String batchName = "EKB Example : classic";
    String serverUrl = BuildConfig.APPLITOOLS_SERVER_URL;
    suiteConfig.setHideCaret(true)
        .setAppName(appName)
        .setApiKey(apiKey)
        .setServerUrl(serverUrl)
        //Add the following line to force use of Android PixelCopy to obtain screenshots
        //This can improve the quality of the screenshot, for example to ensure rendering of the shadow layer.
        .setFeatures(Feature.PIXEL_COPY_SCREENSHOT)
        .setBatch(new BatchInfo(batchName));
  }

  @Before
  public void beforeEachTest() {
    eyes = new Eyes(runner);
        /*
           Uncomment the call to 'eyes.setComponentsProvider' if you use AndroidX components such as
           NestedScrollView, RecyclerView and ViewPager2
        */
    eyes.setComponentsProvider(new AndroidXComponentsProvider());
    eyes.setConfiguration(suiteConfig);
  }

  @Test
  public void simpleTest() {
    // Start the test
    eyes.open("Hello World!", "My first Espresso Android test!");
    // Visual checkpoint #1.
    eyes.checkWindow("Hello!");
    onView(withId(R.id.click_me_btn)).perform(click());
    // Visual checkpoint #2.
    eyes.checkWindow("Click!");
  }

  @After
  public void afterEachTest() {
    try {
      eyes.close();
    } finally {
      eyes.abortIfNotClosed();
    }
  }
}
