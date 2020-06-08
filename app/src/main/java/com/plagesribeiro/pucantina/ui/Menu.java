package com.plagesribeiro.pucantina.ui;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.plagesribeiro.pucantina.ListViewMenuAdapter;

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
    private List<ListViewMenuAdapter> mList = new ArrayList<>();
    private ListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        init(root);
        addList();
        adapter();
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void init(View view){
        mRecycleview = (RecyclerView) view.findViewById(R.id.recyclierView);
    }

    private void addList(){
        ListViewMenuAdapter itemAdapter = new ListViewMenuAdapter();
        itemAdapter.setImage(R.drawable.coxinha);
        itemAdapter.setNome("Tomato");
        itemAdapter.setPreco("Tomato");
        mList.add(itemAdapter);
    }

    private void adapter(){
        mAdapter = new ListAdapter(mList, getActivity());
        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}