package com.plagesribeiro.pucantina.ui;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu extends Fragment {

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference().child("produto");

    int[] images={R.drawable.herera,R.drawable.costa,R.drawable.mata,R.drawable.degea,R.drawable.thibaut,R.drawable.vanpersie,R.drawable.oscar};

    public ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    public SimpleAdapter adapter;
    public List<Produto> produtos = new ArrayList<Produto>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Produto produto = new Produto();
                    produto.setNome(ds.child("nome").getValue().toString());
                    produto.setValor(ds.child("valor").getValue().toString());
                    produtos.add(produto);
                    produto = null;
                }
                Toast.makeText(getActivity(), produtos.get(0).getNome(), Toast.LENGTH_SHORT).show();
                HashMap<String, String> map = new HashMap<String, String>();

                for(int i = 0; i < produtos.size(); i++) {
                    map = new HashMap<String, String>();
                    map.put("Produto", produtos.get(i).getNome());
                    map.put("Image", Integer.toString(images[i]));

                    data.add(map);
                }

                String[] from={"Produto", "Image"};

                int[] to={R.id.nameTxt, R.id.imageView1};
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.listview_menu, from, to);
                ListView listView = getActivity().findViewById(R.id.listView_menu);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}