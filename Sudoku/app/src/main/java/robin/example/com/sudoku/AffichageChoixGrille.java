package robin.example.com.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AffichageChoixGrille extends AppCompatActivity{

    Activity context;
    ListView listGrille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_choix_grille);

        context = this;
        final Bundle bundle = context.getIntent().getExtras();
        this.setTitle("Sudoku Niveau "+bundle.getInt("niveau"));

        ArrayList<vgrille> items = new ArrayList<vgrille>();

        for(int i=1; i <= 100; i++){
            items.add(new vgrille((double) bundle.getInt("niveau"), i));
        }

        listGrille = findViewById(R.id.listGrille);
        MyAdapter adapter = new MyAdapter(this, items);

        listGrille.setAdapter(adapter);

        listGrille.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final vgrille item = (vgrille) parent.getAdapter().getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choix Niveau");
                builder.setMessage(item.level+"---->"+item.done+"%");
                builder.setPositiveButton("Continuez",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(context, Grille.class);
                        intent.putExtra("niveau", bundle.getInt("niveau"));
                        intent.putExtra("grille", position);
                        context.startActivity(intent);
                    }
                });
                builder.show();
            }
        });

    }
}
