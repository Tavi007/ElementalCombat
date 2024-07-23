package Tavi007.ElementalCombat.client;

public class TextureData {

    private String name;
    private int posX;
    private int posY;

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
