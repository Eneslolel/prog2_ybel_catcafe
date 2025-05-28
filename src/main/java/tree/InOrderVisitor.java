package tree;

import java.util.Objects;

/**
 * Traversiert den Baum in In-Order (links–Wurzel–rechts)
 * und baut den String durch einfache Konkatenation auf.
 */
public class InOrderVisitor<T extends Comparable<T>> implements TreeVisitor<T> {

    @Override
    public String visit(Empty<T> node) {
        // Leerer Teilbaum liefert leeren String
        return "";
    }

    @Override
    public String visit(Node<T> node) {
        // Zuerst linken Teilbaum besuchen
        String leftStr = Objects.requireNonNull(node)
            .leftChild()
            .accept(this);
        // dann aktuellen Knoten (toString des gespeicherten T)
        String current = node.data().toString();
        // und zuletzt rechten Teilbaum
        String rightStr = node.rightChild().accept(this);
        return leftStr + current + rightStr;
    }
}
