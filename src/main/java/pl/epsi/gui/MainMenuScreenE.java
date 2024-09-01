package pl.epsi.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import pl.epsi.file.FileManager;
import pl.epsi.file.QuestInfo;
import pl.epsi.file.SettingsInfo;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.QuestManager;

public abstract class MainMenuScreenE extends Screen {
    protected MainMenuScreenE(Text title) {
        super(title);
    }

    public abstract int getMenuSelected();

    public abstract void handleSubGroupChange(int keyCode);

    public abstract boolean isSubGroupEntered();

    @Override
    public void close() {
        QuestInfo info = new QuestInfo(CustomPlayer.getInstance().getCurrentQuest());
        FileManager fm = new FileManager();

        fm.save(info, QuestManager.getInstance().getAvailableQuests(), new SettingsInfo());

        this.client.setScreen(null);
    }

}
