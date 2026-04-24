📍 ESP32 GPS Tracker with Firebase & Android Integration
🚀 Overview

This project implements a real-time GPS tracking system using an ESP32, a GPS module, and Firebase Realtime Database. The ESP32 reads live GPS coordinates and pushes them to Firebase. An Android application listens for updates from Firebase and displays the location dynamically using Google Maps via intents.

The system ensures that whenever the GPS location changes, the updated coordinates are reflected both in Firebase and on the Android device.

🧠 Concept
ESP32 connects to Wi-Fi
GPS module provides real-time latitude & longitude
ESP32 uploads coordinates to Firebase Realtime Database
Android app retrieves updated coordinates from Firebase
Location is displayed on Google Maps using intents
Continuous updates ensure real-time tracking
🧩 Components Used
Hardware
ESP32 Development Board
GPS Module (e.g., NEO-6M)
Connecting wires
Power supply
Software & Tools
Arduino IDE
Firebase Realtime Database
Android Studio (for mobile app)
Google Maps API
🔌 Circuit Connections
GPS Module	ESP32
VCC	3.3V
GND	GND
TX	RX2 (GPIO16)
RX	TX2 (GPIO17)
⚙️ Working Principle
ESP32 initializes Wi-Fi and connects to Firebase
GPS module sends NMEA data to ESP32 via serial communication
Using the TinyGPS++ library:
Latitude and Longitude are extracted
If valid GPS data is received:
Data is formatted into JSON
Uploaded to Firebase under /s_data
Android app:
Listens for Firebase updates
Fetches new coordinates
Opens/updates location in Google Maps using intents
Process repeats continuously for live tracking
🔄 Data Flow
GPS Module → ESP32 → Firebase → Android App → Google Maps
📡 Firebase Structure
s_data: {
  latitude: xx.xxxxxx,
  longitude: yy.yyyyyy
}
📱 Android App Functionality
Connects to Firebase Realtime Database
Listens for coordinate changes
Uses intents to open location in Google Maps
Automatically updates map when new data arrives
💻 Code Features
Wi-Fi auto-reconnection
Real-time GPS parsing using TinyGPS++
Firebase JSON updates
Validation of GPS signal before upload
Efficient data update using Firebase.updateNode()
▶️ How to Run
ESP32 Setup
Install required libraries:
WiFi.h
FirebaseESP32
TinyGPS++

🔄 Flowchart
        START
          ↓
   Initialize ESP32
          ↓
   Connect to WiFi
          ↓
   Connect to Firebase
          ↓
   Read GPS Data
          ↓
   Is Data Valid?
     ↓        ↓
   YES        NO
    ↓          ↓
Send to Firebase  Retry
    ↓
Android App Listens
    ↓
Receive Coordinates
    ↓
Open Google Maps
    ↓
Update Location
    ↓
        LOOP

        
**Update credentials:**

#define WIFI_SSID "your_ssid"
#define WIFI_PASSWORD "your_password"
#define FIREBASE_HOST "your_database_url"
#define FIREBASE_AUTH "your_secret_key"
Upload code to ESP32
Open Serial Monitor to verify GPS data
Android Setup
Connect app to Firebase project
Add Firebase Realtime Database dependency
Implement listener for /s_data
Use intent to launch Google Maps with coordinates
📌 Example Google Maps Intent
Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
mapIntent.setPackage("com.google.android.apps.maps");
startActivity(mapIntent);
⚠️ Notes
Ensure GPS module has a clear view of the sky for accurate readings
Firebase rules should allow read/write access (configure securely for production)
Internet connectivity is required for both ESP32 and Android device
📈 Future Improvements
Add history tracking
Add geofencing alerts
Improve accuracy with filtering
Integrate notifications


