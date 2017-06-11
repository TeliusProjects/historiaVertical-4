package com.example.admin.prova;

/**
 * Created by admin on 08/06/2017.
 */
public class ImagenHome {

    private String url_perfil;
    private String url_imagen;
    private String username;

    public ImagenHome(String url_perfil, String url_imagen, String username) {
        this.url_perfil = url_perfil;
        this.url_imagen = url_imagen;
        this.username = username;
    }

    public String getUrl_perfil() {
        return url_perfil;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public String getUsername() {
        return username;
    }
}

