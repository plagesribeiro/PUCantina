package com.plagesribeiro.pucantina;

import android.util.Base64;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoEntidade{
    private String idPedido, valorTotal, horaPedido, idUsuario;
    private List<Produto> produtos = new ArrayList<Produto>();

    public PedidoEntidade() {
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm 'Dia: ' dd/mm/yyyy");
        Date date = new Date(System.currentTimeMillis());
        this.horaPedido = formatter.format(date);
    }

    @Override
    public String toString() {
        byte[] data = Base64.decode(idUsuario , Base64.DEFAULT);
        String emailUsuario = new String(data , Charset.defaultCharset());

        String resp = "Email usuario: "+emailUsuario+"\nHora Pedido: "+horaPedido;

        int i = 1;
        for(Produto produto : produtos){
            resp = resp +"\n     Produto "+ i+": ";
            resp = resp + "\n          "+produto.getNome();
            resp = resp +"\n          "+produto.getValor()+" reais";
            i++;
        }

        resp = resp + "\nValor Total: "+this.getValorTotal();

        return resp;
    }

    public void setIdUsuario(String idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario(){
        return this.idUsuario;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getValorTotal() {
        int soma = 0;

        for(Produto produto : produtos){
            soma = soma + Integer.parseInt(produto.getValor());
        }

        return Integer.toString(soma);
    }

    public void setValorTotal() {
        int soma = 0;

        for(Produto produto : produtos){
            soma = soma + Integer.parseInt(produto.getValor());
        }

        this.valorTotal = Integer.toString(soma);
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

    public void fromCarrinho(CarrinhoEntidade carrinho , String id){
        this.idUsuario = carrinho.getIdUsuario();
        this.produtos = carrinho.getProdutos();
        this.valorTotal = carrinho.getValorTotal();
        this.idPedido = id;
    }

}
