package com.plagesribeiro.pucantina;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoEntidade {
    private String idCarrinho, valorTotal, idUsuario;
    private List<Produto> produtos = new ArrayList<Produto>();

    @Override
    public String toString() {
        String resp = "IdCarrinho: "+idCarrinho;

        int i = 1;
        for(Produto produto : produtos){
            resp = resp +"\n     Produto "+ i+": ";
            resp = resp + "\n          "+produto.getNome();
            resp = resp +"\n          "+produto.getValor()+" reais";
            i++;
        }

        resp = resp + "\nValor Total: "+valorTotal;

        return resp;
    }

    public void CarrinhoEntidade() {}

    public void setIdUsuario(String idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario(){
        return this.idUsuario;
    }

    public String getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(String idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos){
        this.produtos = produtos;
    }

    public void addProduto(Produto produto , int qtProd){
        for(int i=0 ; i<qtProd ; i++){
            produtos.add(produto);
        }
    }

    public void removeProduto(Produto produto , int qtProd){
        for(int i=0 ; i<qtProd ; i++){
            produtos.remove(produto);
        }
    }
}
