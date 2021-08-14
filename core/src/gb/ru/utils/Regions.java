package gb.ru.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {

    public static TextureRegion[] split (TextureRegion region, int rows, int cols, int frames) {
        int width = region.getRegionWidth();
        int height = region.getRegionHeight();

        int x = region.getRegionX();
        int y = region.getRegionY();
        int tileHeight = height / rows;
        int tileWidth  = width / cols;

        int startX = x;

        TextureRegion[] tiles = new TextureRegion[frames];
        int frame = 0;
        for (int row = 0; row < rows; row++, y += tileHeight) {
            x = startX;
            for (int col = 0; col < cols || frame < frames; col++, x += tileWidth) {
                tiles[frame] = new TextureRegion(region.getTexture(), x, y, tileWidth, tileHeight);
                frame++;
            }
        }
        return tiles;
    }
}


