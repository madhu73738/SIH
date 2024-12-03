package com.example.agro


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults


import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/*
@Composable
fun Screen2(){
    val city = remember { mutableStateOf("") }

    var time by remember{
        mutableStateOf(0L)
    }
    var isRunning by remember{
        mutableStateOf(false)
    }
    var startTime by remember{
        mutableStateOf(0L)
    }
     val context = LocalContext.current

    val keyboardController= LocalSoftwareKeyboardController.current


    Column(modifier= Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement= Arrangement.Top) {

        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly)
        {
            WeatherCard(label = "Temperature", value = "25°C", icon = Icons.Default.Star)
            WeatherCard(label = "Humidity", value = "55%", icon = Icons.Default.Warning)
        }

        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly)
        {
            WeatherCard(label = "Soil Temperature", value = "22°C", icon = Icons.Default.Star)
            WeatherCard(label = "Soil Moisture", value = "60%", icon = Icons.Default.Warning)
        }
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly)
        {
            WeatherCard(label = "Prediction For Rain", value = "Rainfall Predicted", icon = Icons.Default.Info)
        }
        Text(text="All the regional Data ")


        Text(text= formatTime(timeMi=time),
            style= MaterialTheme.typography.headlineLarge,
            modifier= Modifier.padding(9.dp))
        Spacer(modifier = Modifier.height(18.dp))
        Row{
            Button(onClick= { if (isRunning){

                isRunning = false
            } else
            {
                startTime = System.currentTimeMillis() - time
                isRunning=true
                keyboardController?.hide()
            }
            }, modifier= Modifier.weight(1f)
                ){
                Text(text=if(isRunning) "Pause" else "Start", color= Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick={
                time = 0
                isRunning=false
            }, modifier= Modifier.weight(1f)){
                Text(text="Reset", color=Color.White)
            }
        }
    }
    LaunchedEffect(isRunning) {
        while(isRunning){
            delay(1000)
            time=System.currentTimeMillis() - startTime
        }
    }
}
*/

@Composable
fun Screen2() {

    // State for rainfall prediction
    var rainfallPrediction by remember { mutableStateOf<String>("Loading...") }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope() // To launch suspend functions in a coroutine
    var userInputTime by remember { mutableStateOf("0") } // User input for time in seconds

    // State for city (not used in the code but can be extended for user input)
    val city = remember { mutableStateOf("") }

    // Timer-related states
    var time by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(0L) }

    // Get the context and keyboard controller
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Use a coroutine to fetch weather data when the screen is loaded
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val apiKey = "1cbba39e39585260c7ac9e2280d7d87f"  // Replace with your actual API key
                val city = "New Delhi"  // Or any city you want
                val response = RetrofitInstance.api.getWeather(city, apiKey)

                // Check if there is rainfall data and update the UI accordingly
                rainfallPrediction = response.rain?.`1h`?.let {
                    "Rainfall  predicted in last 1 hour: $it mm"
                } ?: "No rainfall predicted"

                isLoading = false
            } catch (e: Exception) {
                rainfallPrediction = "Error fetching data"
                isLoading = false
            }
        }
    }

    // Format the time (this could be a helper function)
    fun formatTime(timeMi : Long): String{

        val hours= TimeUnit.MILLISECONDS.toHours(timeMi)
        val min = TimeUnit.MILLISECONDS.toMinutes(timeMi) %60
        val sec= TimeUnit.MILLISECONDS.toSeconds(timeMi) %60

        return String.format("%02d:%02d:%02d", hours , min, sec)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        // Weather data displayed in a LazyVerticalGrid (4 cards in total)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 cards per row
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between columns
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between rows
        ) {
            items(4) { index ->
                when (index) {
                    0 -> WeatherCard(label = "Temperature", value = "25°C", icon = painterResource(id = R.drawable.baseline_wb_sunny_24))
                    1 -> WeatherCard(label = "Humidity", value = "55%", icon = painterResource(id = R.drawable.baseline_cloud_24))
                    2 -> WeatherCard(label = "Soil Temperature", value = "22°C", icon = painterResource(id = R.drawable.baseline_wb_sunny_24))
                    3 -> WeatherCard(label = "Soil Moisture", value = "60%", icon =  painterResource(id = R.drawable.baseline_water_drop_24))
                }
            }
        }

        // Prediction Text for Rain
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            WeatherCard(
                label = "Prediction For Rain",
                value = rainfallPrediction,
                icon = painterResource(id = R.drawable.baseline_cloudy_snowing_24),
                modifier = Modifier.fillMaxWidth(0.6f).height(140.dp)
            )
        }

        // Timer display
        Text(
            text = formatTime(timeMi = time),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(9.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // User input for timer (minutes)
        OutlinedTextField(
            value = userInputTime,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    userInputTime = newValue
                }
            },
            label = { Text("Enter Time (in minutes)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Row for Start/Pause and Reset buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    val startTimeInMillis = userInputTime.toLongOrNull()?.times(60_000) ?: 0L // Convert minutes to milliseconds
                    if (startTimeInMillis > 0) {
                        time = startTimeInMillis
                        isRunning = !isRunning
                        startTime = System.currentTimeMillis()
                        keyboardController?.hide() // Hide keyboard when starting the timer
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = if (isRunning) "Pause" else "Start", color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

            Button(
                onClick = {
                    time = 0
                    isRunning = false
                    userInputTime = "0" // Reset input as well
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Reset", color = Color.White)
            }
        }
    }

    // Timer logic for reverse countdown
    LaunchedEffect(isRunning) {
        while (isRunning && time > 0) {
            delay(1000) // Delay of 1 second
            time -= 1000 // Decrease time by 1 second
        }
        if (time <= 0) {
            isRunning = false // Stop the timer when it reaches zero
        }
    }
}

@Composable
fun WeatherCard(label: String, value: String, icon: Painter, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(), // Use the modifier passed in to control width
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFBBDEFB) // Light blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(40.dp),
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Screen2()
}



/*
@Composable
fun formatTime(timeMi : Long): String{

    val hours= TimeUnit.MILLISECONDS.toHours(timeMi)
    val min = TimeUnit.MILLISECONDS.toMinutes(timeMi) %60
    val sec= TimeUnit.MILLISECONDS.toSeconds(timeMi) %60

    return String.format("%02d:%02d:%02d", hours , min, sec)
}*/
