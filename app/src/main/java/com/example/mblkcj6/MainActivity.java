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
import androidx.appcompat.app.AlertDialog;
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
    private Spinner spUnitType, spDecks;
    private Button btnAdd, btnAddDeck, btnShowDeckCards;
    private ListView listViewCards;

    private GwintDatabase db;

    private List<Gwint> cardList = new ArrayList<>();
    private ArrayAdapter<Gwint> cardAdapter;

    private List<Deck> deckList = new ArrayList<>();
    private ArrayAdapter<Deck> deckAdapter;
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
        spDecks = findViewById(R.id.spDecks);
        btnAdd = findViewById(R.id.btnAdd);
        btnAddDeck = findViewById(R.id.btnAddDeck);
        btnShowDeckCards = findViewById(R.id.btnShowDeckCards);
        listViewCards = findViewById(R.id.listViewCards);

        db = GwintDatabase.getInstance(this);

        deckAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, deckList);
        spDecks.setAdapter(deckAdapter);

        cardAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, cardList);
        listViewCards.setAdapter(cardAdapter);

        loadCards();
        loadDecks();

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

        btnAddDeck.setOnClickListener(v -> showAddDeckDialog());

        btnShowDeckCards.setOnClickListener(v -> showCardsInSelectedDeck());

        String[] unitTypes = {"Bliskie starcie (Melee)", "Daleki zasięg (Range)", "Oblężenie (Artillery)"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, unitTypes);
        spUnitType.setAdapter(spinnerAdapter);

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
            btnAdd.setText("Aktualizuj kartę");
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
        cardAdapter.notifyDataSetChanged();
    }

    private void loadDecks() {
        List<Deck> decks = db.gwintDAO().getAllDecks();
        deckList.clear();
        deckList.addAll(decks);
        deckAdapter.notifyDataSetChanged();
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

    private void showAddDeckDialog() {
        EditText input = new EditText(this);
        input.setHint("Nazwa talii");

        new AlertDialog.Builder(this)
                .setTitle("Stwórz nowa talie")
                .setView(input)
                .setPositiveButton("Utwórz", (dialog, which) -> {
                    String deckName = input.getText().toString().trim();
                    if (!deckName.isEmpty()) {
                        db.gwintDAO().insertDeck(new Deck(deckName));
                        loadDecks();
                        Toast.makeText(this, "Utworzono talie", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void showCardsInSelectedDeck() {
        if (spDecks.getSelectedItem() == null) {
            Toast.makeText(this, "Brak dostępnych talii!", Toast.LENGTH_SHORT).show();
            return;
        }

        Deck selectedDeck = (Deck) spDecks.getSelectedItem();

        DeckWithCards deckWithCards = db.gwintDAO().getDeckWithCards(selectedDeck.getDeckId());

        if (deckWithCards != null && deckWithCards.cards != null) {
            cardList.clear();
            cardList.addAll(deckWithCards.cards);
            cardAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Karty z talii: " + selectedDeck.getDeckName(), Toast.LENGTH_SHORT).show();
        }
    }

}
