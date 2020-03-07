package dev.alexfranco.mtw.dogapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val razas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Network.hayRed(this)) {
            obtenerRazas("https://dog.ceo/api/breeds/list/all")
        } else {
            Log.d("RED", "NO HAY RED")
            Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
        }
    }

    private fun obtenerRazas(url: String) {
        val queue = Volley.newRequestQueue(this)
        val solicitud =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                try {
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonArray: JSONObject =
                        JSONObject(jsonObj.getJSONObject("message").toString())

                    razas.add("")
                    jsonArray.keys().forEach { key ->
                        razas.add(key)
                    }

                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item, razas
                    )
                    spinRazas.adapter = adapter
                    spinRazas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            obtenerRaza(razas[position])
                        }
                    }
                } catch (e: Exception) { }
            }, Response.ErrorListener { })
        queue.add(solicitud)
    }

    private fun obtenerRaza(raza: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://dog.ceo/api/breed/$raza/images"
        val solicitud =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                try {
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonArray: JSONArray = jsonObj.getJSONArray("message")

                    for (i in 0 until jsonArray.length()) {
                        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                        val adapter = DogAdapter(jsonArray)
                        recycler.adapter = adapter
                    }
                } catch (e: Exception) { }
            }, Response.ErrorListener { })
        queue.add(solicitud)

    }
}
