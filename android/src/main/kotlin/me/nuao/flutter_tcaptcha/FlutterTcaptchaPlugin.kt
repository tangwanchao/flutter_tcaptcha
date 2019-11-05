package me.nuao.flutter_tcaptcha

import com.tencent.captchasdk.TCaptchaDialog
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import org.json.JSONObject

class FlutterTcaptchaPlugin(private val registrar: Registrar) : MethodCallHandler {

    companion object {

        const val ERROR_CODE_PARAMETER_ERROR = "ERROR_CODE_PARAMETER_ERROR"
        const val ERROR_CODE_NO_TCAPTCHA_ID = "ERROR_CODE_NO_TCAPTCHA_ID"
        const val ERROR_CODE_HAS_DIALOG = "ERROR_CODE_HAS_DIALOG"

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "flutter_tcaptcha")
            channel.setMethodCallHandler(FlutterTcaptchaPlugin(registrar))
        }
    }

    private var mTCaptchaID: String? = null
    private var mDialog:TCaptchaDialog? = null

    init {
        registrar.addViewDestroyListener {
            resetDialog()
            return@addViewDestroyListener false
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when {
            call.method == "setTCaptchaID" -> setTCaptchaID(call, result)
            call.method == "showTCaptchaDialog" -> showTCaptchaDialog(call, result)
            call.method == "dismissDialog" -> dismissDialog(result)
            else -> result.notImplemented()
        }
    }

    /**
     * 设置防水墙 ID
     */
    private fun setTCaptchaID(call: MethodCall, result: Result) {
        try {
            val id: String = call.arguments()
            this.mTCaptchaID = id
            result.success(null)
        } catch (th: Throwable) {
            result.error(ERROR_CODE_PARAMETER_ERROR, "TCaptchaID is String", th.message)
        }
    }

    /**
     * @return true TCaptchaID 存在
     */
    private fun checkTCaptchaID(result: Result): Boolean {
        if (mTCaptchaID.isNullOrEmpty()) {
            result.error(ERROR_CODE_NO_TCAPTCHA_ID,"请先设置 TCaptchaID",null)
            return false
        }
        return true
    }

    /**
     * 展示防水墙对话框,并给予回调
     */
    private fun showTCaptchaDialog(call: MethodCall, result: Result) {
        if (!checkTCaptchaID(result)) {
            return
        }

        if (mDialog != null) {
            result.error(ERROR_CODE_HAS_DIALOG,"已存在防水墙",null)
            return
        }

        val id = mTCaptchaID!!
        var jsonString:String? = null
        try {
            jsonString = call.arguments()
        }catch (ex:Throwable){
            // do nothing
        }
        mDialog = TCaptchaHelper.createDialog(registrar.activeContext(),id,object :TCaptchaHelper.Callback{
            override fun onSuccess(jsonObject: JSONObject) {
                result.success(jsonObject.toString())
                resetDialog()
            }

            override fun onLoadErr(jsonObject: JSONObject) {
                result.success(jsonObject.toString())
                resetDialog()
            }

            override fun onFailure(jsonObject: JSONObject) {
                result.success(jsonObject.toString())
                resetDialog()
            }
        },jsonString)
        mDialog?.show()
    }

    /**
     * 关闭防水墙
     */
    private fun dismissDialog(result: Result){
        resetDialog()
        result.success(null)
    }

    /**
     * 重置防水墙到初始状态
     */
    private fun resetDialog(){
        mDialog?.dismiss()
        mDialog = null
    }
}
