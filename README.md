# Lost & Found App

## Overview
Lost & Found is an Android mobile application that helps users report lost or found items and reconnect them with their owners. The app allows users to create adverts, upload images, and search for items using category filters.

This project was developed as part of SIT305 – Mobile Application Development.

---

## Features

- Create adverts for lost or found items
- Select Lost or Found type
- Upload images for each item
- Store data using SQLite database
- View all items in a RecyclerView list
- Filter items by category (Electronics, Pets, Wallets, etc.)
- Display timestamp for each advert
- Delete adverts when the item is returned

---

## Technologies Used

- **Language:** Java  
- **Layout:** XML  
- **Database:** SQLite  
- **UI Components:** RecyclerView, EditText, Buttons, ImageView  
- **Libraries:**  
  - AndroidX  
  - Glide (for image loading)  
  - Kotlin Coroutines (background tasks)  
- **SDK:** Android SDK (API level 28+)

---

## Project Structure

- `MainActivity` – Home screen navigation  
- `CreateAdvertActivity` – Create new lost/found post  
- `ItemListActivity` – Display all items  
- `ItemDetailActivity` – Show item details and delete option  
- `DatabaseHelper` – Handle SQLite operations  

---

## How to Run the App

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/LostAndFound-App.git
