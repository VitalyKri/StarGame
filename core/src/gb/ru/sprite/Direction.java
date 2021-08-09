package gb.ru.sprite;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    NORTH(new Vector2(0, 1)), SOUTH(new Vector2(0, -1)),
    EAST(new Vector2(-1, 0)), WEST(new Vector2(1, 0)),
    CENTER(new Vector2(0, 0));
    private Vector2 vector;

    Direction(Vector2 vector) {
        this.vector = vector;
    }

    public Vector2 getVector() {
        return vector;
    }

    public static Direction directionOnKeycode(int keycode) {
        if (keycode == 29) {
            return EAST;
        }
        if (keycode == 51) {
            return NORTH;
        }
        if (keycode == 32) {
            return WEST;
        }
        if (keycode == 47) {
            return SOUTH;
        }
        throw new NullPointerException("Не верный код кнопки");

    }
}
