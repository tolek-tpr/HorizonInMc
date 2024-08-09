package pl.epsi;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.epsi.blocks.HorizonBlocks;
import pl.epsi.event.EventImpl;
import pl.epsi.events.MainMenuKeybindListener;
import pl.epsi.events.TravelToMarkerRenderer;
import pl.epsi.events.TravelToUpdateDetector;
import pl.epsi.items.HorizonItems;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.*;
import pl.epsi.render.SubRenderer;
import pl.epsi.render.SubtitleRenderer;
import pl.epsi.util.HorizonUtil;

import java.util.ArrayList;

public class HorizonInMc implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("horizoninmc");
	public static final String MOD_ID = "horizoninmc";
	public ArrayList<EventImpl> events = new ArrayList<>();
	public static final SubRenderer subtitleRenderer = new SubRenderer();

	@Override
	public void onInitialize() {
		HorizonItems.initialize();
		HorizonBlocks.initialize();
		LOGGER.info("Hello Fabric world!");

		events.add(new MainMenuKeybindListener());
		events.add(new TravelToUpdateDetector());
		events.add(new TravelToMarkerRenderer());
		events.add(subtitleRenderer);

		events.forEach(e -> e.setEnabled(true));

		Quest test = new Quest(Text.literal("name"), Text.literal("desc"), 4, QuestCategory.MAIN);
		QuestStep step = new QuestStep(test, Text.literal("step name"), Text.literal("desc name"));
		step.setObjective(new TravelToObjective(50, 64, 50, 2, step));
		test.addStep(step);
		test.setConsumer((e) -> { System.out.println("COMPPLETED"); });
		CustomPlayer.getInstance().setQuest(test);
		QuestManager.getInstance().availableQuests.put(QuestCategory.MAIN, HorizonUtil.asArray(test));
	}

}