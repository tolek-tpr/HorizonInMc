package pl.epsi.render;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CustomTextRenderer {

    public static class FontProps {
        public String name;
        public int bitmapHeight = 16;
        public int bitmapWidth = 232;
        public int baseLine = 10;
        public int baseHeight = 16;
        public int fallbackWidth = 10;
        public int fallbackHeight = 16;
        public int spaceWidth = 5;
        public final Map<Character, ArrayList<Integer>> characters = new TreeMap<>();
    }

    private final static Map<Identifier, CustomTextRenderer> renderers = new TreeMap<>();

    final private Identifier font;
    final private FontProps props;

    private int horizontalSpacing = 1;
    private int verticalSpacing = 1;
    private Integer textHeight = null;

    public enum HorizontalAlign { LEFT, RIGHT, CENTER }
    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;

    public enum VerticalAlign { TOP, BOTTOM, CENTER }
    private VerticalAlign verticalAlign = VerticalAlign.TOP;

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

    public static CustomTextRenderer of(String modId, String fontPath) {
        Identifier font = new Identifier(modId, fontPath + ".png");

        if (!renderers.containsKey(font)) {
            Yaml yaml = new Yaml(new Constructor(FontProps.class, new LoaderOptions()));
            try {
                Optional<Path> o = FabricLoader.getInstance().getModContainer(modId).get()
                        .findPath("assets/" + modId + "/" + fontPath + ".yml");
                if (o.isPresent()) {
                    FontProps props = yaml.load(new FileInputStream(o.get().toFile()));
                    props.characters.forEach((k, v) -> {
                        if (v.size() < 3) props.characters.get(k).add(props.fallbackWidth);
                        if (v.size() < 4) props.characters.get(k).add(props.fallbackHeight);
                    });
                    renderers.put(font, new CustomTextRenderer(font, props));
                } else { throw new Exception("Could not find " + fontPath + ".yml"); }
            } catch (Exception e) {
                e.printStackTrace();
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

    public CustomTextRenderer setTextHeight(int height) {
        this.textHeight = height;
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
        if (ch == ' ') return new Cursor(cursor.x + props.spaceWidth, cursor.y, cursor.count + 1);
        if (!props.characters.containsKey(ch)) return cursor;
        ArrayList<Integer> position = props.characters.get(ch);
        int posX = position.get(0);
        int posY = position.get(1);
        int w = position.get(2);
        int h = position.get(3);
        int th = textHeight == null ? position.get(3) : textHeight;
        context.drawTexture(font, cursor.x, cursor.y, w, th,
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