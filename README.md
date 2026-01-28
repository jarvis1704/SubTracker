# SubTracker

<div align="center">
  <!-- Placeholder for App Icon -->
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" alt="SubTracker Logo" width="120" />
  <br />
  
  <h1>SubTracker: Master Your Subscriptions</h1>
  
  <p>
    <strong>Track, Manage, and Optimize your recurring expenses.</strong>
  </p>

  <p>
    <a href="https://play.google.com/store/apps/details?id=com.biprangshu.subtracker">
      <img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" height="80" />
    </a>
  </p>

  <p>
    <img src="https://img.shields.io/badge/Kotlin-2.3.0-purple.svg?style=flat&logo=kotlin" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Compose-Material3-blue.svg?style=flat&logo=jetpackcompose" alt="Compose" />
    <img src="https://img.shields.io/badge/License-Apache%202.0-green.svg?style=flat" alt="License" />
    <img src="https://img.shields.io/badge/API-24%2B-orange.svg?style=flat&logo=android" alt="Min SDK" />
  </p>
</div>

---

## 📱 Overview

**SubTracker** is a modern, privacy-focused Android application designed to help you regain control over your digital financial life. In an era where everything is a subscription, SubTracker acts as your personal financial assistant, ensuring you never pay for an unused service or miss a renewal date again.

Built with **Jetpack Compose** and **Material 3**, it offers a beautiful, fluid experience while leveraging the power of **Google Gemini AI** to provide actionable insights into your spending habits.

## ✨ Key Features

*   **📊 Comprehensive Tracking:** distinct views for monthly and yearly recurring expenses.
*   **🔔 Smart Reminders:** Get notified *before* a payment is due. Never pay a late fee or unwanted renewal again.
*   **🧠 AI-Powered Insights:** 
    *   **Optimization:** Detects unused subscriptions and suggests bundles (e.g., region-specific offers).
    *   **Burn Rate Analysis:** Predicts your future cash flow needs.
    *   **Price Watch:** Alerts you if a service price is increasing in your region.
*   **📈 Visual Analytics:** Beautiful, interactive charts (powered by Vico) visualize your spending trends over time.
*   **🔒 Privacy First:** Your data lives on your device. We use a local-first architecture (Room Database) with optional cloud features only for AI analysis.
*   **🎨 Material You:** Fully dynamic theming that adapts to your device's wallpaper.
*   **👆 Biometric Lock:** Optional fingerprint/face unlock to keep your financial data secure.

## 📸 Screenshots

<!-- Add your screenshots here. Create a 'docs/screenshots' folder and add images. -->
<div align="center">
  <img src="docs/screenshots/home_placeholder.png" width="200" alt="Home Screen" />
  <img src="docs/screenshots/add_placeholder.png" width="200" alt="Add Subscription" />
  <img src="docs/screenshots/analytics_placeholder.png" width="200" alt="Analytics" />
  <img src="docs/screenshots/settings_placeholder.png" width="200" alt="Settings" />
</div>

## 🛠️ Tech Stack

SubTracker is built with modern Android development best practices:

*   **Language:** [Kotlin](https://kotlinlang.org/)
*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
*   **Architecture:** MVVM + Clean Architecture
*   **DI:** [Hilt](https://dagger.dev/hilt/)
*   **Local Data:** [Room Database](https://developer.android.com/training/data-storage/room)
*   **Background Work:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
*   **AI:** [Firebase GenAI (Gemini)](https://firebase.google.com/docs/genai)
*   **Charting:** [Vico](https://github.com/patrykandpatrick/vico)
*   **Image Loading:** [Coil](https://coil-kt.github.io/coil/)

## 🚀 Getting Started

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/SubTracker.git
    ```
2.  **Open in Android Studio:**
    Ensure you are using Android Studio Ladybug or newer.
3.  **Build & Run:**
    Sync Gradle project and run on an emulator or physical device (Android 7.0+).

## 🤝 Contributing

We welcome contributions! If you have an idea for a feature or have found a bug:

1.  Open an [Issue](https://github.com/yourusername/SubTracker/issues) to discuss the change.
2.  Fork the repository.
3.  Create your feature branch (`git checkout -b feature/AmazingFeature`).
4.  Commit your changes.
5.  Open a Pull Request.

## 📄 License

```text
Copyright 2026 Biprangshu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
