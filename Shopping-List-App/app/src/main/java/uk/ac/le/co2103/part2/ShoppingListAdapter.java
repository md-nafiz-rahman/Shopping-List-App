package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private ArrayList<ShoppingList> shoppingLists; // declare shoppingLists arraylist

    // Constructor for ShoppingListAdapter
    public ShoppingListAdapter(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // View holder class for recyclerview items
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView shoppingListName;
        public ImageView imageView;

        // constructor for view holder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shoppingListName = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    // Bind data to the view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.shoppingListName.setText(shoppingList.getName());
        String imageUriString = shoppingList.getImage();
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            holder.imageView.setImageURI(imageUri);
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        // on click listener for item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddProductActivity.class);
                intent.putExtra("shoppingList", shoppingList);
                view.getContext().startActivity(intent);
            }
        });
    }

    // Retrieve number of products in shoppingLists
    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }


    // Retrieve ShoppingList from a specific position in shoppingLists
    public ShoppingList getShoppingListAt(int position) {
        return shoppingLists.get(position);
    }

}
