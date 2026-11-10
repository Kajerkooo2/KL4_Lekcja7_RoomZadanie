package com.example.mblkcj6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GwintDAO {

    @Insert
    void insert(Gwint card);

    @Delete
    void delete(Gwint card);

    @Update
    void update(Gwint card);
    @Query("SELECT * FROM gwint_cards ORDER BY id DESC")
    List<Gwint> getAllCards();

    @Insert
    long insertDeck(Deck deck);

    @Insert
    void insertCrossRef(DeckCardCrossRef crossRef);

    @Query("SELECT * FROM decks")
    List<Deck> getAllDecks();

    @Transaction
    @Query("SELECT * FROM decks WHERE deckId = :deckId")
    DeckWithCards getDeckWithCards(int deckId);
}
