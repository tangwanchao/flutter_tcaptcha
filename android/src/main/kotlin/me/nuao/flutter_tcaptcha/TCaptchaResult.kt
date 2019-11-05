package me.nuao.flutter_tcaptcha

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

/**
 * @author 唐万超
 * @date 2019/11/04
 */

private val gson = Gson()

data class TCaptchaResult(
        @SerializedName("resultCode") val resultCode: Int,
        @SerializedName("ticket") val ticket: String? = null,
        @SerializedName("appid") val appid: String? = null,
        @SerializedName("randstr") val randstr: String? = null,
        @SerializedName("info") val info: String? = null
) {
    companion object {
        fun fromJsonObject(jsonObject: JSONObject): TCaptchaResult {
            val ret: Int = if (jsonObject.has("ret")) {
                jsonObject.getInt("ret")
            } else {
                -1
            }
            val ticket: String? = if (jsonObject.has("ticket")) {
                jsonObject.getString("ticket")
            } else {
                null
            }
            val appid: String? = if (jsonObject.has("appid")) {
                jsonObject.getString("appid")
            } else {
                null
            }
            val randstr: String? = if (jsonObject.has("randstr")) {
                jsonObject.getString("randstr")
            } else {
                null
            }
            val info: String? = if (jsonObject.has("info")) {
                jsonObject.getString("info")
            } else {
                null
            }
            return TCaptchaResult(ret, ticket, appid, randstr, info)
        }
    }

    /**
     * @return TCaptchaResult json str
     */
    fun toJsonStr(): String {
        return gson.toJson(this)
    }
}