# Emoji Toolkit Android - Usage

## Client Properties

The following properties are available in the Swift/iOS version of the JoyPixels library:

 - `emojiVersion` (str) - Used only to direct CDN path. This is a 2-digit version (e.g. '8.0'). Not recommended for usage below 3.0.0.
 - `emojiDownloadSize` (enum) **Default: `128`** - Used on to direct CDN path for non-sprite PNG usage to determine the size of the emoji image downloaded. Available options are '32', '64', and '128'.
 - `imagePathPNG` (str) Defaults to CDN (jsdelivr) path. 
 - `greedyMatch` (bool) **Default: `false`** - When `true`, matches non-fully-qualified Unicode values.
 - `ascii` (bool) **Default `true`** - When `true`, matches ASCII characters (in `unicodeToImage` and `shortnameToImage` functions).
 - `riskyMatchAscii` (bool) **Default `true`** - When `true`, matches ASCII characters not encapsulated by spaces. Can cause issues when matching (e.g. `C://filepath` or `<button>.</button>` both contain ASCII chars).


## Usage Examples

Below there are some examples of how you will actually use the libraries to convert Unicode emoji characters to :shortnames: and :shortnames: to emoji images.


### Client Functions


**`client.toShortname(string) -> String`** - _native unicode -> shortnames_

Take native unicode emoji input and translate it to their corresponding shortnames. (we recommend this for database storage)

**`client.shortnameToImage(string, imageSize, callback) -> SpannableStringBuilder`** - _shortname -> images_

Take input containing shortnames and translate it directly to JoyPixels images for display in a spannable string. An imageSize parameter is used to set the size the size of the emoji images within the spannable string. A callback is made when all emoji have been retrieved from the `imagePathPNG` CDN.

**`client.shortnameToUnicode(string) -> String`** - _shortname -> native unicode emoji_

Take input containing shortnames and translate it directly to Unicode Emoji (when displaying the unified input to clients).

**`client.unicodeToImage(string, imageSize, callback) -> SpannableStringBuilder`** - _native unicode -> images_

Take native unicode emoji input and translate it directly to JoyPixels images for display in a spannable string. An imageSize parameter is used to set the size of the emoji images within the spannable string. A callback is made when all emoji have been retrieved from the `imagePathPNG` CDN.

**`client.toImage(string, imageSize, callback) -> SpannableStringBuilder`** - _native unicode + shortnames -> images_

Take a string containing both native unicode emoji and shortnames, and translate it into JoyPixels images for display in a spannable string. An imageSize parameter is used to set the size of the emoji images within the spannable string. A callback is made when all emoji have been retrieved from the `imagePathPNG` CDN.

