import 'dart:async';

import 'package:flutter/services.dart';

class FlutterUserAgent {
  static const MethodChannel _channel = MethodChannel('flutter_user_agent');

  static Map<String, dynamic> _properties;

  /// Initialize the module.
  /// This should be called before the module can be used.
  static Future init() async {
    _properties = Map.unmodifiable(await _channel.invokeMethod('getProperties'));
  }

  /// Returns the device's user agent.
  static String get userAgent {
    return _properties['userAgent'];
  }

  /// Returns the device's webview user agent.
  static String get webViewUserAgent {
    return _properties['webViewUserAgent'];
  }

  /// Fetch a [property] that can be used to build your own user agent string.
  static dynamic getProperty(String property) {
    return _properties[property];
  }

  /// Return a map of properties that can be used to generate the user agent string.
  static Map<String, dynamic> get properties {
    return _properties;
  }
}
