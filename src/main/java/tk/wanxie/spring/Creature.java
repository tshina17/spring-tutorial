package tk.wanxie.spring;

public interface Creature {

    public void speak();

    default void eat() {
        System.out.println("Chewy!!!");
    }

    default void beforeInit() {
        System.out.println("---------------------------------");
        System.out.println("I'm a class of " + this.getClass());
    }

    default void afterInit() {
        System.out.println("I'm not a " + this.getClass() + " anymore!");
        System.out.println("---------------------------------");
        System.out.println("");
    }

}
