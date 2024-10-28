package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText quantityEditText;
    private Spinner unitSpinner;
    private Button buttonDecrementQuantity;
    private Button buttonIncrementQuantity;
    private Button buttonSave;

    private Product product;

    // Declare extra keys for intent
    public static final String EXTRA_PRODUCT = "uk.ac.le.co2103.part2.EXTRA_PRODUCT";
    public static final String EXTRA_POSITION = "uk.ac.le.co2103.part2.EXTRA_POSITION";
    public static final String EXTRA_SHOPPING_LIST = "uk.ac.le.co2103.part2.EXTRA_SHOPPING_LIST";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        // Initialize UI elements for activity_update_product
        nameEditText = findViewById(R.id.nameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        unitSpinner = findViewById(R.id.unitSpinner);
        buttonDecrementQuantity = findViewById(R.id.buttonDecrementQuantity);
        buttonIncrementQuantity = findViewById(R.id.buttonIncrementQuantity);
        buttonSave = findViewById(R.id.buttonSave);

        // Retrieve and store position of a product in a shopping list
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        ShoppingList shoppingList = (ShoppingList) getIntent().getSerializableExtra(EXTRA_SHOPPING_LIST);

        if (position != -1) {
            product = shoppingList.getProducts().get(position);
        } else {

            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set on screen UI elements with product attributes
        nameEditText.setText(product.getName());
        quantityEditText.setText(String.valueOf(product.getQuantity()));
        unitSpinner.setSelection(((ArrayAdapter<String>) unitSpinner.getAdapter()).getPosition(product.getUnit().toString()));

        // On click listener for decrement button
        buttonDecrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(quantityEditText.getText().toString());
                if (currentQuantity > 0) {
                    currentQuantity--;
                    quantityEditText.setText(String.valueOf(currentQuantity));
                }
            }
        });

        // On click listener for increment button
        buttonIncrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(quantityEditText.getText().toString());
                currentQuantity++;
                quantityEditText.setText(String.valueOf(currentQuantity));
            }
        });

        // On click listener for save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                String unitString = unitSpinner.getSelectedItem().toString();
                Product.Unit unit = Product.Unit.valueOf(unitString.toUpperCase());

                // Update the product with the new values.
                product.setName(name);
                product.setQuantity(quantity);
                product.setUnit(unit);

                // Update the shopping list with the updated product.
                shoppingList.updateProduct(product);

                // Create a result intent and set the result to OK.
                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_SHOPPING_LIST, shoppingList);
                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });
    }

}
