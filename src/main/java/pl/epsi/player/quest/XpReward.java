package pl.epsi.player.quest;

public class XpReward extends Reward {

    public int amount;

    public XpReward(int amount) {
        super(XpReward.class);
        this.amount = amount;
    }

}
