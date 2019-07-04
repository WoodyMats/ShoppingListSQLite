package com.example.shoppinglistactivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private Context context;
    private Cursor cursor;
    public ShoppingListAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(ShoppingListTable.ShoppingListEntry.COLUMN_NAME));
        long id = cursor.getInt(cursor.getColumnIndex(ShoppingListTable.ShoppingListEntry._ID));
        holder.nameText.setText(name);

    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null){
            cursor.close();
        }

        cursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public Button removeFromList;

        public ShoppingListViewHolder(View itemView){
            super(itemView);

            nameText = itemView.findViewById(R.id.textview_name_item);
            removeFromList = itemView.findViewById(R.id.removeBtn);
        }
    }
}
