package uk.ac.le.co2103.part2;

import static uk.ac.le.co2103.part2.MainActivity.Constants.REQUEST_CREATE_LIST;
import static uk.ac.le.co2103.part2.MainActivity.Constants.REQUEST_VIEW_SHOPPING_LIST;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
    private RecyclerView recyclerView; // Initialize recyclerView for displaying shopping lists
    private ShoppingListAdapter adapter; // Initialize adapter for the recyclerView

    private static final String TAG = MainActivity.class.getSimpleName();

    boolean isLongClick = false;


    /*
    Constants used for request codes for handling
    creation of shopping list and viewing shopping list
    */
    public class Constants {
        public static final int REQUEST_CREATE_LIST = 1;
        public static final int REQUEST_VIEW_SHOPPING_LIST = 2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);

        shoppingLists = new ArrayList<>();

        // Recycler view setup to display shopping lists
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShoppingListAdapter(shoppingLists);
        recyclerView.setAdapter(adapter);

        // FloatingActionButton with id fab for creating new shopping lists
        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_LIST);
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            // When clicked on a shopping list, display the shopping list (list of products inside)
            @Override
            public void onItemClick(View view, int position) {
                new Handler().postDelayed(() -> {
                    if (!isLongClick) {
                        ShoppingList shoppingList = adapter.getShoppingListAt(position);
                        Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                        intent.putExtra("shoppingList", shoppingList);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, REQUEST_VIEW_SHOPPING_LIST);
                    }
                    isLongClick = false;
                }, 200);
            }

            /*
            When long clicked on shopping list display option to delete shopping list
            Display alert dialog to confirm deletion of shopping list with 'Yes' and 'No'
            confirm button.
             */
            @Override
            public void onItemLongClick(View view, int position) {
                isLongClick = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete shopping list")
                        .setMessage("Are you sure you want to delete this shopping list?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            ShoppingList shoppingList = adapter.getShoppingListAt(position);
                            shoppingLists.remove(shoppingList);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, shoppingList.getName() + " list has been deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

        }));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
        Retrieve and handle results from CreateListActivity with constant REQUEST_CREATE_LIST
        If user provides image then the constructor with image parameter is used, otherwise
        constructor with no image is as parameter is used to create shopping list
         */
        if (requestCode == Constants.REQUEST_CREATE_LIST && resultCode == RESULT_OK) {
            if (data != null) {
                String listName = data.getStringExtra("listName");
                String imageUriString = data.getStringExtra("imageUri");

                ShoppingList shoppingList;
                if (imageUriString != null) {
                    shoppingList = new ShoppingList(listName, imageUriString);
                } else {
                    shoppingList = new ShoppingList(listName);
                }
                shoppingLists.add(shoppingList);
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == Constants.REQUEST_VIEW_SHOPPING_LIST && resultCode == RESULT_OK) {
            if (data != null) {
                ShoppingList updatedShoppingList = (ShoppingList) data.getSerializableExtra("shoppingList");
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    shoppingLists.set(position, updatedShoppingList);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}
