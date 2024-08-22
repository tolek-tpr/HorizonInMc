package pl.epsi.gui.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.*;
import pl.epsi.util.HorizonUtil;

import java.util.ArrayList;
import java.util.List;

public class ActiveQuestModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private TextRenderer tx = MinecraftClient.getInstance().textRenderer;
    private Quest quest = CustomPlayer.getInstance().getCurrentQuest();
    private Identifier skillPointLogo = new Identifier("horizoninmc", "navigator/skill_point_logo");
    private Identifier xpPointLogo = new Identifier("horizoninmc", "navigator/xp_point_logo");
    private Identifier guiTravelMarker = new Identifier("horizoninmc", "quests/gui_travel_marker");
    private Identifier stepCompleted = new Identifier("horizoninmc", "quests/quest_complete");

    public ActiveQuestModule(int x, int screenWidth, int screenHeight) {
        super(x, 86, screenWidth / 2, screenHeight, Text.literal("ActiveQuestModule"));
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = this.getX() + 70;
        int y = this.getY();
        MatrixStack matrixStack = context.getMatrices();
        String categoryName = HorizonUtil.getQuestCategoryName(quest.getCategory());

        // Quest name rendering
        matrixStack.push();
        matrixStack.translate(x, y, 0);
        matrixStack.scale(2.5F, 2.5F, 0);
        context.drawTextWithShadow(tx, quest.getName().getString().toUpperCase(), 0, 0, 0xc7b37b);
        matrixStack.pop();

        // Sub category + level rendering
        y += 10;
        context.drawTextWithShadow(tx, categoryName, x, y + 23, 0xc7b37b);
        context.drawTextWithShadow(tx, "|", x + tx.getWidth(categoryName) + 5, y + 23, 0x5e5d5c);
        int questOffset = tx.getWidth(categoryName) + 10 + tx.getWidth("|");
        context.drawTextWithShadow(tx, "Level " + quest.getMinLevel(), x + questOffset, y + 23, 0xc7b37b);

        // Description rendering
        int yDescOffset = 33 + tx.fontHeight;
        List<OrderedText> descriptionWrapped = tx.wrapLines(quest.getDescription(), getWidth());
        for (OrderedText t : descriptionWrapped) {
            context.drawTextWithShadow(tx, t, x, y + yDescOffset, 0xa29f9a);
            yDescOffset += tx.fontHeight + 2;
        }
        yDescOffset += 40;
        x -= 70;

        context.drawTextWithShadow(tx, "OBJECTIVE LOG", x, y + yDescOffset, 0xe3d7d7);
        context.drawHorizontalLine(x, x + width / 2, y + yDescOffset + 2 + tx.fontHeight, 0xffe3d7d7);

        int yStepOffset = yDescOffset + 12 + tx.fontHeight;
        for (QuestStep step : quest.steps) {
            if (step.getStatus() == QuestStatus.STATUS_COMPLETED) {
                RenderSystem.enableBlend();
                context.drawGuiTexture(stepCompleted, x, y + yStepOffset, 8, 8);
                RenderSystem.disableBlend();
                context.drawTextWithShadow(tx, step.getName(), x + 12, y + yStepOffset, 0x5e5d5c);
                yStepOffset += tx.fontHeight + 5;
                continue;
            }
            if (step instanceof TravelStep) {
                if (step.getStatus() != QuestStatus.STATUS_COMPLETED) {
                    RenderSystem.enableBlend();
                    context.drawGuiTexture(guiTravelMarker, x, y + yStepOffset, 8, 8);
                    RenderSystem.disableBlend();
                }
            }
            context.drawTextWithShadow(tx, step.getName(), x + 12, y + yStepOffset, 0xe3d7d7);
            yStepOffset += tx.fontHeight + 5;
        }

        context.drawTextWithShadow(tx, "REWARDS", x + width / 2 + 40, y + yDescOffset, 0xe3d7d7);
        context.drawHorizontalLine(x + width / 2 + 40, x + width, y + yDescOffset + 2 + tx.fontHeight, 0xffe3d7d7);

        int yRewardOffset = yDescOffset + 12 + tx.fontHeight;
        for (Reward reward : quest.rewards) {
            if (reward instanceof XpReward r) {
                // draw img
                context.drawGuiTexture(xpPointLogo, x + width / 2 + 40, y + yRewardOffset, 16, 24);
                context.drawTextWithShadow(tx, "+" + r.amount, x + width / 2 + 70, y + yRewardOffset, 0xe3d7d7);
                context.drawTextWithShadow(tx, "XP", x + width / 2 + 70, y + yRewardOffset + tx.fontHeight + 3, 0xe3d7d7);
                yRewardOffset += tx.fontHeight * 2 + 8;
            } else if (reward instanceof SkillPointReward r) {
                GL11.glEnable(GL11.GL_BLEND);
                context.drawGuiTexture(skillPointLogo, x + width / 2 + 40, y + yRewardOffset, 16, 24);
                GL11.glDisable(GL11.GL_BLEND);
                context.drawTextWithShadow(tx, "+" + r.amount, x + width / 2 + 70, y + yRewardOffset, 0xe3d7d7);
                context.drawTextWithShadow(tx, "Skill Points", x + width / 2 + 70, y + yRewardOffset + tx.fontHeight + 3, 0xe3d7d7);
                yRewardOffset += tx.fontHeight * 2 + 8;
            }
        }
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
