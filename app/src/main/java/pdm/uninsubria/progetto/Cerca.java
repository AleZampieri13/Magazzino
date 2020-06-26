package pdm.uninsubria.progetto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cerca extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    Context context;
    ArrayList<String> elencoNomi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca);
        context = this;
    }

    public void ricerca(View v){
        final EditText cerca_txt = findViewById(R.id.cerca_txt);
        final String ricercato = cerca_txt.getText().toString();
        elencoNomi = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Materiali").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                Materiale m = null;
                final String nome = (String) dataSnapshot.child("Nome").getValue();
                final String codice = (String) dataSnapshot.child("Codice").getValue();
                final String descrizione = (String) dataSnapshot.child("Descrizione").getValue();
                final int quantita = Integer.parseInt((String) dataSnapshot.child("Quantita").getValue());
                final String categoria = (String) dataSnapshot.child("Categoria").getValue();
                final int quantitaMinima = Integer.parseInt((String) dataSnapshot.child("QuantitaMinima").getValue());
                if(nome.toLowerCase().compareTo(ricercato.toLowerCase()) == 0){
                    elencoNomi.add(nome);
                }
                if(!elencoNomi.isEmpty()){
                    listView = findViewById(R.id.listView);;
                    arrayAdapter=new ArrayAdapter(context,android.R.layout.simple_list_item_1,elencoNomi);
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        Materiale m;
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final String nome = elencoNomi.get(i);
                            if(ricercato.toLowerCase().compareTo(nome.toLowerCase()) == 0){
                                m = new Materiale(nome, codice, descrizione, categoria, quantita, quantitaMinima);
                                    }
                                    if(m != null){
                                        Intent intent = new Intent(Cerca.this,
                                                Specifiche.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("pdm.uninsubria.progetto.Materiale", m);
                                        startActivity(intent);
                                        listView.setAdapter(null);
                                        elencoNomi = null;
                                    }
                                }
                    });

                }
                else{
                    chiudiTastiera();
                    Toast.makeText(Cerca.this,"Nessun elemento trovato",Toast.LENGTH_SHORT).show();
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

    private void chiudiTastiera() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
