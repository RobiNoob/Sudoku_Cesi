package robin.example.com.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button niveau1, niveau2, niveau3;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Séléctionner un niveau");

        niveau1 = findViewById(R.id.buttonNiveau1);
        niveau1.setOnClickListener(this);

        niveau2 = findViewById(R.id.buttonNiveau2);
        niveau2.setOnClickListener(this);

        niveau3 = findViewById(R.id.buttonNiveau3);
        niveau3.setOnClickListener(this);

        context = this;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, AffichageChoixGrille.class);
        Bundle bundle = new Bundle();

        switch (v.getId()){
            case R.id.buttonNiveau1:
                bundle.putInt("niveau", 1);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;

            case R.id.buttonNiveau2:
                bundle.putInt("niveau", 2);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;

            case R.id.buttonNiveau3:
                bundle.putInt("niveau", 3);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
        }
    }
}
