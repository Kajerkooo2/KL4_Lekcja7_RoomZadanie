package com.example.mblkcj6;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etPower;
    private CheckBox cbIsHero;
    private Spinner spUnitType;
    private Button btnAdd;
    private ListView listViewCards;

    private GwintDatabase db;

    private List<Gwint> cardList = new ArrayList<>();
    private ArrayAdapter<Gwint> adapter;
    Gwint selectedCardToEdit = null;
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

        etName = findViewById(R.id.etName);
        etPower = findViewById(R.id.etPower);
        cbIsHero = findViewById(R.id.cbIsHero);
        spUnitType = findViewById(R.id.spUnitType);
        btnAdd = findViewById(R.id.btnAdd);
        listViewCards = findViewById(R.id.listViewCards);

        db = GwintDatabase.getInstance(this);

        String[] unitTypes = {"Bliskie starcie (Melee)", "Daleki zasięg (Range)", "Obleżenie (Artillery)"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, unitTypes);
        spUnitType.setAdapter(spinnerAdapter);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cardList);
        listViewCards.setAdapter(adapter);

        loadCards();

        listViewCards.setOnItemLongClickListener((parent,view,postion,id)->{
            Gwint cardToDelete = cardList.get(postion);
            db.gwintDAO().delete(cardToDelete);

            Toast.makeText(MainActivity.this,"Usunięto karte " + cardToDelete.getName(),Toast.LENGTH_SHORT).show();
            loadCards();

            return true;
        });

        btnAdd.setOnClickListener(v -> {
            if (selectedCardToEdit == null) {
                saveCard();
            } else {
                updateCard();
            }
        });
        listViewCards.setOnItemClickListener((parent, view, position, id) -> {
            selectedCardToEdit = cardList.get(position);

            etName.setText(selectedCardToEdit.getName());
            etPower.setText(String.valueOf(selectedCardToEdit.getPower()));
            cbIsHero.setChecked(selectedCardToEdit.isHero());

            String unitType = selectedCardToEdit.getUnitType();
            for (int i = 0; i < unitTypes.length; i++) {
                if (unitTypes[i].equals(unitType)) {
                    spUnitType.setSelection(i);
                    break;
                }
            }
            btnAdd.setText("Aktualizuj karte");
        });
    }
    private void saveCard() {
        String name = etName.getText().toString().trim();
        String powerStr = etPower.getText().toString().trim();
        boolean isHero = cbIsHero.isChecked();
        String unitType = spUnitType.getSelectedItem().toString();

        if (name.isEmpty() || powerStr.isEmpty()) {
            Toast.makeText(this, "Wypelenij nazwe i sile karty", Toast.LENGTH_SHORT).show();
            return;
        }

        int power = Integer.parseInt(powerStr);
        Gwint newCard = new Gwint(power,isHero,unitType,name);

        db.gwintDAO().insert(newCard);

        Toast.makeText(this, "Dodano karte", Toast.LENGTH_SHORT).show();
        clearForm();
        loadCards();
    }

    private void loadCards() {
        List<Gwint> cards = db.gwintDAO().getAllCards();
        cardList.clear();
        cardList.addAll(cards);
        adapter.notifyDataSetChanged();
    }

    private void clearForm() {
        etName.setText("");
        etPower.setText("");
        cbIsHero.setChecked(false);
        spUnitType.setSelection(0);
        etName.requestFocus();
    }
    private void updateCard() {
        String name = etName.getText().toString().trim();
        String powerStr = etPower.getText().toString().trim();
        boolean isHero = cbIsHero.isChecked();
        String unitType = spUnitType.getSelectedItem().toString();

        if (name.isEmpty() || powerStr.isEmpty()) {
            Toast.makeText(this, "Wypelnij nazwę i sile", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedCardToEdit.setName(name);
        selectedCardToEdit.setPower(Integer.parseInt(powerStr));
        selectedCardToEdit.setHero(isHero);
        selectedCardToEdit.setUnitType(unitType);

        db.gwintDAO().update(selectedCardToEdit);

        Toast.makeText(this, "Zaktualizowano karte", Toast.LENGTH_SHORT).show();
        clearForm();
        loadCards();
    }
}