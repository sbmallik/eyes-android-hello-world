
# eyes-android-hello-world

This repo contains the code to run visual tests for android application using espresso / Applitools

## Pre-requisite

### Mac OS High Sierra or Mojave
The project is intended to execute in Mac OS Mojave / High Sierra. Here is a [link](https://support.apple.com/en-us/HT201541#:~:text=From%20the%20Apple%20menu%20%EF%A3%BF,Then%20click%20Software%20Update.) for Mac OS upgrade in general. An Apple developer's account may be necessary.
### Java Runtime Environment (JRE)
The Java runtime environment version 8 or higher is required. Here is the associated [link](https://www.oracle.com/java/technologies/downloads/) (a valid oracle account may be necessary). For Mac OS installation, select either x64 DMG or compressed archive option. Once installed, the environment variable JAVA_HOME must be set in the user's ~/.zshrc file. Here is the sample command when Java is installed using DMG file:
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.0.1.jdk/Contents/Home/
```
### Gradle
Gradle is a build tool, which is required for Android automation. It require Java JDK version 8 or higher and support building and packaging code. Here is the [link](https://gradle.org/install) for Gradle. Homebrew is a convenient option to install.
### Android Studio
The android studio provides an integrated development environment for apps. This can be downloaded from this [link](https://developer.android.com/studio). Please select Mac with intel chip option prior to download the DMG file.

Once the installation completes, please ensure at least one version of Android SDK has been downloaded. Typically the SDK should be a few versions behind the latest one. Next, use the AVD manager to create a virtual device that will be used for testing.

The following environment setup is required:
```bash
export ANDROID_HOME=~/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/emulator:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```
### Github Account
The github account is required to maintain repository independently. For Mac OS, the github client must be installed. Here is the [link](https://desktop.github.com/) to install Github. It is also necessary to connect an SSH key to the account. Using the SSH protocol, it is possible to connect and authenticate to remote servers and services. Moreover this setting eliminates the requirement of either username or token to connect to github for each visit.
### Applitools Account
The Applitools account is required for visual testing purposes. Here is the [link](https://auth.applitools.com/users/register) to register for a free account.

## Setup visual tests

The **main** branch of the repository demonstrates the visual tests for android application.

### Clone repository
Clone the main branch of the repository with the following command:
```bash
git clone git@github.com:sbmallik/eyes-android-hello-world.git
```
The repository contains the main application in the `./app/src/main` folder, while the **instrumented test** code is located in `./app/src/androidTest` folder.
### Launch Android Studio
Open a project from the root directory of the above repository. Ensure the gradle builds are successful. For all changes in the code please ensure that the `gradle sync` was executed followed by `Make Project`.

Next, select `app` from the dropdown menu inside the toolbar followed by the target device in the next dropdown. It is presumed that SDK and Device has been downloaded and crated. Next the application can be installed and run in the virtual device by clicking the `run` button. Please note that the application can be stopped by clicking the `stop` button in the toolbar.
### Execute tests inside Android Studio
In order to run any test, first navigate to the test files. These are located in `./app/src/androidTest/java/com.applitools.helloworld.android` folder.
Next, set the following environment variables related to the Applitools account:
```bash
export APPLITOOLS_API_KEY=<API key obtained from applitools account>
export APPLITOOLS_SERVER_URL=<URL to fetch the visual test results, also associated with the applitools account>
```

### Run legacy visual test
Launch the file `DocumentationExampleTest` in the editor. The file may contain multiple tests. So the options are to either run all tests at the **class** level or to run a test individually by clicking the run button next to the **annotated test method** inside the test file.
This action will launch the test app inside the emulator and subsequently launch the target app to perform the test.
The console logs may be monitored to ascertain the test results.

### Run native mobile grid test
The native mobile grid (NMG) test process is similar but the test file should be `DocumentationExampleNMGTest`. The visual grid device settings should appear in the Applitools configuration object. 

### Validate visual test results
The **Applitools Test Manager** URL is same as the one set in `APPLITOOLS_SERVER_URL`. The console logs will indicate the test result status and a test result URL is provided in case of a test failure. In either case the test results are available from the test manager.
Stop the application that was started in the above step. Navigate to the test file `TAUUITests/TAUUITests.swift` file and run the test `testTAUApplitoolEyes`. Once the test pass, check the results in Applitools test manager.

### Executing the tests from the command line
The following command executes all tests (unfiltered) crated in the repository:
```bash
./gradlew connectedAndroidTest
```