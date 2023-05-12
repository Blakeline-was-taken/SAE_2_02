package module;

public class Joueur extends Coordonable{
    private int exp;

    public Joueur(){
        super(new int[] {0, 0});
        exp = 0;
    }

    public int getExp(){
        return exp;
    }

    public void deplacer(int[] parCoord){
        coord = parCoord;
    }

    public void addExp(int expToAdd){
        exp = exp + expToAdd;
    }
}
