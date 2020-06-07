package com.plagesribeiro.pucantina;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoEntidade{
    private String idPedido, valorTotal, horaPedido;
    private List<Produto> produtos = new ArrayList<Produto>();

    @Override
    public String toString() {
        String resp = "IdPedido: "+idPedido +"\nHora Pedido: "+horaPedido;

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

    public void PedidoEntidade() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        horaPedido = formatter.format(date);
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido =  horaPedido;
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
