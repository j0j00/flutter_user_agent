## 0.0.1

* Initial release.

## 1.0.0

* Add flutter example to the project.

## 1.0.1

* API changes:
    * Make `FlutterUserAgent.init` cache user-agent properties by default, unless `force: true` is specified.
    * Add `FlutterUserAgent.getPropertyAsync` function for lazily fetching properties without having to call `FlutterUserAgent.init`.
    * Add `FlutterUserAgent.release` function for releasing all the user-agent properties temporarily statically stored in memory.