package catcafe;

import tree.InOrderVisitor;
import tree.PostOrderVisitor;

import java.util.Optional;

/** Starter for the cat-café task. */
public class Main {
    /**
     * And go.
     *
     * @param args command line parameters, not used
     */
    public static void main(String... args) {
        CatCafe cafe = new CatCafe();

        cafe.addCat(new FelineOverLord("Miss Chief Sooky", 2));
        cafe.addCat(new FelineOverLord("Gwenapurr Esmeralda", 3));
        cafe.addCat(new FelineOverLord("Morticia", 3));
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));

        System.out.println("Es schnurren " + cafe.getCatCount() + " Samtpfötchen.");
        Optional<FelineOverLord> optionalCat;

        //Suche nach einer Katze im Gewichtsbereich (3,4)
        optionalCat = cafe.getCatByWeight(3, 4);
        // nur ausfuhren wenn Optional nicht leer ist
        optionalCat.ifPresent(c->System.out.println("Gewicht [3,4]: " + c));

        optionalCat = cafe.getCatByName("Morticia");
        optionalCat.ifPresent(c -> System.out.println("Name 'Morticia': " + c));

        optionalCat = cafe.getCatByName("Miss Chief Sooky");
        optionalCat.ifPresent(c->System.out.println("Name 'Miss Chief Sooky': " + c));

        // In-Order-Traversal ausgeben
        String inOrder = cafe.accept(new InOrderVisitor<>());
        System.out.println("In-Order Traversal: " + inOrder);

        // Post-Order-Traversal ausgeben
        String postOrder = cafe.accept(new PostOrderVisitor<>());
        System.out.println("Post-Order Traversal: " + postOrder);
    }
}
