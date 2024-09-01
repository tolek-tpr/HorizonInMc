package pl.epsi.player.quest;

public class SkillPointReward extends Reward {

    public int amount;

    public SkillPointReward(int amount) {
        super(SkillPointReward.class);
        this.amount = amount;
    }

}
