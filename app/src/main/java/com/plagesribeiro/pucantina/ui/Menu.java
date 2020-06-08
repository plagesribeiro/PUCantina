package com.plagesribeiro.pucantina.ui;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.plagesribeiro.pucantina.ListAdapter;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;
import com.plagesribeiro.pucantina.listViewMenuAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu extends Fragment {

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference().child("produto");
    StorageReference storageRef ;
    int[] images = {R.drawable.herera,R.drawable.costa,R.drawable.mata,R.drawable.degea,R.drawable.thibaut,R.drawable.vanpersie,R.drawable.oscar};
    public ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    public SimpleAdapter adapter;
    public List<Produto> produtos = new ArrayList<Produto>();
    private RecyclerView mRecycleview;
    private List<listViewMenuAdapter> mList = new ArrayList<>();
    private ListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        addList();
        adapter();
        storageRef  = FirebaseStorage.getInstance().getReference();

        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Produto produto = new Produto();
                    produto = ds.getValue(Produto.class);
                    produtos.add(produto);
                    produto = null;
                }

                HashMap<String, String> map = new HashMap<String, String>();

                File localFile = null;
                try {
                    localFile = File.createTempFile("images", "jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StorageReference islandRef;
                for(int i = 0; i < produtos.size(); i++) {
                    islandRef = storageRef.child("images/"+produtos.get(i).getNome()+".jpg");

                    islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getActivity(), taskSnapshot.toString(), Toast.LENGTH_SHORT).show();
                            // Local temp file has been created
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
                            // Handle any errors
                        }
                    });
                }

                /*for(int i = 0; i < produtos.size(); i++) {
                    storageRef.child("images/"+produtos.get(i).getNome()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(getActivity(), "Ta fazeno foto", Toast.LENGTH_SHORT).show();
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
                            // Handle any errors
                        }
                    });
                }*/


                /*for(int i = 0; i < produtos.size(); i++) {
                    map = new HashMap<String, String>();
                    map.put("Produto", produtos.get(i).getNome());
                    map.put("Image", Integer.toString(images[i]));

                    data.add(map);
                }

                String[] from={"Produto", "Image"};

                int[] to={R.id.textView_NomeProduto, R.id.imageView_fotoProduto};

                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.listview_menu, from, to);
                ListView listView = getActivity().findViewById(R.id.listView_menu);
                listView.setAdapter(adapter);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(View view){
        mRecycleview = view.findViewById(R.id.recyclierView);
    }

    private void addList(){
        listViewMenuAdapter itemAdapter = new listViewMenuAdapter();
        itemAdapter.setImage(R.drawable.coxinha);
        itemAdapter.setNome("Tomato");
        itemAdapter.setPreco("Tomato");
        mList.add(itemAdapter);
    }

    private void adapter(){


        mAdapter = new ListAdapter(mList, getActivity());
        mRecycleview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}