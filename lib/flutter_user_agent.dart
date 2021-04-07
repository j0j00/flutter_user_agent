import 'dart:async';

import 'package:flutter/services.dart';

class FlutterUserAgent {
  static const MethodChannel _channel = MethodChannel('flutter_user_agent');

  static Map<String, dynamic>? _properties;

  /// Initialize the module.
  ///
  /// This is usually called before the module can be used.
  ///
  /// Set [force] to true if you want to refetch the user agent properties from
  /// the native platform.
  static Future init({force: false}) async {
    if (_properties == null || force) {
      Map<dynamic, dynamic> properties= (await _channel.invokeMethod('getProperties'));
      print(Map.unmodifiable(properties));
      _properties =
          Map.unmodifiable(Map<String, dynamic>.from(properties)) ;
    }
  }

  /// Release all the user agent properties statically cached.
  /// You can call this function when you no longer need to access the properties.
  static void release() {
    _properties = null;
  }

  /// Returns the device's user agent.
  static String? get userAgent {
    return _properties!['userAgent'].toString();
  }

  /// Returns the device's webview user agent.
  static String? get webViewUserAgent {
    return _properties!['webViewUserAgent'].toString();
  }

  /// Fetch a [property] that can be used to build your own user agent string.
  static String getProperty(String property) {
    return _properties![property].toString();
  }

  /// Fetch a [property] asynchronously that can be used to build your own user agent string.
  static Future<String> getPropertyAsync(String property) async {
    await init();
    return _properties![property].toString();
  }

  /// Return a map of properties that can be used to generate the user agent string.
  static Map<String, dynamic>? get properties {
    return _properties;
  }
}
