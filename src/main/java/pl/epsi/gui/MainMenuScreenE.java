package pl.epsi.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class MainMenuScreenE extends Screen {
    protected MainMenuScreenE(Text title) {
        super(title);
    }
    public abstract int getMenuSelected();
}
