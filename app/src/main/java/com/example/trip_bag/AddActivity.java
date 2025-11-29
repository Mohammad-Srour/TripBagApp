package com.example.trip_bag;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AddActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtQty;
    private EditText edtDate;
    private CheckBox checkBoxIsPacked;
    private Switch switchIsImportant;
    private Spinner spinnerCategory;
    private Spinner spinnerBagType;

    private static final String PREF_NAME = "lifeCycleAdd";

    private boolean isSaving = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtName = findViewById(R.id.edt_Item_Name);
        edtQty = findViewById(R.id.edt_Quantity);
        edtDate = findViewById(R.id.edt_Reminder_Date);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerBagType = findViewById(R.id.spinnerBagType);
        checkBoxIsPacked = findViewById(R.id.checkBoxIsPacked);
        switchIsImportant=findViewById(R.id.switchIsImportant);


        edtDate.setFocusable(false);
        edtDate.setClickable(true);
        edtDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    AddActivity.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {

                        String newDate  = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        edtDate.setText(newDate);
                    },
                    year, month, day
            );

            datePicker.show();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        edtName.setText(prefs.getString("name", ""));
        edtQty.setText(prefs.getString("quantity", ""));
        edtDate.setText(prefs.getString("date", ""));
        checkBoxIsPacked.setChecked(prefs.getBoolean("packed", false));
        switchIsImportant.setChecked(prefs.getBoolean("important", false));

        spinnerCategory.setSelection(prefs.getInt("category", 0));
        spinnerBagType.setSelection(prefs.getInt("bagType", 0));
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (isSaving) {
            return;
        }

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("name", edtName.getText().toString());
        editor.putString("quantity", edtQty.getText().toString());
        editor.putString("date", edtDate.getText().toString());
        editor.putBoolean("packed", checkBoxIsPacked.isChecked());
        editor.putBoolean("important", switchIsImportant.isChecked());
        editor.putInt("category", spinnerCategory.getSelectedItemPosition());
        editor.putInt("bagType", spinnerBagType.getSelectedItemPosition());

        editor.apply();
    }
    public void btn_Save(View view) {

        isSaving = true;
      String name=edtName.getText().toString();
      int quantity= edtQty.getText().toString().isEmpty() ? 1 : Integer.parseInt(edtQty.getText().toString());
      String date = edtDate.getText().toString();
        String category=spinnerCategory.getSelectedItem().toString();
        String bagType = spinnerBagType.getSelectedItem().toString();
        boolean packed = checkBoxIsPacked.isChecked();
        boolean important = switchIsImportant.isChecked();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("quantity", quantity);
        resultIntent.putExtra("date", date);
        resultIntent.putExtra("category", category);
        resultIntent.putExtra("bagType", bagType);
        resultIntent.putExtra("isPacked", packed);
        resultIntent.putExtra("isImportant", important);

        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        editor.clear();

        editor.apply();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}