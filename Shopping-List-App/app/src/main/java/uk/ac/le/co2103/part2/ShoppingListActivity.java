package uk.ac.le.co2103.part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static uk.ac.le.co2103.part2.UpdateProductActivity.EXTRA_SHOPPING_LIST;

public class ShoppingListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE_PRODUCT = 3;

    private static final int REQUEST_ADD_PRODUCT = 2;

    public static final String EXTRA_PRODUCT = "uk.ac.le.co2103.part2.EXTRA_PRODUCT";
    private ProductAdapter adapter;

    private ShoppingList shoppingList;

    private RecyclerView recyclerView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // Retrieve the shopping list passed from MainActivity
        shoppingList = (ShoppingList) getIntent().getSerializableExtra("shoppingList");

        // Initialize the products arraylist and adapter
        ArrayList<Product> products = shoppingList.getProducts();
        adapter = new ProductAdapter();
        adapter.setProducts(products);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            // Handle events for item click in product list
            @Override
            public void onItemClick(Product product) {
                int position = adapter.getPosition(product);
                Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT, product);
                intent.putExtra(UpdateProductActivity.EXTRA_POSITION, position);
                intent.putExtra(EXTRA_SHOPPING_LIST, shoppingList);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_PRODUCT);
            }

            // Handle events when clicked on edit button
            @Override
            public void onEditClick(Product product) {
                int position = adapter.getPosition(product);
                Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT, product);
                intent.putExtra(UpdateProductActivity.EXTRA_POSITION, position);
                intent.putExtra(EXTRA_SHOPPING_LIST, shoppingList);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_PRODUCT);


            }

            // Handle deletion of products using product name
            @Override
            public void onDeleteClick(Product product) {
                adapter.deleteProduct(product.getName());
            }

            // Display product description when long clicked on a product
            @Override
            public void onItemLongClick(Product product) {
                String productDetails = "Shopping list name: " + shoppingList.getName() + "\n"
                        + "Product name: " + product.getName() + "\n"
                        + "Quantity: " + product.getQuantity() + "\n"
                        + "Unit: " + product.getUnit().getDisplayUnit();
                Toast.makeText(ShoppingListActivity.this, productDetails, Toast.LENGTH_SHORT).show();
            }

        });

        // FloatingActionButton with id fabAddProduct to add products within a shopping list
        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
                intent.putExtra("shoppingList", shoppingList);
                startActivityForResult(intent, REQUEST_ADD_PRODUCT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_ADD_PRODUCT && resultCode == RESULT_OK) {
            Product updatedProduct = (Product) data.getSerializableExtra(EXTRA_PRODUCT);

            // Retrieve the shopping list to add the product.
            shoppingList.addProduct(updatedProduct);
            // Update the RecyclerView adapter.
            adapter.updateProduct(updatedProduct);
        }
        else if (requestCode == REQUEST_CODE_UPDATE_PRODUCT && resultCode == RESULT_OK) {
            shoppingList = (ShoppingList) data.getSerializableExtra(EXTRA_SHOPPING_LIST);
            adapter.setProducts(shoppingList.getProducts());
            adapter.notifyDataSetChanged();
        }

        adapter.setProducts(shoppingList.getProducts());
        adapter.notifyDataSetChanged();
    }

    // Method to go back to shopping list view
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("shoppingList", shoppingList);
        resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

}
