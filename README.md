## 📂 Projects Included

---

### 🔹 1. Currency Converter App

A simple Android application to convert between multiple currencies with theme support.

#### ✨ Features:
- Convert between **INR, USD, EUR, JPY**
- User-friendly interface with Material Design
- Light Mode and Dark Mode toggle
- Input validation for amount field
- Real-time conversion calculation

#### 🛠️ Technologies Used:
- Java
- Android SDK
- Material Components
- SharedPreferences (for theme storage)

---

### 🔹 2. Media Player App

An Android application to play audio files and stream video content.

#### ✨ Features:
- Play audio files from device storage
- Stream video using URL input
- Media controls:
  - Play
  - Pause
  - Stop
  - Restart
- File picker for selecting local files
- Error handling for invalid media

#### 🛠️ Technologies Used:
- MediaPlayer API
- VideoView
- MediaController
- URI handling

---

### 🔹 3. Sensor Data App

An application that reads and displays real-time sensor data from the device.

#### ✨ Features:
- Displays Accelerometer data (X, Y, Z axes)
- Shows Light sensor values
- Displays Proximity sensor readings
- Handles unavailable sensors gracefully
- Real-time updates using listeners

#### 🛠️ Technologies Used:
- SensorManager
- SensorEventListener
- Android Hardware Sensors API

---

### 🔹 4. Gallery & Camera App

A complete image management application with camera and gallery functionality.

#### ✨ Features:

##### 📸 Camera:
- Capture images using device camera
- Save images to device storage
- Automatic file naming

##### 📁 Gallery:
- Select folder from device storage
- Display images in grid layout
- Supports JPG, PNG, JPEG formats

##### 🖼️ Image Details:
- View image preview
- Show:
  - File name
  - File size
  - Date created

##### 🗑️ Delete Feature:
- Delete image with confirmation dialog
- Safe removal using DocumentFile API
- Automatic return to gallery after deletion

#### 🛠️ Technologies Used:
- Camera Intent
- FileProvider
- RecyclerView (Grid Layout)
- DocumentFile API
- Storage Access Framework (SAF)

---

## 🚀 How to Run the Projects

1. Clone this repository:
```bash
git clone https://github.com/CoderPrateek971/Mobile-Application-Development
```

2. Open the project in Android Studio

3. Select the desired module:
   
       CurrencyConverter
       Media Player
       Sensor Data
       Gallery

5. Build and run the application on:

       Android Emulator
       OR
       Physical Android Device


## 🔐 Permissions Required

Depending on the module, the following permissions may be required:

    📷 Camera Permission (for capturing images)

    📁 Storage Permission (for reading/writing files)

    🌐 Internet Permission (for streaming media)

📌 Project Highlights

- Modular structure (each question as a separate project)

- Clean and readable code

- Proper UI design using Material Components

- Error handling implemented

- Tested on both emulator and real device


---

##  Author
Prateek Garg
