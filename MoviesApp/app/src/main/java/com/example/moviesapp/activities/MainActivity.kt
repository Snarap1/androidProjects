package com.example.moviesapp.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.moviesapp.R
import com.example.moviesapp.data.MovieAdapter
import com.example.moviesapp.models.Movie
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter:MovieAdapter
    private lateinit var movies:ArrayList<Movie>
    private lateinit var requestQueue:RequestQueue
    private lateinit var field:EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)

        movies = arrayListOf()
        requestQueue = Volley.newRequestQueue(this)
    }

    private fun getMovies(film:String) {
        var url:String = "http://www.omdbapi.com/?apikey=62a1358d&s=$film"

        val request = JsonObjectRequest(Request.Method.GET, url,null, object:  Response.Listener<JSONObject>{
            override fun onResponse(response: JSONObject) {
                val jsonArray:JSONArray = response.getJSONArray("Search")
                for (i in 0 until jsonArray.length()){
                    val jsonObject:JSONObject = jsonArray.getJSONObject(i)

                    val title = jsonObject.getString("Title")
                    val year = jsonObject.getString("Year")
                    val posterUrl = jsonObject.getString("Poster")

                    val movie = Movie(title,posterUrl,year)
                    movies.add(movie)
                }
                movieAdapter = MovieAdapter(this@MainActivity, movies)
                recyclerView.adapter = movieAdapter
            }
        },Response.ErrorListener{error: VolleyError? -> error?.printStackTrace() })

        requestQueue.add(request)
    }

    fun findFilm(view: View) {
        field = findViewById(R.id.search)
        var filmName = field.text.toString().trim()
        getMovies(filmName)
    }
}