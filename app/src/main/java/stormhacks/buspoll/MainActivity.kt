package stormhacks.buspoll

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    private var stopList: ArrayList<Stop> = ArrayList<Stop>()
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

        var txtData = findViewById(R.id.textView) as TextView
        txtData.text = stopList.toString()
    }
}
