package module;

public class Quete extends Coordonable {
    private final int[][] cond;
    private final int duree;
    private final int exp;
    private final String intitule;
    private boolean validee;

    public Quete(int[] parCoord, int[][] parCond, int parDuree, int parExp, String parIntitule){
        super(parCoord);
        cond = parCond;
        duree = parDuree;
        exp = parExp;
        intitule = parIntitule;
        validee = false;
    }

    public int[][] getCond() {
        return cond;
    }

    public int getDuree() {
        return duree;
    }

    public int getExp() {
        return exp;
    }

    public String getIntitule() {
        return intitule;
    }

    public boolean isValidee() {
        return validee;
    }
}
