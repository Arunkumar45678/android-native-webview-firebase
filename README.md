# Android Native WebView App (Firebase OAuth)

## Features
- Native Google Login (Firebase Auth)
- Secure WebView auto-login
- PHP + MySQL backend
- Camera & file upload supported
- Logout sync (App + Web)

## Flow
Native Login → Firebase → WebView → PHP Session

## Setup
1. Create Firebase project
2. Enable Google Authentication
3. Add Android app
4. Download google-services.json
5. Place in android-app/app/

Backend:
- Upload backend-php to hosting
- Add Firebase serviceAccount.json
