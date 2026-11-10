package com.example.mblkcj6;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class DeckWithCards {
    @Embedded public Deck deck;
    @Relation(
            parentColumn = "deckId",
            entityColumn = "id",
            associateBy = @Junction(DeckCardCrossRef.class)
    )
    public List<Gwint> cards;
}
