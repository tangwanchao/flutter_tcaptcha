import Flutter
import UIKit
public class SwiftFlutterTcaptchaPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_tcaptcha", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterTcaptchaPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }
    
  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {

    case "showTCaptchaDialog":
        //打开防水墙
        print("打开防水墙")
        FlutterTcaptchaPlugin.showTCaptchaDialog { (resultBlock:[AnyHashable : Any]?) in
            result(resultBlock)
        }
        break

    case "dismissDialog":
        //关闭防水墙
        print("iOS没有关闭防水墙的公开方法")
        
        break

    case "setTCaptchaID":
        //设置防水墙id
        if call.arguments is String {
            setTCaptchaID(call.arguments as! String)
            print("防水墙ID设置成功")
        }else{
            print("防水墙ID类型错误")
        }
        break

    default: break

    }
  }
    func setTCaptchaID(_ idString:String) {
        let userDefaults = UserDefaults.standard
        userDefaults.setValue(idString, forKey: "KeyTCaptchaID")
        userDefaults.synchronize()
    }
}
