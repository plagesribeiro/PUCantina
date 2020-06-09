package com.plagesribeiro.pucantina;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String idUsuario;
    private List<Produto> mList = new ArrayList<>();
    private Context mContext;
    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

    public ListAdapter(Context context, String idUsuario) {
        this.idUsuario = idUsuario;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final Produto produto = mList.get(position);
        Glide.with(mContext)
                .load(produto.getUrlImagem())
                .into(((ViewHolder) viewHolder).mImg);
        ((ViewHolder) viewHolder).mTv_name.setText(produto.getNome());
        ((ViewHolder) viewHolder).mTv_preco.setText(produto.getValor());
        ((ViewHolder) viewHolder).mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nomeCarrinho = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Carrinho";
                        String idCarrinho = Base64.encodeToString(nomeCarrinho.getBytes(), Base64.DEFAULT).replaceAll("(\n|\r)", "");


                        CarrinhoEntidade carrinho = dataSnapshot.child("carrinho").child(idCarrinho).getValue(CarrinhoEntidade.class);
                        carrinho.addProduto(produto,1);
                        banco.child("carrinho").child(idCarrinho).setValue(carrinho);
                        carrinho = null;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(Produto produto) {
        mList.add(produto);
        notifyItemInserted(mList.size() - 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTv_name;
        public TextView mTv_preco;
        public ImageView mImg;
        public Button mBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv_name = (TextView) itemView.findViewById(R.id.textView_NomeProduto);
            mTv_preco = (TextView) itemView.findViewById(R.id.textView_PrecoProduto);
            mImg = (ImageView) itemView.findViewById(R.id.imageView_fotoProduto);
            mBtn = itemView.findViewById(R.id.btn_id);
        }
    }
}