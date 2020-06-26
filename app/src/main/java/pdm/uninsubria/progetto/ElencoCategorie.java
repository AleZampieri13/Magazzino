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

public class ElencoCategorie extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Materiale> elenco = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_categorie);

        ListView listView;

        Intent intent = getIntent();
        Categoria elencoCategorie = (Categoria) intent.getSerializableExtra("pdm.uninsubria.progetto.Categoria");
        listView = (ListView) findViewById(R.id.listView);

        final ArrayList<String> categorie = elencoCategorie.getElenco();

        //Create Adapter
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,categorie);

        //assign adapter to listview
        listView.setAdapter(arrayAdapter);

        //add listener to listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String categoria = categorie.get(i);
                mDatabase.child("Materiali").addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String nome = (String) dataSnapshot.child("Nome").getValue();
                        String cat = (String) dataSnapshot.child("Categoria").getValue();
                        String codice = (String) dataSnapshot.child("Codice").getValue();
                        String descrizione = (String) dataSnapshot.child("Descrizione").getValue();
                        int quantita = Integer.parseInt((String) dataSnapshot.child("Quantita").getValue());
                        final int quantitaMinima = Integer.parseInt((String) dataSnapshot.child("QuantitaMinima").getValue());
                        if(cat.compareTo(categoria) == 0){
                            Materiale m = new Materiale(nome, codice, descrizione, cat, quantita, quantitaMinima);
                            System.out.println(nome);
                            elenco.add(m);
                        }
                        if(!elenco.isEmpty()){
                            Intent intent = new Intent(ElencoCategorie.this,
                                    ElencoMateriali.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("pdm.uninsubria.progetto.Materiale", elenco);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String nome = (String) dataSnapshot.child("Nome").getValue();
                        String cat = (String) dataSnapshot.child("Categoria").getValue();
                        String codice = (String) dataSnapshot.child("Codice").getValue();
                        String descrizione = (String) dataSnapshot.child("Descrizione").getValue();
                        int quantita = Integer.parseInt((String) dataSnapshot.child("Quantita").getValue());
                        final int quantitaMinima = Integer.parseInt((String) dataSnapshot.child("QuantitaMinima").getValue());
                        if(cat.compareTo(categoria) == 0){
                            Materiale m = new Materiale(nome, codice, descrizione, cat, quantita, quantitaMinima);
                            elenco.remove(m);
                        }
                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

            }
        });

    }

    public void cerca(View v){
        Intent intent = new Intent(ElencoCategorie.this,
                Cerca.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
