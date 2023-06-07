package modele;

/**
 * La classe abstraite Coordonable représente un objet ayant des coordonnées.
 */
public abstract class Coordonable {
    protected int[] coord;

    /**
     * Constructeur de la classe abstraite Coordonable.
     *
     * @param parCoord int[2] - Les coordonnées de l'objet Coordonable.
     */
    protected Coordonable(int[] parCoord){
        coord = parCoord;
    }

    /**
     * Renvoie la distance entre cet objet Coordonable et un autre objet Coordonable.
     *
     * @param coord2 Coordonable - L'objet avec lequel mesurer la distance.
     * @return int - La distance entre les deux objets.
     */
    public int distance(Coordonable coord2){
        return Math.abs(coord[0]-coord2.getCoord()[0])+Math.abs(coord[1]-coord2.getCoord()[1]);
    }

    /**
     * Renvoie une représentation textuelle de l'objet Coordonable.
     *
     * @return String - Une chaîne de caractères indiquant les coordonnées de l'objet.
     */
    public String toString() {
        return "Localisé au point ( " + coord[0] + " ; " + coord[1] + " )";
    }

    /**
     * Renvoie les coordonnées de l'objet Coordonable.
     *
     * @return int[] - Les coordonnées de l'objet.
     */
    public int[] getCoord(){
        return coord;
    }
}
