#import "FlutterTcaptchaPlugin.h"
#import <flutter_tcaptcha/flutter_tcaptcha-Swift.h>
#import <TCWebCodesSDK/TCWebCodesBridge.h>

@interface FlutterTcaptchaPlugin()
//作为属性
typedef void(^ResultBlock)(NSDictionary * dic);
@property (nonatomic, copy) ResultBlock webCodesBlock;
@end
@implementation FlutterTcaptchaPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTcaptchaPlugin registerWithRegistrar:registrar];
}


+ (void)showTCaptchaDialog:(void(^)(NSDictionary * blockParam))callBack{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSString * value = [userDefaults valueForKey:@"KeyTCaptchaID"];
    UIView *view = [[UIApplication sharedApplication].delegate window];
    if (value.length != 0) {
        [[TCWebCodesBridge sharedBridge] loadTencentCaptcha:view appid:value callback:^(NSDictionary *resultJSON) {
            callBack(resultJSON);
        }];
    }else{
        NSLog(@"请先设置防水墙ID");
    }
}
@end
