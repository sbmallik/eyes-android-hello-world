package com.applitools.helloworld.android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;
import android.view.View;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.applitools.eyes.android.common.AndroidDeviceInfo;
import com.applitools.eyes.android.common.AndroidDeviceName;
import com.applitools.eyes.android.common.BatchInfo;
import com.applitools.eyes.android.common.Feature;
import com.applitools.eyes.android.common.Region;
import com.applitools.eyes.android.common.ScreenOrientation;
import com.applitools.eyes.android.common.TestResultContainer;
import com.applitools.eyes.android.common.TestResults;
import com.applitools.eyes.android.common.TestResultsSummary;
import com.applitools.eyes.android.common.config.Configuration;
import com.applitools.eyes.android.components.androidx.AndroidXComponentsProvider;
import com.applitools.eyes.android.espresso.Eyes;
import com.applitools.eyes.android.espresso.fluent.Target;
import com.applitools.eyes.android.espresso.visualgrid.RunnerOptions;
import com.applitools.eyes.android.espresso.visualgrid.VisualGridRunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DocumentationExampleNMGTest {

  private static final String TAG = "DocumentationExampleNMGTest";

  @Rule
  public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule(MainActivity.class, false, false);

  @Rule
  public ActivityTestRule<DialogActivity> mDialogActivityRule = new ActivityTestRule(DialogActivity.class, false, false);

  @Rule
  public ActivityTestRule<GoogleMapsActivity> mGoogleMapActivityRule = new ActivityTestRule(GoogleMapsActivity.class, false, false);

  private static final VisualGridRunner runner = new VisualGridRunner(new RunnerOptions().apiKey(BuildConfig.APPLITOOLS_API_KEY).serverUrl(BuildConfig.APPLITOOLS_SERVER_URL).testConcurrency(3));
  private static final Configuration suiteConfig = new Configuration();
  private Eyes eyes;

  @BeforeClass
  public static void beforeTestSuite() {
    BatchInfo batchName = new BatchInfo("EKB Example: visualGrid");
    batchName.setSequenceName("EspressoNMG");
    batchName.addProperty("Demo", "EspressoDemo");
    batchName.setNotifyOnCompletion(true);
    suiteConfig.setHideCaret(true)
        .setAppName("EKB Example: visualGrid app")
        //Add the following line to force use of Android PixelCopy to obtain screenshots
        //This can improve the quality of the screenshot, for example to ensure rendering of the shadow layer.
        .setFeatures(Feature.PIXEL_COPY_SCREENSHOT)
        .setBatch(batchName);
    suiteConfig.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Galaxy_S21));
    suiteConfig.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Galaxy_S21_ULTRA));
    suiteConfig.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Galaxy_Note_10, ScreenOrientation.Landscape));
    suiteConfig.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_4_XL));
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
  public void testStartScreen() {
    mMainActivityRule.launchActivity(null);
    eyes.open("Hello World test");
    eyes.check("Click me button",Target.region(ViewMatchers.withId(R.id.click_me_btn)));
    View helloLabel = mMainActivityRule.getActivity().findViewById(R.id.hello_text_view);
    eyes.check("HelloWorld label", Target.region(helloLabel));
    Region region = new Region(200, 300, 0, 0);
    eyes.check("Region",Target.region(region));
    eyes.check("Before button click", Target.window());
    onView(withId(R.id.click_me_btn)).perform(click());
    eyes.check("After button click", Target.window());
  }

  @Test
  public void testDialog() {
    mDialogActivityRule.launchActivity(null);
    eyes.open("Dialog test");
    //TBD - can we add examples with a popup or dialog and then show the 3 possibilities of
    eyes.check("main viewport only",Target.window());
    eyes.check("dialog only",Target.window().dialog());
    eyes.check("Both main viewport and dialog",Target.window().includeAllLayers());
  }

  @Test
  public void testGoogleMap() {
    mGoogleMapActivityRule.launchActivity(null);
    eyes.open("GoogleMaps test");
    eyes.check("A googleMap", Target.googleMap().id(R.id.map));
    // eyes.check("Not a SupportMapFragment", Target.googleMap().id(R.id.map).isNotSupportGoogleMap());
  }

  @After
  public void afterEachTest() {
    try {
      eyes.closeAsync();
    } finally {
      eyes.abortIfNotClosed();
    }
  }

  @AfterClass
  public static void afterTestSuite() {
    TestResultsSummary allTestResults = runner.getAllTestResults(false);
    for (TestResultContainer result : allTestResults) {
      handleTestResults(result);
    }
  }

  private static void handleTestResults(TestResultContainer summary) {
    Throwable ex = summary.getException();
    if (ex != null) {
      Log.e(TAG, "System error occurred while checking target.");
    }
    TestResults result = summary.getTestResults();
    if (result == null) {
      Log.e(TAG, "No test results information available.");
    } else {
      Log.d(TAG, String.format("URL = %s, AppName = %s, testName = %s, matched = %d, mismatched = %d, missing = %d, aborted = %s",
          result.getUrl(),
          result.getAppName(),
          result.getName(),
          result.getMatches(),
          result.getMismatches(),
          result.getMissing(),
          (result.isAborted() ? "aborted" : "no")));
    }
  }
}
