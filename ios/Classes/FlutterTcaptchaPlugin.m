#import "FlutterTcaptchaPlugin.h"
#import <flutter_tcaptcha/flutter_tcaptcha-Swift.h>

@implementation FlutterTcaptchaPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTcaptchaPlugin registerWithRegistrar:registrar];
}
@end
