# KL4_Lekcja7_RoomZadanie

Prosta aplikacja Android napisana w Javie, która pokazuje podstawy obsługi biblioteki Room. Projekt umożliwia zarządzanie kartami Gwinta oraz tworzenie talii i przypisywanie do nich kart.

## Funkcjonalności

- dodawanie kart z nazwą, siłą, statusem bohatera i typem jednostki,
- edytowanie istniejących kart,
- usuwanie kart przez długie naciśnięcie na liście,
- tworzenie nowych talii,
- przypisywanie kart do talii,
- wyświetlanie kart należących do wybranej talii.

## Technologie

- Java 11,
- Android SDK,
- Android Jetpack Room 2.6.1,
- Gradle Kotlin DSL,
- AppCompat i Material Components,
- JUnit i Espresso.

## Baza danych

Aplikacja używa lokalnej bazy SQLite zarządzanej przez Room. W bazie znajdują się:

- `Gwint` — karta Gwinta,
- `Deck` — talia kart,
- `DeckCardCrossRef` — tabela pośrednia realizująca relację wiele-do-wielu,
- `GwintDatabase` — główna baza danych aplikacji,
- `GwintDAO` — operacje odczytu i zapisu danych.

## Wymagania

- Android Studio (najnowsza stabilna wersja),
- Android SDK 36,
- JDK 11,
- urządzenie lub emulator z Androidem API 26 lub nowszym.

## Uruchomienie

1. Sklonuj repozytorium:

   ```bash
   git clone https://github.com/Kajerkooo2/KL4_Lekcja7_RoomZadanie.git
   ```

2. Otwórz folder projektu w Android Studio.
3. Poczekaj na pobranie zależności Gradle.
4. Uruchom aplikację na emulatorze lub urządzeniu fizycznym, używając przycisku **Run**.

Możesz również uruchomić build z terminala:

```bash
./gradlew assembleDebug
```

Na systemie Windows:

```bat
gradlew.bat assembleDebug
```

## Struktura projektu

```text
app/src/main/java/com/example/mblkcj6/
├── Gwint.java                 # encja reprezentująca kartę
├── Deck.java                  # encja reprezentująca talię
├── DeckCardCrossRef.java      # relacja karta–talia
├── DeckWithCards.java         # talia razem z kartami
├── GwintDAO.java              # operacje bazodanowe
├── GwintDatabase.java         # konfiguracja Room
└── MainActivity.java          # główny ekran aplikacji
```

## Testy

Projekt zawiera katalogi dla testów jednostkowych i instrumentacyjnych:

- `app/src/test/` — testy lokalne JVM,
- `app/src/androidTest/` — testy uruchamiane na urządzeniu lub emulatorze.
