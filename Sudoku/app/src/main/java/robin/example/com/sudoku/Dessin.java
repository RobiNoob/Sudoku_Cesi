package robin.example.com.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dessin extends View implements View.OnTouchListener {
    ArrayList<Rectangle> rectangles;
    String chiffreSelect = "";
    Editable tab[][] = null;
    public static String grille = "";
    ArrayList<ArrayList<String>> tabLigne = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> tabCol = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> tabCarre = new ArrayList<ArrayList<String>>();
    public Dessin(Context context, @Nullable AttributeSet attrs) {
        super(context);
        this.setOnTouchListener(this);
        for (int i = 0; i < 9; i++) {
            tabLigne.add(new ArrayList<String>());
            tabCol.add(new ArrayList<String>());
            tabCarre.add(new ArrayList<String>());
        }
    }
    @Override
    public void onDraw(Canvas canvas) {
        if (tab == null) {
            tab = new Editable[9][9];
            for (int i = 0; i < 81; i++) {
                if (grille.charAt(i) == '0')
                    tab[i / 9][i % 9] = new Editable("", true);
                else tab[i / 9][i % 9] = new Editable(String.valueOf(grille.charAt(i)), false);
            }
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(1, 1, this.getWidth() - 1, this.getWidth() - 1, paint);
        paint.setColor(Color.BLACK);
        rectangles = new ArrayList<Rectangle>();
        for (int i = 1; i < 9; i++) {
            if (i % 3 == 0)
                paint.setStrokeWidth(5);
            else
                paint.setStrokeWidth(1);
            canvas.drawLine(i * ((this.getWidth() - 1) / 9), 0, i * ((this.getWidth() - 1) / 9), this.getWidth() - 1, paint);
            canvas.drawLine(0, i * ((this.getWidth() - 1) / 9), this.getWidth() - 1, i * ((this.getWidth() - 1) / 9), paint);
        }
        for (int i = 0; i < 9; i++) {
            tabLigne.get(i).clear();
            tabCol.get(i).clear();
            tabCarre.get(i).clear();
        }
        paint.setTextSize(50);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tab[i][j].type)
                    paint.setColor(Color.BLUE);
                else
                    paint.setColor(Color.BLACK);
                canvas.drawText(tab[i][j].valeur, ((((this.getWidth() - 1) / 9) * i) + (((this.getWidth() - 1) / 9) * (i + 1))) / 2, ((((this.getWidth() - 1) / 9) * j) + (((this.getWidth() - 1) / 9) * (j + 1))) / 2, paint);
                tabLigne.get(i).add(tab[i][j].valeur);
                tabCol.get(j).add(tab[i][j].valeur);
                tabCarre.get((i / 3) + (3 * (j / 3))).add(tab[i][j].valeur);
            }
        }
        for (int i = 0; i < 9; i++) {
            rectangles.add(new Rectangle((i * ((this.getWidth() - 1) / 9)) + 10, this.getWidth() + 20, (i * ((this.getWidth() - 1) / 9) + (this.getWidth() - 20) / 9) + 5, this.getWidth() + ((this.getWidth() - 20) / 9) + 20, String.valueOf(i + 1), chiffreSelect, canvas));
        }
        if (testVic()) {
            Toast.makeText(getContext(), "Victory", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(x + " " + y);
                if (x > 1 && x < this.getWidth() - 1 && y > 1 && y < this.getWidth()) {
                    if (tab[x / ((this.getWidth() - 1) / 9)][y / ((getWidth() - 1) / 9)].type)
                        tab[x / ((this.getWidth() - 1) / 9)][y / ((getWidth() - 1) / 9)].valeur = chiffreSelect;
                } else {
                    for (Rectangle rectangle : rectangles) {
                        if (rectangle.Compare(x, y)) {
                            if (chiffreSelect != rectangle.chiffre)
                                chiffreSelect = rectangle.chiffre;
                            else chiffreSelect = "";
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        this.invalidate();
        return false;
    }
    public Boolean testVic() {
        for (int i = 0; i < 9; i++) {
            if (tabLigne.get(i).contains(""))
                return false;
            Set<String> test = new HashSet<String>(tabLigne.get(i));
            if (test.size() < tabLigne.get(i).size())
                return false;
            if (tabCol.get(i).contains(""))
                return false;
            test.clear();
            test.addAll(tabCol.get(i));
            if (test.size() < tabCol.get(i).size())
                return false;
            if (tabCarre.get(i).contains(""))
                return false;
            test.clear();
            test.addAll(tabCarre.get(i));
            if (test.size() < tabCarre.get(i).size())
                return false;
        }
        return true;
    }
}
class Rectangle {
    int left, right, top, bottom;
    String chiffre;
    Canvas canvas;
    public Rectangle(int left, int top, int right, int bottom, String chiffre, String chiffreSelect, Canvas canvas) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.chiffre = chiffre;
        this.canvas = canvas;
        //System.out.println(left+" "+top+" "+right+" "+bottom);
        Paint paint = new Paint();
        if (chiffre.equals(chiffreSelect))
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        canvas.drawRect(left, top, right, bottom, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText(chiffre, (left + right) / 2, (bottom + top) / 2, paint);
    }
    public boolean Compare(int x, int y) {
        if (x > this.left && x < this.right) {
            if (y < this.bottom && y > this.top) {
                return true;
            }
        }
        return false;
    }
}
class Editable {
    String valeur;
    boolean type;
    public Editable(String valeur, boolean type) {
        this.valeur = valeur;
        this.type = type;
    }
}