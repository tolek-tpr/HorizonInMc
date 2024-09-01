package pl.epsi.file;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import pl.epsi.player.quest.Quest;
import pl.epsi.player.quest.QuestCategory;
import pl.epsi.player.quest.QuestStep;
import pl.epsi.player.quest.Reward;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The thing that allows the game to save and load things
 *  Also, this should be server side xD
 */
public class FileManager {

    private final static String PREFIX = "horizon";
    private final static String SETTINGS_FILE_NAME = PREFIX + "/settings.json";
    private final static String CURRENT_QUESTS_FILE_NAME = PREFIX + "/current_quest.json";
    private final static String ALL_QUESTS_FILE_NAME = PREFIX + "/quests.json";
    private final static String INVENTORY_FILE_NAME = PREFIX + "/inventory.json";
    private final Gson gson;

    public FileManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(QuestStep.class, new QuestStepAdapter());
        builder.registerTypeAdapter(Reward.class, new RewardAdapter());
        gson = builder.create();

        new File("horizon").mkdir();
    }

    public void save(QuestInfo questInfo, HashMap<QuestCategory, ArrayList<Quest>> allQuests, SettingsInfo settingsInfo) {
        try {
            FileWriter writer = new FileWriter(CURRENT_QUESTS_FILE_NAME, StandardCharsets.UTF_8);
            gson.toJson(questInfo, writer);
            writer.flush();
            writer.close();
            writer = new FileWriter(ALL_QUESTS_FILE_NAME, StandardCharsets.UTF_8);
            gson.toJson(allQuests, writer);
            writer.flush();
            writer.close();
            writer = new FileWriter(SETTINGS_FILE_NAME, StandardCharsets.UTF_8);
            gson.toJson(settingsInfo, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QuestInfo loadCurrentQuest() {
        try (FileReader reader = new FileReader(CURRENT_QUESTS_FILE_NAME, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, QuestInfo.class);
        } catch (IOException e) {
            System.out.println("File not found!");
            return null;
        } catch (JsonIOException e) {
            System.out.println("Json Error");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SettingsInfo loadSettings() {
        try (FileReader reader = new FileReader(SETTINGS_FILE_NAME, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, SettingsInfo.class);
        } catch (IOException e) {
            System.out.println("Settings file not found!");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<QuestCategory, ArrayList<Quest>> loadAvailableQuests() {
        try (FileReader reader = new FileReader(ALL_QUESTS_FILE_NAME, StandardCharsets.UTF_8)) {
            Type t = new TypeToken<HashMap<QuestCategory, ArrayList<Quest>>>() {}.getType();
            return gson.fromJson(reader, t);
        } catch (IOException e) {
            System.out.println("Quests file not found!");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
