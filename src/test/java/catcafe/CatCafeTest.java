package catcafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für CatCafe – hier prüfen wir, ob das Hinzufügen
 * und Suchen von Katzen korrekt funktioniert, und ob ungültige
 * Suchanfragen sauber mit Optional.empty() beantwortet werden.
 */
class CatCafeTest {

    private CatCafe cafe;

    @BeforeEach
    void setUp() {
        // Jedes Mal vor einem Test wird ein ganz neues cafe erzeugt
        cafe = new CatCafe();
    }

    @Test
    void testEmptyCafeHasZeroCats() {
        // Am Anfang enthalt unser Baum keine Knoten getCatCount() muss 0 geben
        assertEquals(0, cafe.getCatCount(),
            "Ein frisch angelegtes Café sollte keine Katzen haben");
    }

    @Test
    void testAddCatIncreasesCount() {
        // Wenn wir eine Katze hinzufugen wachst die Anzahl um eins
        cafe.addCat(new FelineOverLord("A", 1));
        assertEquals(1, cafe.getCatCount(),
            "Nach dem ersten addCat-Aufruf sollte count == 1 sein");
        // Ein zweiter Aufruf mit anderem Objekt erhoht erneut
        cafe.addCat(new FelineOverLord("B", 2));
        assertEquals(2, cafe.getCatCount(),
            "Nach zwei verschiedenen Katzen sollte count == 2 sein");
    }

    @Test
    void testAddDuplicateCatDoesNotIncreaseCount() {
        // Dasselbe Objekt zweimal hinzufugen darf die Baumgrosse nicht verandern
        FelineOverLord twin = new FelineOverLord("Twin", 4);
        cafe.addCat(twin);
        cafe.addCat(twin);
        assertEquals(1, cafe.getCatCount(),
            "Das Aufnehmen der gleichen Katze darf nicht doppelt gezählt werden");
    }

    @Test
    void testGetCatByNameFound() {
        // Wir legen eine Katze an und suchen genau diesen Namen
        FelineOverLord morticia = new FelineOverLord("Morticia", 3);
        cafe.addCat(morticia);

        // Das Optional soll nicht leer sein, wenn der Name existiert
        Optional<FelineOverLord> result = cafe.getCatByName("Morticia");
        assertTrue(result.isPresent(),
            "Bei vorhandenem Namen muss Optional.isPresent() true sein");
        // die zuruckgegebene Instanz muss gleich sein wie die eingefugte
        assertSame(morticia, result.get(),
            "Optional.get() muss das gleiche Objekt liefern, das wir hinzugefügt haben");
    }

    @Test
    void testGetCatByNameNotFound() {
        // Ein Name der nicht eingefugt wurde liefert ein leeres Optional
        cafe.addCat(new FelineOverLord("Fitzby", 5));
        Optional<FelineOverLord> result = cafe.getCatByName("Unbekannt");
        assertTrue(result.isEmpty(),
            "Suche nach nicht vorhandenem Namen muss Optional.empty() zurückgeben");
    }

    @Test
    void testGetCatByNameNullParam() {
        // auch bei null Optional.empty() liefern
        Optional<FelineOverLord> result = cafe.getCatByName(null);
        assertTrue(result.isEmpty(),
            "Bei null als Suchparameter darf kein Wert zurückgegeben werden");
    }

    @Test
    void testGetCatByWeightFound() {
        // Zwei Katzen mit unterschiedlichen Gewichten hinzufugen
        FelineOverLord light  = new FelineOverLord("LightCat", 2);
        FelineOverLord heavy  = new FelineOverLord("HeavyCat", 5);
        cafe.addCat(light);
        cafe.addCat(heavy);

        // Bei min=1 und max=3 muss die leichte Katze gefunden werden
        Optional<FelineOverLord> result = cafe.getCatByWeight(1, 3);
        assertTrue(result.isPresent(),
            "Leichte Katze im Bereich [1,3) soll gefunden werden");
        // prufen des Gewichts des gefundenen Objekts
        assertEquals(2, result.get().weight(),
            "Die gefundene Katze muss das Gewicht 2 haben");
    }

    @Test
    void testGetCatByWeightNotFound() {
        // Nur eine schwere Katze hinzufugen
        cafe.addCat(new FelineOverLord("Solo", 10));

        // wenn keine Katze im Bereich (1,5) liegt ergibt ein leeres Optional
        Optional<FelineOverLord> result = cafe.getCatByWeight(1, 5);
        assertTrue(result.isEmpty(),
            "Kein Treffer im Bereich [1,5) darf Optional.empty() sein");
    }

    @Test
    void testGetCatByWeightInvalidRange() {
        // Wenn maxWeight kleiner als minWeight ist wird keine Suche durchgeführt
        cafe.addCat(new FelineOverLord("X", 3));
        Optional<FelineOverLord> result = cafe.getCatByWeight(5, 2);
        assertTrue(result.isEmpty(),
            "Ungültiger Bereich (max < min) muss Optional.empty() zurückliefern");
    }

    @Test
    void testGetCatByWeightNegativeMin() {
        // Negativer wert ist ungultig
        cafe.addCat(new FelineOverLord("Y", 2));
        Optional<FelineOverLord> result = cafe.getCatByWeight(-1, 3);
        assertTrue(result.isEmpty(),
            "Negatives minWeight soll Optional.empty() liefern");
    }
}
