package pl.epsi;

import net.fabricmc.api.ModInitializer;

import net.minecraft.text.Text;
import org.joml.Vector3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.epsi.blocks.HorizonBlocks;
import pl.epsi.event.EventImpl;
import pl.epsi.events.MainMenuKeybindListener;
import pl.epsi.events.TravelToMarkerRenderer;
import pl.epsi.items.HorizonItems;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.*;
import pl.epsi.render.SubRenderer;
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
		events.add(new TravelToMarkerRenderer());
		events.add(subtitleRenderer);
		events.add(QuestListener.getInstance());

		events.forEach(e -> e.setEnabled(true));

		Quest test = new Quest(Text.literal("name"), Text.literal("desc"), 4, QuestCategory.MAIN);
		Quest test2 = new Quest(Text.literal("Shadow From the Past"), Text.literal("desc"), 5, QuestCategory.MAIN);
		Quest test3 = new Quest(Text.literal("name"), Text.literal("desc"), 6, QuestCategory.MAIN);
		Quest test4 = new Quest(Text.literal("name"), Text.literal("desc"), 7, QuestCategory.MAIN);
		Quest t3st = new Quest(Text.literal("name1"), Text.literal("desc"), 4, QuestCategory.MAIN);
		Quest test1 = new Quest(Text.literal("name2"), Text.literal("desc"), 4, QuestCategory.MAIN);
		Quest t3st2 = new Quest(Text.literal("name3"), Text.literal("desc"), 4, QuestCategory.MAIN);
		Quest test5 = new Quest(Text.literal("side"), Text.literal("desc"), 4, QuestCategory.SIDE);
		Quest test6 = new Quest(Text.literal("errands"), Text.literal("desc"), 4, QuestCategory.ERRANDS);

		test1.setStatus(QuestStatus.STATUS_COMPLETED);
		t3st2.setStatus(QuestStatus.STATUS_COMPLETED);
		TravelStep step = new TravelStep(Text.literal("step name"), Text.literal("desc name"), 2);

		step.addPoint(new Vector3d(50.5, 64.5, 50.5));
		step.addPoint(new Vector3d(60.5, 64.5, 60.5));
		step.addPoint(new Vector3d(70.5, 64.5, 70.5));

		CustomPlayer.getInstance().setCurrentQuest(test5);
		test.steps.add(step);
		test.rewards.add(new XpReward(28000));
		test.rewards.add(new SkillPointReward(2));
	}

}