package com.example.trip_bag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BagItem> displayList;
    private BigItemAdapter adapter;
    private RecyclerView recyclerView;

    private SearchView searchView;

    private CheckBox checkFilterPacked;
    private ArrayList<BagItem> allBagItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        checkFilterPacked = findViewById(R.id.checkFilterPacked);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allBagItems = SharedPref.loadItems(this);
        displayList = new ArrayList<>(allBagItems);



        adapter = new BigItemAdapter(displayList, this);
        recyclerView.setAdapter(adapter);

        String searchQuery = SharedPref.loadSearchQuery(this);
        boolean filterPacked = SharedPref.loadFilterPacked(this);

        if (searchQuery != null) {
            searchView.setQuery(searchQuery, false);
        }

        checkFilterPacked.setChecked(filterPacked);


        filterListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPref.saveSearchQuery(this, searchView.getQuery().toString());
        SharedPref.saveFilterPacked(this, checkFilterPacked.isChecked());
    }


    private void filterListeners() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                filterList();
                return false;
            }
            public boolean onQueryTextChange(String newText) {
                filterList();
                return true;
            }
        });
        checkFilterPacked.setOnCheckedChangeListener((buttonView, isChecked) -> {
            filterList();
        });

    }

    private void filterList() {
        String text = searchView.getQuery().toString().toLowerCase().trim();
        boolean ispacked = checkFilterPacked.isChecked();
        displayList.clear();

        ArrayList<BagItem> filteredList = new ArrayList<>();

        for(BagItem item : allBagItems) {
            if (item.getName().toLowerCase().contains(text) && (!ispacked || item.isPacked())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

    public void btn_Add(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String name = data.getStringExtra("name");
            String category = data.getStringExtra("category");
            int qty = data.getIntExtra("quantity", 0);
            String date = data.getStringExtra("date");
            boolean packed = data.getBooleanExtra("isPacked", false);
            boolean important = data.getBooleanExtra("isImportant", false);
            String bagType = data.getStringExtra("bagType");

            BagItem newItem = new BagItem(name, category, qty, date, packed, important, bagType);
            allBagItems.add(newItem);

            SharedPref.saveItems(this, allBagItems);

            filterList();
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {

            int position = data.getIntExtra("position", -1);
            if (position != -1) {

                BagItem item = allBagItems.get(position);

                item.setName(data.getStringExtra("name"));
                item.setCategory(data.getStringExtra("category"));
                item.setQuantity(data.getIntExtra("quantity", 1));
                item.setReminderDate(data.getStringExtra("date"));
                item.setPacked(data.getBooleanExtra("isPacked", false));
                item.setImportant(data.getBooleanExtra("isImportant", false));
                item.setBagType(data.getStringExtra("bagType"));

                SharedPref.saveItems(this, allBagItems);
                filterList();
            }
        }
    }


    public void removeBagItems(BagItem item) {

        allBagItems.remove(item);
        SharedPref.saveItems(this, allBagItems);
        filterList();
    }

    public ArrayList<BagItem> getAllBagItems() {
        return allBagItems;
    }


}
