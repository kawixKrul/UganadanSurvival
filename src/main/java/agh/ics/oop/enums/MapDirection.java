package agh.ics.oop.enums;

import agh.ics.oop.model.Vector2d;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
            case NORTHWEST -> "Północny zachód";
            case NORTHEAST -> "Północny wschód";
            case SOUTHWEST -> "Południowy zachód";
            case SOUTHEAST ->  "Południowy wschód";
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case SOUTHEAST -> new Vector2d(1, -1);
        };
    }

    private MapDirection intToDirection(int rotation) {
        return switch (rotation) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalArgumentException(rotation + " is not a valid rotation");
        };
    }

    public MapDirection next(int rotation) {
        return intToDirection((rotation + this.ordinal()) % 8);
    }
}
