package io.jojodev.flutter.flutteruseragent;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * FlutterUserAgentPlugin
 */
public class FlutterUserAgentPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;
    private Context applicationContext;

    private Map<String, Object> constants;

    // --- FlutterPlugin

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
      channel = new MethodChannel(binding.getBinaryMessenger(), "flutter_user_agent");
      channel.setMethodCallHandler(this);
      applicationContext = binding.getApplicationContext();
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
      channel.setMethodCallHandler(null);
      channel = null;
      applicationContext = null;
    }

    // --- MethodCallHandler

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if ("getProperties".equals(call.method)) {
            result.success(getProperties());
        } else {
            result.notImplemented();
        }
    }

    private Map<String, Object> getProperties() {
        if (constants != null) {
            return constants;
        }
        constants = new HashMap<>();

        PackageManager packageManager = applicationContext.getPackageManager();
        String packageName = applicationContext.getPackageName();
        String shortPackageName = packageName.substring(packageName.lastIndexOf(".") + 1);
        String applicationName = "";
        String applicationVersion = "";
        int buildNumber = 0;
        String userAgent = getUserAgent();
        String packageUserAgent = userAgent;

        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            applicationName = applicationContext.getApplicationInfo().loadLabel(applicationContext.getPackageManager()).toString();
            applicationVersion = info.versionName;
            buildNumber = info.versionCode;
            packageUserAgent = shortPackageName + '/' + applicationVersion + '.' + buildNumber + ' ' + userAgent;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        constants.put("systemName", "Android");
        constants.put("systemVersion", Build.VERSION.RELEASE);
        constants.put("packageName", packageName);
        constants.put("shortPackageName", shortPackageName);
        constants.put("applicationName", applicationName);
        constants.put("applicationVersion", applicationVersion);
        constants.put("applicationBuildNumber", buildNumber);
        constants.put("packageUserAgent", packageUserAgent);
        constants.put("userAgent", userAgent);
        constants.put("webViewUserAgent", getWebViewUserAgent());

        return constants;
    }

    private String getUserAgent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return System.getProperty("http.agent");
        }

        return "";
    }

    private String getWebViewUserAgent() {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            return WebSettings.getDefaultUserAgent(applicationContext);
        }

        WebView webView = new WebView(applicationContext);
        String userAgentString = webView.getSettings().getUserAgentString();

        destroyWebView(webView);

        return userAgentString;
    }

    private void destroyWebView(WebView webView) {
        webView.loadUrl("about:blank");
        webView.stopLoading();

        webView.clearHistory();
        webView.removeAllViews();
        webView.destroyDrawingCache();

        // NOTE: This can occasionally cause a segfault below API 17 (4.2)
        webView.destroy();
    }
}
