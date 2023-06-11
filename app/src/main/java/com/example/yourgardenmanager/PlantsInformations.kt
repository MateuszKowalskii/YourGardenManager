package com.example.yourgardenmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

/*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.yourgardenmanager.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.*
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import retrofit2.converter.gson.GsonConverterFactory

class PlantsInfromations : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plants_infromations)

        textView = findViewById(R.id.textView)

        val token = "j-0CtM52yRmUORt0kdVD_UDo_GDxJdS3YViIrcim1aw"

        val api = PlantApiService.api
        val call = api.searchPlants(token, "sunflower") // Replace "rose" with your search query

        call.enqueue(object : Callback<PlantSearchResponse> {
            override fun onResponse(
                call: Call<PlantSearchResponse>,
                response: Response<PlantSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val plantResponse = response.body()
                    val plants = plantResponse?.data

                    if (plants != null && plants.isNotEmpty()) {
                        val searchQuery = "rose"
                        val plant = findPlantByCommonName(plants, searchQuery)
                        //val plant = plants[0]
                        if(plant!= null){
                            val plantInfo = "Common Name: ${plant.common_name}\nScientific Name: ${plant.scientific_name}"
                            textView.text = plantInfo
                        } else {
                            textView.text = "Plant not found"
                        }
                        //val plantInfo = StringBuilder()// "Common Name: ${plant.common_name}\nScientific Name: ${plant.scientific_name}"
                        //for (plant in plants) {
                        //    plantInfo.append("Common Name: ${plant.common_name}\nScientific Name: ${plant.scientific_name}\n\n")
                        //}
                        //textView.text = plantInfo
                    }
                } else {
                    Log.e("API Error", "Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PlantSearchResponse>, t: Throwable) {
                Log.e("API Error", "Request failed", t)
            }
        })

    }

    private fun findPlantByCommonName(plants: List<Plant>, commonName: String): Plant? {
        for (plant in plants) {
            if (plant.common_name.equals(commonName, ignoreCase = true)) {
                return plant
            }
        }
        return null
    }

    //var url = "https://perenual.com/api/species/details/"
    /*
    var url = "https://trefle.io/api/v1/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plants_infromations)

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder =Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<PlantInformation>?> {
            override fun onResponse(
                call: Call<List<PlantInformation>?>,
                response: Response<List<PlantInformation>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = java.lang.StringBuilder()
                for(myData in responseBody){
                    myStringBuilder.append(myData.common_name)
                    myStringBuilder.append("\n")
                }

                findViewById<TextView>(R.id.textView).setText(myStringBuilder)
            }

            override fun onFailure(call: Call<List<PlantInformation>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    */
}*/