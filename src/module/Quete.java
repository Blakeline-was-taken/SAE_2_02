package module;

public class Quete extends Coordonable {
    private final int[][] cond;
    private final int duree;
    private final int exp;
    private final String intitule;
    private boolean validee;

    /**
     * Constructeur de la classe quête, prenant en paramètre toutes les informations de quêtes contenues dans les
     * fichiers scénarios, à l'exception du numéro de quête. (celui-ci est uniquement utilisé par la classe Scénario)
     *
     * @param parCoord int[2] – Les coordonnées de la quête
     * @param parCond int[2][2] – Les préconditions de la quête
     * @param parDuree int – La durée de la quête
     * @param parExp int – L'expérience donnée par la quête. Si l'identifiant est 0 dans la classe scénario, cette
     *               valeur sert de cap d'expérience à avoir pour battre la quête
     * @param parIntitule String – L'intitulé de la quête
     */
    public Quete(int[] parCoord, int[][] parCond, int parDuree, int parExp, String parIntitule){
        super(parCoord);
        cond = parCond;
        duree = parDuree;
        exp = parExp;
        intitule = parIntitule;
        validee = false;
    }

    @Override
    public String toString() {
        return "Quete \"" + intitule + "\" - " + super.toString() + " - duree: " + duree +
                " - exp: " + exp + " - preconditions: " + Arrays.deepToString(cond) + " - validee: " + validee;
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
