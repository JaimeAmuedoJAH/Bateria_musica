package com.jah.bateria_musica;

public class Cancion {

    private String nombreCancion;
    private int cancion;

    public Cancion(String nombreCancion, int cancion) {
        this.nombreCancion = nombreCancion;
        this.cancion = cancion;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public int getCancion() {
        return cancion;
    }

    public void setCancion(int cancion) {
        this.cancion = cancion;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "nombreCancion='" + nombreCancion + '\'' +
                ", cancion=" + cancion +
                '}';
    }
}
