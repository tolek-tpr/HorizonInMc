package pl.epsi.file;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import net.minecraft.text.Text;
import org.joml.Vector3d;
import pl.epsi.player.quest.QuestStatus;
import pl.epsi.player.quest.QuestStep;
import pl.epsi.player.quest.TravelStep;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class QuestStepAdapter implements JsonDeserializer<QuestStep> {

    @Override
    public QuestStep deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Gson gson = new Gson();
        String className = obj.get("type").getAsString();

        if (className.equals(TravelStep.class.getName())) {
            JsonArray points = obj.getAsJsonArray("points");
            Type pointsListType = new TypeToken<LinkedList<Vector3d>>() {}.getType();
            Type statusType = new TypeToken<QuestStatus>() {}.getType();

            LinkedList<Vector3d> pointsDeserialized = gson.fromJson(points, pointsListType); // D
            int currentPoint = obj.get("currentPoint").getAsInt(); // D
            int detectRadius = obj.get("detectRadius").getAsInt(); // D
            boolean isMajor = obj.get("isMajor").getAsBoolean(); // D
            String name = obj.get("name").getAsString(); // D
            String description = obj.get("description").getAsString(); // D
            QuestStatus status = gson.fromJson(obj.get("status"), statusType); // D

            TravelStep step = new TravelStep(Text.literal(name), Text.literal(description), detectRadius);
            step.setPoints(pointsDeserialized);
            step.setCurrentPoint(currentPoint);
            step.setMajor(isMajor);
            step.setStatus(status);

            return step;
        }

        return null;
    }
}
