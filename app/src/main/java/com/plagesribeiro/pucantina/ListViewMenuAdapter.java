package com.plagesribeiro.pucantina;

import com.google.firebase.storage.StorageReference;

public class ListViewMenuAdapter {
    private  byte[] image;
    private  String nome;
    private  String preco;

    public ListViewMenuAdapter() {

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public  String getNome() {
        return nome;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
    public  String getPreco() {
        return preco;
    }
}