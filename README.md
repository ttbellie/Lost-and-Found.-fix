# Lost & Found App

## SIT305 Task 7.1P - Lost and Found Mobile Application

### Overview
The Lost & Found App is an Android application that helps connect lost items with their owners. Users can report lost or found items by posting details including item name, description, location, date, category, and an image. Other users can browse the listings and contact the poster to return items.

### Features
- **Create Adverts**: Post lost or found items with details (name, phone, description, date, location, category, image)
- **Browse Listings**: View all posted lost and found items in a list
- **Filter by Category**: Filter items by category (Electronics, Pets, Wallets, Keys, Jackets, Umbrellas, Others)
- **Image Upload**: Capture photos using camera or select from gallery
- **Date/Time Stamps**: Each posting shows how recently it was listed (e.g., "2 days ago")
- **Remove Items**: Delete adverts after the item has been returned to its owner
- **SQLite Database**: All data is stored locally using SQLite

### Tech Stack
- **Language**: Java
- **Database**: SQLite (via SQLiteOpenHelper)
- **UI Components**: RecyclerView, MaterialCardView, TextInputLayout, Spinner
- **Architecture**: Activity-based with Model-View pattern
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

### Project Structure
```
app/src/main/java/com/example/lostandfound/
├── MainActivity.java          # Home screen with navigation buttons
├── CreateAdvertActivity.java  # Form to create new lost/found post
├── ListActivity.java          # List all items with category filter
├── DetailActivity.java        # View item details and remove
├── ItemAdapter.java           # RecyclerView adapter for item list
├── Item.java                  # Data model class
└── DatabaseHelper.java        # SQLite database helper
```

### How to Run
1. Clone this repository
2. Open the project in Android Studio
3. Build and run on an emulator or physical device (API 26+)

### Screenshots
*(Add screenshots of your app here)*
