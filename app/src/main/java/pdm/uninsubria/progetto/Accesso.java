package pdm.uninsubria.progetto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Accesso extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public <LogInActivity> void login(View view){
        EditText utente_in = findViewById(R.id.utente_in);
        final String utente = utente_in.getText().toString().trim();
        EditText password_in = findViewById(R.id.password_in);
        String password = password_in.getText().toString();
        TextView errore_txt = findViewById(R.id.errore_txt);

        mFirebaseAuth.signInWithEmailAndPassword(utente, password)
                .addOnCompleteListener(Accesso.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase.child("Categorie").addChildEventListener(new ChildEventListener() {
                                final Categoria categorie = new Categoria();
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    String categoria = (String) dataSnapshot.child("Nome").getValue();
                                    categorie.aggiungiElenco(categoria);
                                    System.out.println(categoria);
                                    Intent intent = new Intent(Accesso.this,
                                            ElencoCategorie.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("pdm.uninsubria.progetto.Categoria", categorie);
                                    startActivity(intent);
                                }
                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                    String categoria = (String) dataSnapshot.child("Nome").getValue();
                                    categorie.rimuoviElenco(categoria);
                                }
                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
                                @Override
                                public void onCancelled(DatabaseError databaseError) { }
                            });

                        } else {
                            AlertDialog.Builder builder = new
                                    AlertDialog.Builder(Accesso.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle("Errore")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

}
