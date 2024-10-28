package uk.ac.le.co2103.part2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class ShoppingList implements Serializable {

    // Unique identifier listId, name, image and lists of products for shopping list
    private UUID listId;
    private String name;
    private String image;
    private ArrayList<Product> products;

    // Constructor for shopping list with image
    public ShoppingList(String name, String image) {
        this.listId = UUID.randomUUID();
        this.name = name;
        this.image = image;
        this.products = new ArrayList<>();
    }

    // Constructor for shopping list without image
    public ShoppingList(String name) {
        this.listId = UUID.randomUUID();
        this.name = name;
        this.products = new ArrayList<>();
    }
    public String getImageUriAsString() {
        return image;
    }


    // Method for adding product to a list
    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }


    // Method for updating product in a list
    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(product.getName())) {
                products.set(i, product);
                break;
            }
        }
    }


    // Setter and getter method for products list, listId, name and image for shopping list
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public UUID getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
