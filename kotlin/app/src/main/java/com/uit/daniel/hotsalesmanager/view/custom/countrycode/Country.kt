package com.uit.daniel.hotsalesmanager.view.custom.countrycode

import android.content.Context
import com.uit.daniel.hotsalesmanager.utils.Constant
import org.json.JSONArray

data class Country(
    var flag: Int,
    var code: String,
    var name: String
)

fun listCodeCountry(context: Context): ArrayList<Country> {
    val countries = ArrayList<Country>()
    val mflag = TakeFlagCountry()
    val array = createArrayFromJson(context)

    for (i in 0 until array.length()) {
        val objJSON = array.getJSONObject(i)

        if (objJSON.getString("dial_code") == Constant.CODE_VIETNAM) {
        }
        countries.add(
            Country(
                mflag.getFlagMasterResID(objJSON.getString("code")),
                "(" + objJSON.getString("dial_code") + ")",
                objJSON.getString("name")
            )
        )
    }
    return countries
}

private fun createArrayFromJson(context: Context): JSONArray {
    val inputStream = context.assets.open("CountryCodes.json")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    val json = String(buffer, Charsets.UTF_8)
    return JSONArray(json)
}
