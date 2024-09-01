package pl.epsi.player.quest;

public abstract class Reward {

    private String type;

    public Reward(Class c) {
        this.type = c.getName();
    }

}
