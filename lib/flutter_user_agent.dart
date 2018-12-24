import 'dart:async';

import 'package:flutter/services.dart';

class FlutterUserAgent {
  static const MethodChannel _channel =
      const MethodChannel('flutter_user_agent');

  static Map<String, dynamic> _properties;

  static Future init() async {
    _properties = Map.unmodifiable(await _channel.invokeMethod('getProperties'));
  }

  static String get userAgent {
    return _properties['userAgent'];
  }

  static String get webViewUserAgent {
    return _properties['webViewUserAgent'];
  }

  static getProperty(String property) {
    return _properties[property];
  }

  static get properties {
    return _properties;
  }
}
