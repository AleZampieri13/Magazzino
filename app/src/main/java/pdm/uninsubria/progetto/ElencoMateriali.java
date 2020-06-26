package pdm.uninsubria.progetto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ElencoMateriali extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> nomeMateriali = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_materiali);

        ListView listView;

        Intent intent = getIntent();
        final ArrayList<Materiale> elencoMateriali = (ArrayList<Materiale>) intent.getSerializableExtra("pdm.uninsubria.progetto.Materiale");
        listView = (ListView) findViewById(R.id.listView);


        for(Materiale m : elencoMateriali){
            nomeMateriali.add(m.getNome());
        }

        //Create Adapter
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,nomeMateriali);

        //assign adapter to listview
        listView.setAdapter(arrayAdapter);

        //add listener to listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String n = nomeMateriali.get(i);
                mDatabase.child("Materiali").addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Materiale m = null;
                        String nome = (String) dataSnapshot.child("Nome").getValue();
                        String codice = (String) dataSnapshot.child("Codice").getValue();
                        String descrizione = (String) dataSnapshot.child("Descrizione").getValue();
                        int quantita = Integer.parseInt((String) dataSnapshot.child("Quantita").getValue());
                        final String categoria = (String) dataSnapshot.child("Categoria").getValue();
                        final int quantitaMinima = Integer.parseInt((String) dataSnapshot.child("QuantitaMinima").getValue());
                        if(nome.compareTo(n) == 0){
                            m = new Materiale(nome, codice, descrizione, categoria, quantita, quantitaMinima);
                        }
                        if(m != null){
                            Intent intent = new Intent(ElencoMateriali.this,
                                    Specifiche.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("pdm.uninsubria.progetto.Materiale", m);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) { }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

            }
        });
    }

    public void cerca(View v) {
        Intent intent = new Intent(ElencoMateriali.this,
                Cerca.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
