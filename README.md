---
# E-Medib Android Application


![home1](https://github.com/user-attachments/assets/5f296960-6968-4567-ab30-406eec813549)
![home2](https://github.com/user-attachments/assets/fee0812a-79e2-40f7-a4e1-d46e4da1152f)
![dsmqpage](https://github.com/user-attachments/assets/5b7d9189-c798-4447-b1e0-e7e66b1099dd)

E-Medib is an Android application designed to monitor, document, and assess the condition of Diabetes Mellitus type 1 and 2 patients. The frontend of this application is built using Kotlin, Jetpack Compose, and XML, and is developed in Android Studio. The backend is powered by Laravel, with MySQL as the database. Authentication and API testing were done using Postman.
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


This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Feel free to customize this `README.md` file according to your specific project requirements and preferences.
