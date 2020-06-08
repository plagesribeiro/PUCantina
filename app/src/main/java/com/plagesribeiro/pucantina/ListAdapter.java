package com.plagesribeiro.pucantina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter<ItemAdapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemAdapter> mList;
    private Context mContext;
    public ListAdapter(List<ItemAdapter> list, Context context){
        super();
        mList = list;
        mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_menu, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ItemAdapter itemAdapter = mList.get(position);
        ((ViewHolder) viewHolder).mTv_name.setText(listViewMenuAdapter.getNome());
        ((ViewHolder) viewHolder).mTv_preco.setText(listViewMenuAdapter.getPreco());
        ((ViewHolder) viewHolder).mImg.setImageResource(listViewMenuAdapter.getImage());
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTv_name;
        public TextView mTv_preco;
        public ImageView mImg;
        public ViewHolder(View itemView) {
            super(itemView);
            mTv_name = (TextView) itemView.findViewById(R.id.textView_NomeProduto);
            mTv_name = (TextView) itemView.findViewById(R.id.textView_PrecoProduto);
            mImg = (ImageView) itemView.findViewById(R.id.imageView_fotoProduto);
        }
    }
}
