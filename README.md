# Shopping List App

A Java Android app made using Android Studio for creating and managing shopping lists, allowing users to add, view, edit, and delete lists and items with quantities, units, and optional images.

## Features

1. **Main Activity (`MainActivity`)**: Displays shopping lists in a RecyclerView, with options to add or delete lists.
2. **Create List (`CreateListActivity`)**: Allows users to add a list with a unique name and optional image.
3. **View List (`ShoppingListActivity`)**: Shows items in a selected list. Users can tap items for options to edit or delete.
4. **Add Product (`AddProductActivity`)**: Enables adding products with name, quantity, and unit options. Duplicate names show a toast warning.
5. **Edit Product (`UpdateProductActivity`)**: Provides editing functionality with quantity adjustment and save options.

## Structure

All Java classes are in `uk.ac.le.co2103.part2`. Key files:
- `MainActivity.java`: Main screen displaying shopping lists.
- `CreateListActivity.java`: Add new shopping lists.
- `ShoppingListActivity.java`: View items in a list.
- `AddProductActivity.java`: Add products to a list.
- `UpdateProductActivity.java`: Edit product details.

## Installation

1. Open the project in Android Studio.
2. Build and run the app on an emulator or device.

---

This project was created as part of an Android development exercise.
