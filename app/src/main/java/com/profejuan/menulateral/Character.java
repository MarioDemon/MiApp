package com.profejuan.menulateral;

public class Character {
    //Variables que tiene la lista
    private String NumeroID;//Numero de distrito
    private String name;//Numero de energia 1
    private String status;//Numero de energia 2
    private String species;//Numero de energia 3

//----------------------------------------------------------------------------------//

    public Character(String NumeroID, String name, String status, String species) {

        this.NumeroID = NumeroID;
        this.name = name;
        this.status = status;
        this.species = species;

    }

    //Devolucion de las variables
    public String getNumeroID() {return NumeroID;}

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }


}
