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
import pl.epsi.file.FileManagerImpl;
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
		events.add(new FileManagerImpl());

		events.forEach(e -> e.setEnabled(true));
	}

}