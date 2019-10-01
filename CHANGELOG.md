# flutter_user_agent version history

## 1.1.0

* iOS API change for compatibility purposes.

  Change `UIWebView` (deprecated since iOS 12.0) to `WKWebView` - [#1](https://github.com/j0j00/flutter_user_agent/issues/1) (courtesy of [@Triipaxx](https://github.com/Triipaxx)!)

## 1.0.1

* API changes:
    * Make `FlutterUserAgent.init` cache user-agent properties by default, unless `force: true` is specified.
    * Add `FlutterUserAgent.getPropertyAsync` function for lazily fetching properties without having to call `FlutterUserAgent.init`.
    * Add `FlutterUserAgent.release` function for releasing all the user-agent properties temporarily statically stored in memory.
## 1.0.0

* Add flutter example to the project.

## 0.0.1

* Initial release.