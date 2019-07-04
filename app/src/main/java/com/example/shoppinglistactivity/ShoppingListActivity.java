package com.example.shoppinglistactivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingListActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private ShoppingListAdapter mAdapter;
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_layout);

        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.shoppingListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShoppingListAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        mEditTextName = findViewById(R.id.addItemEdtTxt);

        Button buttonAdd = findViewById(R.id.addBtn);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void addItem() {

        if (mEditTextName.getText().toString().trim().length() == 0) {
            return;
        }

        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListTable.ShoppingListEntry.COLUMN_NAME, name);

        mDatabase.insert(ShoppingListTable.ShoppingListEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());

        mEditTextName.getText().clear();
    }

    private void removeItem(long id) {
        mDatabase.delete(ShoppingListTable.ShoppingListEntry.TABLE_NAME,
                ShoppingListTable.ShoppingListEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                ShoppingListTable.ShoppingListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ShoppingListTable.ShoppingListEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}

