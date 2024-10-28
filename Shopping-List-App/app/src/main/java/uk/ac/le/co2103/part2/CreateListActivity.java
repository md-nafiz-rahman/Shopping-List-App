package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateListActivity extends AppCompatActivity {

    private EditText editTextListName;

    private ImageView imageViewList;

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;

    private ImageView imageView;

    private Button buttonCreateList;

    private Button buttonSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        // Initialize UI elements for activity_create_list
        editTextListName = findViewById(R.id.editTextListName);
        imageView = findViewById(R.id.imageView);
        imageViewList = findViewById(R.id.imageView);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonCreateList = findViewById(R.id.buttonCreateList);

        buttonCreateList.setOnClickListener(view -> {

            String listName = editTextListName.getText().toString().trim();

            // Check if list name is empty
            if (!listName.isEmpty()) {
                for (ShoppingList list : MainActivity.shoppingLists) {
                    // Check if list name already exists, if it does display toast message
                    if (list.getName().equalsIgnoreCase(listName)) {
                        Toast.makeText(CreateListActivity.this, listName + " already exists, please try a different name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Create an intent and add listName as extra
                Intent intent = new Intent();
                intent.putExtra("listName", listName);

                // Get the image URI from the ImageView's Tag
                String imageUriString = (String) imageView.getTag();
                if (imageUriString != null) {
                    intent.putExtra("imageUri", imageUriString);
                }

                setResult(RESULT_OK, intent);
                finish();
            } else {
                // display toast message if list name is not entered
                Toast.makeText(CreateListActivity.this, "Please enter a name for Shopping List", Toast.LENGTH_SHORT).show();
            }
        });

        // Onclick listener for image selection button
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent for choosing an image from the phone's gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }
        });
    }



    // Handle image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageViewList.setTag(imageUri.toString());
        }
    }

}
