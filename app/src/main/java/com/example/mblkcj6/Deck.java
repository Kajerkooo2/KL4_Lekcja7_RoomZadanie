package com.example.mblkcj6;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "decks")
public class Deck {
    @PrimaryKey(autoGenerate = true)
    private int deckId;
    private String deckName;

    public Deck(String deckName){
        this.deckName = deckName;
    }

    public Deck() {}

    @Override
    public String toString() {
        return deckName;
    }

    public int getDeckId() { return deckId; }
    public void setDeckId(int deckId) { this.deckId = deckId; }

    public String getDeckName() { return deckName; }
    public void setDeckName(String deckName) { this.deckName = deckName; }
}
