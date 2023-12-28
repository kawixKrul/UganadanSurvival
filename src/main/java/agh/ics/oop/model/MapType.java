package agh.ics.oop.model;


import java.util.Objects;
public enum MapType {
    NORMAL, TOXIC;

    public static MapType fromString(String variant) {
        if (Objects.equals(variant, "normal")) {
            return MapType.NORMAL;
        } else return MapType.TOXIC;
    }
}
