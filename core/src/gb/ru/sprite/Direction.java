package gb.ru.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public enum Direction {
    NORTH(new Vector2(0, 1)), SOUTH(new Vector2(0, -1)),
    EAST(new Vector2(-1, 0)), WEST(new Vector2(1, 0)),
    NOTDIRECTION(new Vector2(0, 0));
    private Vector2 vector;

    Direction(Vector2 vector) {
        this.vector = vector;
    }

    public Vector2 getVector() {
        return vector;
    }

    public static Direction directionOnKeycode(int keycode) {
        if (keycode == Input.Keys.A) {
            return EAST;
        }
        if (keycode == Input.Keys.W) {
            return NORTH;
        }
        if (keycode == Input.Keys.D) {
            return WEST;
        }
        if (keycode == Input.Keys.S) {
            return SOUTH;
        }
        return NOTDIRECTION;

    }
}
