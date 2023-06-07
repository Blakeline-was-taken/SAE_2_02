package modele;

import java.util.Arrays;

/**
 * La classe Quete représente une quête avec des coordonnées, des préconditions, une durée, de l'expérience et un intitulé.
 * Elle hérite de la classe abstraite Coordonable.
 */
public class Quete extends Coordonable {
    private final int[][] cond;
    private final int duree;
    private final int exp;
    private final String intitule;

    /**
     * Constructeur de la classe Quete, prenant en paramètre toutes les informations de quête contenues dans les fichiers scénarios,
     * à l'exception du numéro de quête (celui-ci est uniquement utilisé par la classe Scénario).
     *
     * @param parCoord int[2] - Les coordonnées de la quête.
     * @param parCond int[2][2] - Les préconditions de la quête.
     * @param parDuree int - La durée de la quête.
     * @param parExp int - L'expérience donnée par la quête. Si l'identifiant est 0 dans la classe scénario,
     *                cette valeur sert de cap d'expérience à avoir pour battre la quête.
     * @param parIntitule String - L'intitulé de la quête.
     */
    public Quete(int[] parCoord, int[][] parCond, int parDuree, int parExp, String parIntitule){
        super(parCoord);
        cond = parCond;
        duree = parDuree;
        exp = parExp;
        intitule = parIntitule;
    }

    /**
     * Renvoie une représentation textuelle de l'objet Quete.
     *
     * @return String - Une chaîne de caractères indiquant l'intitulé, les coordonnées, la durée, l'expérience et les préconditions de la quête.
     */
    public String toString() {
        return "Quete \"" + intitule + "\" - " + super.toString() + " - duree: " + duree +
                " - exp: " + exp + " - preconditions: " + Arrays.deepToString(cond);
    }

    /**
     * Renvoie les préconditions de la quête.
     *
     * @return int[][] - Les préconditions de la quête.
     */
    public int[][] getCond() {
        return cond;
    }

    /**
     * Renvoie la durée de la quête.
     *
     * @return int - La durée de la quête.
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Renvoie l'expérience donnée par la quête.
     *
     * @return int - L'expérience donnée par la quête.
     */
    public int getExp() {
        return exp;
    }

    /**
     * Renvoie l'intitulé de la quête.
     *
     * @return String - L'intitulé de la quête.
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Vérifie si l'objet Quete est égal à un autre objet.
     *
     * @param obj Object - L'objet à comparer avec la quête.
     * @return boolean - `true` si les objets sont égaux, `false` sinon.
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Quete q2){
            return Arrays.deepEquals(cond, q2.getCond()) && duree == q2.getDuree() && exp == q2.getExp() && intitule.equals(q2.getIntitule());
        }
        return false;
    }
}
