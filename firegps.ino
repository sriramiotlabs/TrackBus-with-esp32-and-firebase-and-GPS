#include <WiFi.h>
#include <FirebaseESP32.h>
#include <TinyGPS++.h>

#define FIREBASE_HOST "https://gps-fa1e6-default-rtdb.firebaseio.com/"
#define FIREBASE_AUTH "GIEmBF5cwoLWw729URm9fsy8l6HjnFGkXlJ5tyuefetio"
#define WIFI_SSID "*****"
#define WIFI_PASSWORD "******"
float latitude ;
float longitude ;

//Define FirebaseESP32 data object
FirebaseData firebaseData;
FirebaseJson json;
int Vresistor = A0;
float Vrdata = 0.0;
TinyGPSPlus gps;
#define RXD2 16
#define TXD2 17
HardwareSerial neogps(1);

void setup()
{

  Serial.begin(115200);
  pinMode(2,OUTPUT);
  neogps.begin(9600, SERIAL_8N1, RXD2, TXD2);
  pinMode(Vresistor, INPUT);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
     Serial.print(".");
     delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  digitalWrite(2,HIGH);
  delay(1000);
  digitalWrite(2,LOW);
  delay(1000);
  digitalWrite(2,HIGH);
  delay(1000);
  digitalWrite(2,LOW);
  delay(1000);
  digitalWrite(2,HIGH);
  delay(1000);
  digitalWrite(2,LOW);
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);

    //Set database read timeout to 1 minute (max 15 minutes)
  Firebase.setReadTimeout(firebaseData, 1000 * 60);
  //tiny, small, medium, large and unlimited.
  //Size and its write timeout e.g. tiny (1s), small (10s), medium (30s) and large (60s).
  Firebase.setwriteSizeLimit(firebaseData, "tiny");
  Serial.println("------------------------------------");
  Serial.println("Connected...");

} 

void loop()
{

  float randomFloat_la = random(0, 10000000) / 1000000.0;
  float randomFloat_lo = random(0, 10000000) / 1000000.0;
  boolean newData = false;
  for (unsigned long start = millis(); millis() - start < 1000;)
  {
    while (neogps.available())
    {
      if (gps.encode(neogps.read()))
      {
        newData = true;
      }
   latitude = (gps.location.lat());
   longitude =(gps.location.lng());
   //Firebase json;
   if(newData == true)
   {
    newData = false;
    Serial.println(gps.satellites.value());
  
 

  if (gps.location.isValid() == 1)
   {
     Serial.print("lat");
     Serial.println(latitude,6);
     Serial.print("lon");
     Serial.println(longitude,6);
     json.set("/latitude", latitude);
     json.set("/longitude", longitude);
     Firebase.updateNode(firebaseData, "/s_data", json);
     delay(3000);
  }
  else
  {
    Serial.print(F("INVALID"));
  }

  }

  }
  }
}
