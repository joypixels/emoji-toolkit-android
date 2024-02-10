# ![JoyPixels Logo](https://www.joypixels.com/images/logos/logo-cyan.svg)

> [![Release](https://jitpack.io/v/joypixels/emoji-toolkit-android.svg)](https://github.com/joypixels/emoji-toolkit-android)

**Emoji Toolkit Android** to help users find and replace native system emojis with JoyPixels in their Android app.


## What's Included?

 - This project includes a Java library used to convert emoji into various formats, including conversion to JoyPixels emoji images.
 - The library included here are available free under the MIT license.  Please check the [LICENSE.md](LICENSE.md) file for more details.
 
 
## License to Use JoyPixels Images
 
### JoyPixels Version 3+
 
 JoyPixels has several licensing options available. PNG 32px, 64px, and 128px as well as 32px and 64px sprites are available for digital use, with attribution. See [joypixels.com/liceses/free](https://www.joypixels.com/licenses/free) for more information on usage and attribution requirements.
 
 *Premium Licenses are available for larger PNG assets and SVG assets, for digital and print use.* See [joypixels.com/licenses/premium](https://www.joypixels.com/licenses/premium) for more information or to obtain a Premium License.
 

## Installation

emoji-toolkit-android is available through [JitPack](https://jitpack.io). To install
it, simply add the following line to your build.gradle:

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    implementation 'com.github.joypixels:emoji-toolkit-android:8.0.6'
}
```
and in your manifest add:

```gradle
<uses-permission android:name="android.permission.INTERNET" />
```

Internet is required to download the converted emoji. 

## Contributing
Please see [CONTRIBUTING.md](CONTRIBUTING.md) for more info on contributing to the JoyPixels project. For artwork comments and questions please see the emoji-assets repo.

## Usage
You'll find links to usage demos in [USAGE.md](USAGE.md).


## Information

### Bug reports

If you discover any bugs, feel free to create an issue on GitHub. We also welcome the open-source community to contribute to the project by forking it and issuing pull requests.

 *  [https://github.com/joypixels/emoji-toolkit-android/issues](https://github.com/joypixels/emoji-toolkit-android/issues)


### Contact

If you have any questions, comments, or concerns you are welcome to contact us.

*  [![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/emojione/emojione?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
* [support@joypixels.com](mailto:support@joypixels.com)
* [https://www.joypixels.com](https://www.joypixels.com)
* [https://twitter.com/joypixels](https://twitter.com/joypixels)

### Consideration
The library's `unicodeRegexp` value was generated using [s9e\RegexpBuilder](https://github.com/s9e/RegexpBuilder).
