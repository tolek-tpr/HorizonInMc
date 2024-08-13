package pl.epsi.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CustomTextRenderer {

    public static class FontProps {
        private String name;
        private int bitmapHeight = 16;
        private int bitmapWidth = 232;
        private int baseLine = 10;
        private int baseHeight = 10;
        private int fallbackWidth = 16;
        private int fallbackHeight = 10;
        private final Map<Character, ArrayList<Integer>> characters = new TreeMap<>();
    }

    private final static Map<Identifier, CustomTextRenderer> renderers = new TreeMap<>();

    final private Identifier font;
    final private FontProps props;

    private int horizontalSpacing = 1;
    private int verticalSpacing = 1;

    public enum HorizontalAlign { LEFT, RIGHT, CENTER }
    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;

    public enum VerticalAlign { TOP, BOTTOM, CENTER }
    private VerticalAlign verticalAlign = VerticalAlign.TOP;

    public class ItemToRender {
        public final int x;
        public final int y;
        public final int width;
        public final int height;
        public final int posX;
        public final int posY;

        public ItemToRender(int x, int y, int width, int height, int posX, int posY) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.posX = posX;
            this.posY = posY;
        }
    }

    public class Cursor {
        public final int x;
        public final int y;
        public final int count;

        public Cursor(int x, int y, int count) {
            this.x = x;
            this.y = y;
            this.count = count;
        }
    }

    private CustomTextRenderer(Identifier font, FontProps props) {
        this.font = font;
        this.props = props;
    }

    public static CustomTextRenderer of(String modId, String fontName) {
        Identifier font = new Identifier(modId, fontName + ".png");
        if (!renderers.containsKey(font)) {
            Yaml yaml = new Yaml(new Constructor(FontProps.class, new LoaderOptions()));
            try {
                FontProps props = yaml.load(new FileInputStream(fontName + ".yml"));
                renderers.put(font, new CustomTextRenderer(font, props));
            } catch (Exception e) {
                System.err.println("Font file '" + fontName ".yml' not found");
            }
        }
        return renderers.get(font);
    }

    public CustomTextRenderer setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        return this;
    }

    public CustomTextRenderer setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        return this;
    }

    public CustomTextRenderer setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
        return this;
    }

    public CustomTextRenderer setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
        return this;
    }

    public int renderChar(DrawContext context, char ch, int x, int y) {
        return renderChar(context, ch, new Cursor(x, y, 0)).count;
    }

    public Cursor renderChar(DrawContext context, char ch, Cursor cursor) {
        if (!props.characters.containsKey(ch)) return cursor;
        ArrayList<Integer> position = props.characters.get(ch);
        int posX = position.get(0);
        int posY = position.get(1);
        int w = position.get(2) == null ? props.fallbackWidth : position.get(2);
        int h = position.get(3) == null ? props.fallbackHeight : position.get(3);
        context.drawTexture(font, cursor.x, cursor.y, w, h,
                posX, posY, w,
                h, props.bitmapWidth, props.bitmapHeight);
        return new Cursor(cursor.x + w + horizontalSpacing, cursor.y, cursor.count + 1);
    }

    public int renderLine(DrawContext context, String text, int x, int y) {
        return renderLine(context, text, new Cursor(x, y, 0)).count;
    }

    public Cursor renderLine(DrawContext context, String text, Cursor cursor) {
        for (int i = 0; i < text.length(); ++i) {
            cursor = renderChar(context, text.charAt(i), cursor);
        }
        return cursor;
    }

    public int renderRegion(DrawContext context, String text, int x, int y, int width, int height) {
        return renderRegion(context, text, new Cursor(x, y, 0), width, height).count;
    }

    public Cursor renderRegion(DrawContext context, String text, Cursor initialCursor, int width, int height) {
        Cursor cursor = new Cursor(initialCursor.x, initialCursor.y, initialCursor.count);
        for (int i = 0; i < text.length(); ++i) {
            final char ch = text.charAt(i);
            if (ch == '\n' || cursor.x > initialCursor.x + width) {
                cursor = new Cursor(cursor.x, cursor.y + props.baseHeight + verticalSpacing, cursor.count);
                if (ch == '\n') continue;
            }
            if (cursor.x == initialCursor.x && ch == ' ') {
                cursor = new Cursor(cursor.x, cursor.y, cursor.count + 1);
                continue;
            }
            if (cursor.y > initialCursor.y + height) return cursor;
            cursor = renderChar(context, ch, cursor);
        }
        return cursor;
    }

}