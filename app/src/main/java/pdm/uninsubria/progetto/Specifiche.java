package pdm.uninsubria.progetto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Specifiche extends AppCompatActivity {
    private Materiale materiale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifiche);

        Intent intent = getIntent();
        materiale = (Materiale) intent.getSerializableExtra("pdm.uninsubria.progetto.Materiale");

        TextView titolo = (TextView)findViewById(R.id.titoloMateriale);
        TextView codice = (TextView)findViewById(R.id.codiceMateriale);
        TextView quantita = (TextView)findViewById(R.id.quantitaMateriale);
        TextView descrizione = (TextView)findViewById(R.id.descrizioneMateriale);

        titolo.setText(materiale.getNome().toUpperCase());
        codice.setText("Codice: " + materiale.getCodice().toUpperCase());
        quantita.setText("Quantita: "+materiale.getQuantita());
        descrizione.setText(materiale.getDescrizione());
    }

    public void prenotazione(View v){
        Intent intent = new Intent(Specifiche.this,
                Prenota.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("pdm.uninsubria.progetto.Materiale", materiale);
        startActivity(intent);
    }

    public void cerca(View v) {
        Intent intent = new Intent(Specifiche.this,
                Cerca.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}