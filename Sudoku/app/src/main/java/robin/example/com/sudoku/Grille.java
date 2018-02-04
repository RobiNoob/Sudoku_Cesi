package robin.example.com.sudoku;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Grille extends AppCompatActivity {

    Dessin dessin;
    Activity context;
    int grille;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grille);

        context = this;
        Bundle bundle = context.getIntent().getExtras();
        grille = bundle.getInt("grille");
        level = bundle.getInt("niveau");

        dessin = (Dessin) findViewById(R.id.grille);

        int file;

        if(level ==1)
            file = R.raw.premier;
        else if(level == 2)
            file = R.raw.deuxieme;
        else file = R.raw.troisieme;

        InputStream inputStream = this.getResources().openRawResource(file);

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        if (inputStream != null) {
            for (int i = 0; i <= grille; i++) {
                try {
                    dessin.grille = buffer.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
