import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_tcaptcha/flutter_tcaptcha.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  showTcaptchaDialog() async {
    try{
      TCaptchaResult result = await FlutterTcaptcha.showTCaptchaDialog();
      print(result.toJson());
    }on PlatformException catch(e){
      print(e);
    }
  }

  dismissDialog() async {
    await FlutterTcaptcha.dismissDialog();
  }

  setTCaptchaID() async {
    // 这个是个人申请的测试 appid,有次数限制,如果不可用请换上自己的 appid
    await FlutterTcaptcha.setTCaptchaID("1300606145");
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: <Widget>[
            RaisedButton(
              child: Text("展示防水墙"),
              onPressed: showTcaptchaDialog,
            ),
            RaisedButton(
              child: Text("关闭防水墙"),
              onPressed: dismissDialog,
            ),
            RaisedButton(
              child: Text("设置防水墙id"),
              onPressed: setTCaptchaID,
            )
          ],
        ),
      ),
    );
  }
}
