package code.chapter14;

public class TreeProcessor {

    public static void main(String[] args) {
        Tree root = new Tree("Mary", 22,
                new Tree("Emily", 20,
                        new Tree("Alan", 50, null, null),
                        new Tree("Georgie", 23, null, null)
                ),
                new Tree("Tian", 29,
                        new Tree("Raoul", 23, null, null),
                        null
                )
        );

        // found = 23
        System.out.println(lookup("Raoul", -1, root));
        // not found = -1
        System.out.println(lookup("Jeff", -1, root));

        Tree newTree = fUpdate("Jeff", 80, root);
        // found = 80
        System.out.println(lookup("Jeff", -1, newTree));

        Tree oldTree = update("Jim", 40, root);
        // t was not altered by fUpdate, so Jeff is not found = -1
        System.out.println(lookup("Jeff", -1, oldTree));
        // found = 40
        System.out.println(lookup("Jim", -1, oldTree));

        Tree newTree2 = fUpdate("Jeff", 80, root);
        // found = 80
        System.out.println(lookup("Jeff", -1, newTree2));
        // newTree2 built from t altered by update() above, so Jim is still present = 40
        System.out.println(lookup("Jim", -1, newTree2));
    }
    public static int lookup(String key, int defaultVal, Tree tree) {
        if (tree == null) return defaultVal;
        else if (key.equals(tree.getKey())) return tree.getAge();
        return lookup(key, defaultVal, key.compareTo(tree.getKey()) < 0 ? tree.getLeft() : tree.getRight());
    }

    /**
     * THis update method will modify the existing Tree, which means all users will feel this change
     */
    public static Tree update(String key, int newAge, Tree tree) {
        //If root not exits, create a new tree and set this as new root
        if (tree == null)
            tree = new Tree(key, newAge, null, null);
        else if (key.equals(tree.getKey()))
            tree.setAge(newAge);
        else if (key.compareTo(tree.getKey()) < 0)
            tree.setLeft(update(key, newAge, tree.getLeft()));
        else
            tree.setRight(update(key, newAge, tree.getRight()));
        return tree;
    }

    /**
     * This method generate new node in the one side of old binary tree, and will not break existing tree structure
     */
    public static Tree fUpdate(String key, int newAge, Tree tree) {
        return tree == null ?
                new Tree(key, newAge, null, null) :
                key.equals(tree.getKey()) ?
                        new Tree(key, newAge, tree.getLeft(), tree.getRight()) :
                        key.compareTo(tree.getKey()) < 0 ?
                                new Tree(key, newAge, fUpdate(key, newAge, tree.getLeft()), tree.getRight()) :
                                new Tree(key, newAge, tree.getLeft(), fUpdate(key, newAge, tree.getRight()));
    }
}
