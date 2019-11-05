import 'dart:async';
import 'dart:convert';

import 'package:flutter/services.dart';

class FlutterTcaptcha {
  static const MethodChannel _channel = const MethodChannel('flutter_tcaptcha');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 设置腾讯防水墙 appid
  static Future<void> setTCaptchaID(String id) async {
    await _channel.invokeMethod('setTCaptchaID', id);
  }

  /// 展示腾讯防水墙
  /// [json] 是拓展参数,可以为null,详情参考官方文档
  static Future<TCaptchaResult> showTCaptchaDialog({String json}) async {
    final String jsonStr = await _channel.invokeMethod("showTCaptchaDialog", json);
    return TCaptchaResult.fromJson(jsonDecode(jsonStr));
  }

  /// 关闭防水墙
  static Future<void> dismissDialog() async {
    await _channel.invokeMethod('dismissDialog');
  }
}

class TCaptchaResult {
  TCaptchaResult(this.ret, {this.ticket, this.appid, this.randstr, this.info});

  TCaptchaResult.fromJson(Map<String, dynamic> json)
      : this.ret = json["resultCode"],
        this.ticket = json["ticket"],
        this.appid = json["appid"],
        this.randstr = json["randstr"],
        this.info = json["info"];

  /// 防水墙结果码
  /// 0     -> 成功
  /// -1001 -> 加载失败
  /// 其他  -> 其他情况,一般为用户关闭
  final int ret;
  final String ticket;
  final String appid;
  final String randstr;
  final String info;

  /// 防水墙验证通过
  /// ticket,appid,randstr 不为 null
  bool isSuccess() {
    assert(ticket != null);
    assert(appid != null);
    assert(randstr != null);
    return ret == 0;
  }

  /// 防水墙加载失败
  /// info 错误信息不为 null
  bool isLoadErr() {
    assert(info != null);
    return ret == -1001;
  }

  /// 其他情况,一般为用户关闭防水墙
  bool isFailure() {
    return ret != 0 && ret != -1001;
  }

  Map<String, dynamic> toJson() => {
        "ret": ret,
        "ticket": ticket,
        "appid": appid,
        "randstr": randstr,
        "info": info,
      };
}
