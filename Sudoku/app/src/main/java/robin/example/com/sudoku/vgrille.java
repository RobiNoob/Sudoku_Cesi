package robin.example.com.sudoku;

public class vgrille {

    double level;
    int num;
    int done;

    public vgrille(double level, int num) {
        this.level = level;
        this.num = num;
        this.done = (int) (Math.random()*100);
    }
}
