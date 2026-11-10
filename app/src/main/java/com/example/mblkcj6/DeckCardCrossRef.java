package com.example.mblkcj6;

import androidx.room.Entity;

@Entity(tableName = "deck_card_cross_ref", primaryKeys = {"deckId","id"})
public class DeckCardCrossRef {
    public int deckId; public int id;
    public DeckCardCrossRef(int deckId, int id) {
        this.deckId = deckId;
        this.id = id;
    }
}
