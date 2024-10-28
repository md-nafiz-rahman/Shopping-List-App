package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT = "uk.ac.le.co2103.part2.EXTRA_PRODUCT";

    private EditText nameEditText;
    private EditText quantityEditText;
    private Spinner unitSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize EditText for name and quantity
        nameEditText = findViewById(R.id.editTextName);
        quantityEditText = findViewById(R.id.editTextQuantity);
        unitSpinner = findViewById(R.id.spinner);

        // New adapter for spinner to show unit values
        ArrayAdapter<Product.Unit> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Product.Unit.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        Button addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get input for name,quantity from EditText and for unit from spinner
                String name = nameEditText.getText().toString();
                String quantityString = quantityEditText.getText().toString();
                String unitString = unitSpinner.getSelectedItem().toString();

                // Check if quantity is empty, if it is empty display toast message
                if (quantityString.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                } else {

                    // Check if name is empty, if it is empty display toast message
                    if (name.isEmpty()) {
                        Toast.makeText(AddProductActivity.this, "Please enter product name", Toast.LENGTH_SHORT).show();
                    } else {

                        int quantity = Integer.parseInt(quantityString);
                        Product.Unit unit = Product.Unit.valueOf(unitString.toUpperCase());

                        // Create product object with name, quantity and unit
                        Product product = new Product(name, quantity, unit);

                        // Retrieve shopping list object from intent
                        ShoppingList shoppingList = (ShoppingList) getIntent().getSerializableExtra("shoppingList");

                        // Check for duplicate product name
                        boolean productExists = false;
                        for (Product p : shoppingList.getProducts())
                            if (p.getName().equalsIgnoreCase(name)) {
                                productExists = true;
                                break;
                            }

                        // If duplicate product name is already in shopping list, display toast message to enter a different product name
                        if (productExists) {
                            Toast.makeText(AddProductActivity.this, product.getName() + " already exists, enter a different product.", Toast.LENGTH_SHORT).show();
                        } else {
                            shoppingList.addProduct(product);

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("shoppingList", shoppingList);
                            setResult(RESULT_OK, resultIntent);

                            Intent data = new Intent();
                            data.putExtra(EXTRA_PRODUCT, product);
                            setResult(RESULT_OK, data);

                            finish();
                        }
                    }
                }
            }
        });
    }
}