package com.plagesribeiro.pucantina;

public class ListViewMenuAdapter {
    private static int image;
    private static String nome;
    private static String preco;

    public ListViewMenuAdapter() {

    }

    public static int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public static String getNome() {
        return nome;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
    public static String getPreco() {
        return preco;
    }
}