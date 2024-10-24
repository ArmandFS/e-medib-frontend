---
# E-Medib Android Application

E-Medib is an Android application designed to monitor, track and assess the condition of Diabetes Mellitus type 1 and 2 patients. The Frontend of this application is built using Kotlin, Jetpack Compose, and XML, and is developed in Android Studio. The backend is powered by Laravel, with MySQL as the database. Authentication and API testing were done using Postman.
## Features
- User Authentication (Registration, Login, Logout)
- BMI and BMR Calculation
- Patient Data Management
- Integration with DSMQ
- User-Friendly Interface
## UI/UX Design
The UI/UX design for E-Medib was made using Figma, focusing on a user-friendly and intuitive experience for diabetic patients and healthcare providers.
## API Integration
The E-Medib application integrates with the backend using RESTful APIs. All API requests and responses are handled asynchronously using Retrofit and Kotlin coroutines.
### Authentication
- **Register:** `POST /api/register`
- **Login:** `POST /api/login`
- **Logout:** `POST /api/logout`
### User Profile
- **Get User Data:** `GET /api/accountData`
- **Update Profile:** `PUT /api/updateProfile`
### BMI & BMR Calculation
- **BMI Calculation:** Automatically calculated during registration and profile update.
- **BMR Calculation:** Automatically calculated during registration and profile update.
### DSMQ Integration
- **DSMQ Assessment:** Endpoints to handle DSMQ data integration and management.


---


