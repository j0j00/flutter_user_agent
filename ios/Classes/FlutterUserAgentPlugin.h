#import <sys/utsname.h>
#import <UIKit/UIKit.h>
#import <Flutter/Flutter.h>

@interface FlutterUserAgentPlugin : NSObject<FlutterPlugin>
@property (nonatomic) bool isEmulator;
@end
