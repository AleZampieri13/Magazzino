package pdm.uninsubria.progetto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Prenota extends AppCompatActivity {
    private Materiale materiale;
    private TextView quantita_txt;
    private int q ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenota);
        Intent intent = getIntent();
        materiale = (Materiale) intent.getSerializableExtra("pdm.uninsubria.progetto.Materiale");
        q =  materiale.getQuantita();
        quantita_txt = findViewById(R.id.quantita);
        quantita_txt.setText(""+q);
        TextView titolo = findViewById(R.id.titolo);
        titolo.setText(materiale.getNome());
    }

    public void diminuisci(View v){
        if(q-1>=0){
            q--;
            quantita_txt.setText(""+q);
        }
    }

    public void aggiungi(View v){
        q++;
        quantita_txt.setText(""+q);
    }

    public void prenota(View v){
        //Getting content for email
        String email = "zampus.99@gmail.com";
        String subject = "Prenotazione materiale";
        String message = "Materiale: " + materiale.getNome() +
                "/n Codice: " + materiale.getCodice();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        if(q > materiale.getQuantita()){
            int quantitaDaOrdinare = q - materiale.getQuantita();
            message += "/n Quantita prenotata disponibile in magazzino: " + materiale.getQuantita()
                + "/n Quantita prenonata da ordinare: " + quantitaDaOrdinare;
            mDatabase.child("Materiali").child(""+materiale.getCodice()).child("Quantita").setValue(""+0);
            materiale.setQuantita(0);
        }
        else{
            message += "/n Quantita: " + q;
            mDatabase.child("Materiali").child(""+materiale.getCodice()).child("Quantita").setValue(""+(materiale.getQuantita()-q));
            materiale.setQuantita(materiale.getQuantita()-q);
        }

        //Creating SendMail object
        InviaMail sm = new InviaMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();

        if(materiale.getQuantita() < materiale.getQuantitaMinima()){
            message = "Materiale: " + materiale.getNome() +
                    "/n Codice: " + materiale.getCodice() +
                    "/n Da ordinare " + (materiale.getQuantitaMinima()-materiale.getQuantita()) + " pezzi";
            subject = "Ordine materiale";
            sm = new InviaMail(this, email, subject, message);
            sm.execute();
        }
    }

}
