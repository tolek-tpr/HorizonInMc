package pl.epsi.file;

import com.google.gson.*;
import pl.epsi.player.quest.Reward;
import pl.epsi.player.quest.SkillPointReward;
import pl.epsi.player.quest.XpReward;

import java.lang.reflect.Type;

public class RewardAdapter implements JsonDeserializer<Reward> {
    @Override
    public Reward deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String className = obj.get("type").getAsString();

        if (className.equals(XpReward.class.getName())) {
            return new XpReward(obj.get("amount").getAsInt());
        } else if (className.equals(SkillPointReward.class.getName())) {
            return new SkillPointReward(obj.get("amount").getAsInt());
        }

        return null;
    }
}
