#import <sys/utsname.h>
#import <UIKit/UIKit.h>
#import <Flutter/Flutter.h>
#import <WebKit/WebKit.h>

@interface FlutterUserAgentPlugin : NSObject<FlutterPlugin>
@property (nonatomic) bool isEmulator;
@property (nonatomic) WKWebView* webView;
@end
