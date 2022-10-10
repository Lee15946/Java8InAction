package code.chapter14;

public class Tree {
    private String key;
    private int age;
    private Tree left;
    private Tree right;

    public Tree(String key, int age, Tree left, Tree right) {
        this.key = key;
        this.age = age;
        this.left = left;
        this.right = right;
    }

    public String getKey() {
        return key;
    }

    public int getAge() {
        return age;
    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public void setRight(Tree right) {
        this.right = right;
    }
}
