package pl.epsi.player.inventory;

public class SpearSettings {

    private int damageMultiplier;
    private int chargeSpeedMultiplier;
    private int dischargeDamageMultiplier;
    private int energizedDurationMultiplier;
    private int resonatorBlastDamageMultiplier;
    private int criticalStrikeDamageMultiplier;
    private int silentStrikeDamageMultiplier;
    private int powerAttackDamageMultiplier;

    private SpearSettings() {}

    public static SpearSettings create() { return new SpearSettings(); }
    public SpearSettings setDamage(int i) {
        this.damageMultiplier = i;
        return this;
    }
    public SpearSettings setChargeSpeed(int i) {
        this.chargeSpeedMultiplier = i;
        return this;
    }
    public SpearSettings setDischargeDamage(int i) {
        this.dischargeDamageMultiplier = i;
        return this;
    }
    public SpearSettings setEnergizedDuration(int i) {
        this.energizedDurationMultiplier = i;
        return this;
    }
    public SpearSettings setResonatorBlastDamage(int i) {
        this.resonatorBlastDamageMultiplier = i;
        return this;
    }
    public SpearSettings setCriticalStrikeDamage(int i) {
        this.criticalStrikeDamageMultiplier = i;
        return this;
    }
    public SpearSettings setSilentStrikeDamage(int i) {
        this.silentStrikeDamageMultiplier = i;
        return this;
    }
    public SpearSettings setPowerAttackDamage(int i) {
        this.powerAttackDamageMultiplier = i;
        return this;
    }
    public int getDamageMultiplier() {
        return damageMultiplier;
    }

    public int getChargeSpeedMultiplier() {
        return chargeSpeedMultiplier;
    }

    public int getDischargeDamageMultiplier() {
        return dischargeDamageMultiplier;
    }

    public int getEnergizedDurationMultiplier() {
        return energizedDurationMultiplier;
    }

    public int getResonatorBlastDamageMultiplier() {
        return resonatorBlastDamageMultiplier;
    }

    public int getCriticalStrikeDamageMultiplier() {
        return criticalStrikeDamageMultiplier;
    }

    public int getSilentStrikeDamageMultiplier() {
        return silentStrikeDamageMultiplier;
    }

    public int getPowerAttackDamageMultiplier() {
        return powerAttackDamageMultiplier;
    }

}
