package com.example.yourgardenmanager.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yourgardenmanager.MainActivity
import com.example.yourgardenmanager.R
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PlantsInformations : AppCompatActivity() {

    private val baseUrl = "https://trefle.io/api/v1/plants/search"
    private val token = "j-0CtM52yRmUORt0kdVD_UDo_GDxJdS3YViIrcim1aw"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plants_informations)

        val checkDataButton = findViewById<Button>(R.id.checkDataButton)
        checkDataButton.setOnClickListener {
            val plantName: String = findViewById<TextView>(R.id.plantNameTextView).text.toString()
            searchPlant(plantName)
        }

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        goBackButton.setOnClickListener{
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }
    }

    private fun searchPlant(query: String){
        val client = OkHttpClient()
        val url = "$baseUrl?token=$token&q=$query"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                try {
                    val jsonObject = JSONObject(responseData)
                    val plantsArray = jsonObject.getJSONArray("data")


                    val scientificName = plantsArray.getJSONObject(0).getString("scientific_name")
                    val commonName = plantsArray.getJSONObject(0).getString("common_name")
                    val scientificFamilyName = plantsArray.getJSONObject(0).getString("family")
                    var family_common_name = plantsArray.getJSONObject(0).getString("family_common_name")

                    if(family_common_name == "null")family_common_name = "No Common Name"

                    runOnUiThread {
                        val plantInfo = "Scientific Name: $scientificName\nCommon Name: $commonName\n" +
                                "Scientific Family Name: $scientificFamilyName\nFamily Common Name: $family_common_name"
                        val textView = findViewById<TextView>(R.id.textView)
                        textView.text = plantInfo
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    val textView = findViewById<TextView>(R.id.textView)
                    textView.text = "Did not find such a plant"
                }finally {
                    response.close()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }
}
