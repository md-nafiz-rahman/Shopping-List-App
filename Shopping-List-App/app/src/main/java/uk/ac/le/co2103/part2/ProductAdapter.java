package uk.ac.le.co2103.part2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products = new ArrayList<>();
    private OnItemClickListener listener;

    // Set list of products
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    // Define item clicks
    public interface OnItemClickListener {
        void onItemClick(Product product);
        void onEditClick(Product product);
        void onDeleteClick(Product product);
        void onItemLongClick(Product product);
    }


    // View holder for product list item
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView, listener);
    }

    // Bind data for view holder
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewQuantity.setText(String.valueOf(product.getQuantity()));
        holder.textViewUnit.setText(product.getUnit().toString());

        // Button for decrementing quantity
        if(holder.buttonDecrement != null){
            holder.buttonDecrement.setOnClickListener(v -> {
                int quantity = product.getQuantity() - 1; // decrement by 1
                if (quantity < 0) {
                    quantity = 0;
                }
                product.setQuantity(quantity);
                holder.textViewQuantity.setText(String.valueOf(quantity));
            });
        }

        // Button for incrementing quantity
        if(holder.buttonIncrement != null){
            holder.buttonIncrement.setOnClickListener(v -> {
                int quantity = product.getQuantity() + 1; // increment by 1
                product.setQuantity(quantity);
                holder.textViewQuantity.setText(String.valueOf(quantity));
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            // Display pop up menu with option for edit or delete when clicked on product
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.itemView);
                popupMenu.inflate(R.menu.product_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit_product:
                                // Call onEditClick of the listener
                                if (listener != null) {
                                    listener.onEditClick(product);
                                }
                                return true;

                            case R.id.delete_product:
                                // Call onDeleteClick of the listener
                                if (listener != null) {
                                    listener.onDeleteClick(product);
                                }
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }


    // Retrieve number of products
    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // View holder for product list item
    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewQuantity;
        private TextView textViewUnit;
        private Button buttonDecrement;
        private Button buttonIncrement;

        public ProductViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewQuantity = itemView.findViewById(R.id.text_view_quantity);
            textViewUnit = itemView.findViewById(R.id.text_view_unit);
            buttonDecrement = itemView.findViewById(R.id.buttonDecrementQuantity);
            buttonIncrement = itemView.findViewById(R.id.buttonIncrementQuantity);

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ProductAdapter.this.listener.onItemLongClick(products.get(position));
                }
                return true;
            });
        }
    }

    // Method to update product within a list
    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product != null && product.getName().equals(updatedProduct.getName())) {
                products.set(i, updatedProduct);
                notifyItemChanged(i);
                break;
            }
        }
    }

    // Method to delete a product within a list
    public void deleteProduct(String productName) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(productName)) {
                products.remove(i);

                notifyItemRemoved(i);
                break;
            }
        }
    }



    public int getPosition(Product product) {
        return products.indexOf(product);
    }


}
