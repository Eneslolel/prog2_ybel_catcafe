package tree;

import java.util.Objects;

/**
 * Traversiert den Baum in Post-Order (links–rechts–Wurzel)
 * und baut den String durch einfache Konkatenation auf.
 */
public class PostOrderVisitor<T extends Comparable<T>> implements TreeVisitor<T> {

    @Override
    public String visit(Empty<T> node) {
        // Leerer Teilbaum liefert leeren String
        return "";
    }

    @Override
    public String visit(Node<T> node) {
        // Zuerst linken Teilbaum
        String leftStr = Objects.requireNonNull(node)
            .leftChild()
            .accept(this);
        // dann rechten Teilbaum
        String rightStr = node.rightChild().accept(this);
        // zum Schluss den aktuellen Knoten
        String current = node.data().toString();
        return leftStr + rightStr + current;
    }
}
