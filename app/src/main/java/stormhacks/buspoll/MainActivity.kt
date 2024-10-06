package stormhacks.buspoll

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    private var stopList: ArrayList<Stop> = ArrayList<Stop>()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val mapView = findViewById<MapView>(R.id.poopy)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val minput = InputStreamReader(assets.open("stops.csv"))
        val reader = BufferedReader(minput)

        var line : String?
        var displayData : String = ""
        while (reader.readLine().also { line = it } != null){
            val row : List<String> = line!!.split(",")
            var newStop = Stop(row[1], row[2], row[4], row[5])
            stopList.add(newStop)
            displayData = displayData + row[4] + "\t" + row[5] + "\n"
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()
//        var txtData = findViewById(R.id.textView) as TextView
//        txtData.text = stopList.toString()
    }

    private fun fetchLocation() {
        val currentLocation = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

    }
}
