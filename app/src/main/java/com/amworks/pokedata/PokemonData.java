package com.amworks.pokedata;

public class PokemonData {

    private String name;
    private int height;
    private int id;
    private String type;
    private String imageURL;
    private int weight;
    private String pokeURL;

    //blank constructor
    public PokemonData() {
    }

    public PokemonData(String name, int height, int id, String type, String imageURL, int weight) {  //Another constructor
        this.name = name;
        this.height = height;
        this.id = id;
        this.type = type;
        this.imageURL = imageURL;
        this.weight = weight;
    }

    public void setPokeURL(String pokeURL) {
        this.pokeURL = pokeURL;
    }

    public String getPokeURL() {
        return pokeURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getWeight() {
        return weight;
    }
}
