package gb.ru.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.math.Rect;
import gb.ru.utils.Regions;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    private boolean destroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth()/(float) regions[frame].getRegionHeight();
        setWidth(height*aspect);
    }

    public Sprite(TextureRegion region,int rows,int cols,int frames,int frame) {
        regions = Regions.split(region,rows,cols,frames);
        this.frame = frame;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void update(float delta) {

    }

    public void resize(Rect worldBounds) {

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public float getScale() {
        return scale;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    public void destroy(){
        destroyed = true;
    }
    public void flushDestroy(){
        destroyed = false;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
