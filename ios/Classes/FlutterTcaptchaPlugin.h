#import <Flutter/Flutter.h>

@interface FlutterTcaptchaPlugin : NSObject<FlutterPlugin>


/** 打开防水墙  并返回结果 */
+ (void)showTCaptchaDialog:(void(^)(NSDictionary * blockParam))callBack;
@end
