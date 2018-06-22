package com.example.icg_dominicana.pribandoenreportero.Objects;

public class Report {

    public String url_foto;
    public String description;
    public double latitud;
    public double longitud;
//    public int position;

    public Report() {
    }

    public Report(String url_foto, String description, double latitud, double longitud) {
        this.url_foto = url_foto;
        this.description = description;
        this.latitud = latitud;
        this.longitud = longitud;
//        this.position = position;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

//    public int getPosition() {
//        return position;
//    }
//
//    public void setPosition(int position) {
//        this.position = position;
//    }
}
