package Tavi007.ElementalCombat.client.gui;

public class TextureData {

    private final String name;
    private final int posX;
    private final int posY;

    public TextureData(String name, int posX, int posY) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
