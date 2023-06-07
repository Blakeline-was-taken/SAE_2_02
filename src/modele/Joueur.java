package modele;

/**
 * La classe Joueur représente un joueur avec des coordonnées et de l'expérience.
 * Elle hérite de la classe abstraite Coordonable.
 */
public class Joueur extends Coordonable{
    private int exp;

    /**
     * Constructeur de la classe Joueur ne prenant aucun paramètre.
     * Le joueur est initialisé avec des coordonnées par défaut (0, 0) et une expérience de 0.
     */
    public Joueur(){
        super(new int[] {0, 0});
        exp = 0;
    }

    /**
     * Mets à jour les coordonnées du joueur avec les coordonnées données en paramètre.
     *
     * @param parCoord int[2] - Les nouvelles coordonnées du joueur.
     * @return int - La distance parcourue pour se déplacer jusqu'aux nouvelles coordonnées.
     */
    public int deplacer(int[] parCoord){
        int dist = Math.abs(parCoord[0] - coord[0]) + Math.abs(parCoord[1] - coord[1]);
        coord = parCoord;
        return dist;
    }

    /**
     * Mets à jour les coordonnées du joueur avec les coordonnées de l'objet Coordonable donné en paramètre.
     *
     * @param parCoord Coordonable - L'objet Coordonable contenant les nouvelles coordonnées du joueur.
     * @return int - La distance parcourue pour se déplacer jusqu'aux nouvelles coordonnées.
     */
    public int deplacer(Coordonable parCoord){
        int dist = distance(parCoord);
        coord = parCoord.getCoord();
        return dist;
    }

    /**
     * Ajoute de l'expérience au joueur.
     *
     * @param expToAdd int - Le montant d'expérience à ajouter au joueur.
     */
    public void addExp(int expToAdd){
        exp = exp + expToAdd;
    }

    /**
     * Renvoie une représentation textuelle de l'objet Joueur.
     *
     * @return String - Une chaîne de caractères indiquant les coordonnées et l'expérience du joueur.
     */
    public String toString() {
        return "Joueur - " + super.toString() + ", exp = " + exp;
    }

    /**
     * Renvoie l'expérience du joueur.
     *
     * @return int - Le montant d'expérience du joueur.
     */
    public int getExp(){
        return exp;
    }
}

