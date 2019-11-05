package me.nuao.flutter_tcaptcha

import android.content.Context
import com.tencent.captchasdk.TCaptchaDialog
import com.tencent.captchasdk.TCaptchaVerifyListener
import org.json.JSONObject


/**
 * @author 唐万超
 * @date 2019/08/28
 */
object TCaptchaHelper {

    /**
     * 创建防水墙对话框
     * @param context 推荐 activity
     * @param appId 腾讯防水墙 appID
     * @param listener 回调
     * @param jsonString 额外参数
     */
    fun createDialog(context: Context, appId:String,listener: Callback, jsonString: String? = null): TCaptchaDialog {
        val dialog = TCaptchaDialog(context, appId, TCaptchaVerifyListener { jsonObject ->
            when (jsonObject.getInt("ret")) {
                0 -> listener.onSuccess(jsonObject)
                -1001 -> listener.onLoadErr(jsonObject)
                else -> listener.onFailure(jsonObject)
            }
        }, jsonString)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    interface Callback {
        /**
         * 验证成功回调
         *
         * jsonObject.getString("ticket")为验证码票据
         * jsonObject.getString("appid")为appid
         * jsonObject.getString("randstr")为随机串
         */
        fun onSuccess(jsonObject: JSONObject)

        /**
         * 验证码首个TCaptcha.js加载错误，业务可以根据需要重试
         *jsonObject.getString("info")为错误信息
         */
        fun onLoadErr(jsonObject: JSONObject)

        /**
         * 验证失败回调，一般为用户关闭验证码弹框
         */
        fun onFailure(jsonObject: JSONObject)
    }
}