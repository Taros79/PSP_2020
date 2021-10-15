package dao.modelo.ModMovimientos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movimiento {

    @SerializedName("pp")
    private int pp;

    @SerializedName("generation")
    private Generation generation;

    @SerializedName("stat_changes")
    private List<Object> statChanges;

    @SerializedName("accuracy")
    private int accuracy;

    @SerializedName("contest_combos")
    private ContestCombos contestCombos;

    @SerializedName("priority")
    private int priority;

    @SerializedName("super_contest_effect")
    private SuperContestEffect superContestEffect;

    @SerializedName("type")
    private Type type;

    @SerializedName("effect_changes")
    private List<Object> effectChanges;

    @SerializedName("target")
    private Target target;

    @SerializedName("effect_entries")
    private List<EffectEntriesItem> effectEntries;

    @SerializedName("contest_type")
    private ContestType contestType;

    @SerializedName("past_values")
    private List<Object> pastValues;

    @SerializedName("names")
    private List<NamesItem> names;

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntriesItem> flavorTextEntries;

    @SerializedName("name")
    private String name;

    @SerializedName("damage_class")
    private DamageClass damageClass;

    @SerializedName("effect_chance")
    private Object effectChance;

    @SerializedName("id")
    private int id;

    @SerializedName("power")
    private int power;

    @SerializedName("contest_effect")
    private ContestEffect contestEffect;

    public int getPp() {
        return pp;
    }

    public Generation getGeneration() {
        return generation;
    }

    public List<Object> getStatChanges() {
        return statChanges;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public ContestCombos getContestCombos() {
        return contestCombos;
    }

    public int getPriority() {
        return priority;
    }

    public SuperContestEffect getSuperContestEffect() {
        return superContestEffect;
    }

    public Type getType() {
        return type;
    }

    public List<Object> getEffectChanges() {
        return effectChanges;
    }

    public Target getTarget() {
        return target;
    }

    public List<EffectEntriesItem> getEffectEntries() {
        return effectEntries;
    }

    public ContestType getContestType() {
        return contestType;
    }

    public List<Object> getPastValues() {
        return pastValues;
    }

    public List<NamesItem> getNames() {
        return names;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<FlavorTextEntriesItem> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public String getName() {
        return name;
    }

    public DamageClass getDamageClass() {
        return damageClass;
    }

    public Object getEffectChance() {
        return effectChance;
    }

    public int getId() {
        return id;
    }

    public int getPower() {
        return power;
    }

    public ContestEffect getContestEffect() {
        return contestEffect;
    }

    @Override
    public String toString() {
        return "PP --> " + pp +
                "\nPrecision --> " + accuracy +
                "\nPotencia --> " + power +
                "\nTipo --> " + type.getName();
    }
}