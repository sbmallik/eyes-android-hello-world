package com.applitools.helloworld.android;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import com.applitools.eyes.android.espresso.Eyes;

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

  @Test
  public void simpleTest() {

    // Initialize the eyes SDK and set your private API key.
    Eyes eyes = new Eyes();
    eyes.setApiKey(BuildConfig.APPLITOOLS_API_KEY);
    eyes.setServerUrl(URI.create(BuildConfig.APPLITOOLS_SERVER_URL));

    try {
      // Start the test
      eyes.open("Hello World!", "My first Espresso Android test!");

      // Visual checkpoint #1.
      eyes.checkWindow("Hello!");

      onView(withId(R.id.click_me_btn)).perform(click());

      // Visual checkpoint #2.
      eyes.checkWindow("Click!");

      // End the test.
      eyes.close();
    } finally {
      // If the test was aborted before eyes.close was called, ends the test as aborted.
      eyes.abortIfNotClosed();
    }
  }
}
