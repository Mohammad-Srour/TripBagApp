package com.example.trip_bag;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtQty;
    private EditText edtDate;
    private CheckBox checkBoxIsPacked;
    private Switch switchIsImportant;
    private Spinner spinnerCategory;
    private Spinner spinnerBagType;

    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtName = findViewById(R.id.edt_Item_Name);
        edtQty = findViewById(R.id.edt_Quantity);
        edtDate = findViewById(R.id.edt_Reminder_Date);
        checkBoxIsPacked = findViewById(R.id.checkBoxIsPacked);
        switchIsImportant = findViewById(R.id.switchIsImportant);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerBagType = findViewById(R.id.spinnerBagType);

        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);

        String name = intent.getStringExtra("name");
        int quantity = intent.getIntExtra("quantity", 1);
        String date = intent.getStringExtra("date");
        String category = intent.getStringExtra("category");
        String bagType = intent.getStringExtra("bagType");
        boolean isPacked = intent.getBooleanExtra("isPacked", false);
        boolean isImportant = intent.getBooleanExtra("isImportant", false);

        edtName.setText(name);
        edtQty.setText(String.valueOf(quantity));
        edtDate.setText(date);
        edtDate.setFocusable(false);
        edtDate.setClickable(true);
        edtDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    EditActivity.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {

                        String newDate  = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        edtDate.setText(newDate);
                    },
                    year, month, day
            );

            datePicker.show();
        });

        checkBoxIsPacked.setChecked(isPacked);
        switchIsImportant.setChecked(isImportant);
    }

    public void btn_Save(View view) {
        String newName = edtName.getText().toString();
        int newQuantity = Integer.parseInt(edtQty.getText().toString());
        String newDate = edtDate.getText().toString();
        String newCategory = spinnerCategory.getSelectedItem().toString();
        String newBagType = spinnerBagType.getSelectedItem().toString();
        boolean newPacked = checkBoxIsPacked.isChecked();
        boolean newImportant = switchIsImportant.isChecked();

        Intent resultIntent = new Intent();

        resultIntent.putExtra("position", position);
        resultIntent.putExtra("name", newName);
        resultIntent.putExtra("quantity", newQuantity);
        resultIntent.putExtra("date", newDate);
        resultIntent.putExtra("category", newCategory);
        resultIntent.putExtra("bagType", newBagType);
        resultIntent.putExtra("isPacked", newPacked);
        resultIntent.putExtra("isImportant", newImportant);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    }




