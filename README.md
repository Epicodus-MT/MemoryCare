<img src="mara.jpg" align="right">

# Memory Care
Android Independent Project

#### By _**Mara Timberlake**_

## Description
_This is an Android Application for the loved ones of a community with Memory Loss._

## Specifications
|Behavior|Input|Output|
|---|---|---|
|User enters zip code and clicks 'Find Location'|98125|User sent to 'Locations Page' where the communities in that zip are listed|
|User enters zip code that is not 5 digits and clicks 'Find Location'|9812|Error message displayed 'Please enter a 5-digit US zip code'|
|User clicks 'Care Tips'|mFindAdviceButton.setOnClickListener|User sent to 'Advice Page' where app general information is located|

## Technologies Used

* [Android Studio](https://developer.android.com/studio/index.html)
* Java
* XML
* Git
* Github

## What's included
Within the repository you'll find the following directories and files:

```
MemoryCare/
├── .gradle/
├── .idea/
├── app/
|    └── build/
|    └── src/
|    |     └── androidTest/
|    |     └── main/
|    |     |     └── assets/
|    |     |     |    └── fonts/
|    |     |     └── java/
|    |     |     |    └── AdviceActivity
|    |     |     |    └── Constants
|    |     |     |    └── MainActivity
|    |     |     |    └── MyCommunityArrayActivity
|    |     |     |    └── CommunityActivity
|    |     |     └── res/
|    |     |     |    └── drawable/
|    |     |     |    └── layout/
|    |     |     |    └── values/
|    |     |     └── AndroidManifest.xml
|    |     └── test/
|    └── .gitignore
|    └── app.iml
|    └── build.gradle
|    └── proguard-rules.pro
├── build/
├── gradle/
├── .gitignore
├── build.gradle
└── README.md
```

## Setup/Installation Requirements
To run this program:
  * _LOCAL: Go to Terminal_
  * `git clone https://github.com/Epicodus-MT/MemoryCare.git`
  * Install and open [Android Studio](https://developer.android.com/studio/index.html)
  * Select '_Open an existing Android Studio project_'
  * Navigate to cloned directory and click '_OK_'
  * Create an emulator
  * _Run_ project by selecting _green arrow_ in menu toolbar

## Known Bugs
_No known bugs at this time._

## Support and Contact Details
For questions or feedback, please contact [Mara Timberlake](<contact-info.md>).

## License
[![CC0](https://licensebuttons.net/p/zero/1.0/88x31.png)](https://opensource.org/licenses/MIT)

To the extent possible under law, the author has waived all copyright and related or neighboring rights to this work.

Copyright (c) 2017 *_Mara Timberlake_*
