package module;

public abstract class Coordonable {
    protected int[] coord;

    /**
     * Constructeur de la classe abstraite Coordonable.
     * @param parCoord int[2] – Coordonnées de la classe coordonable
     */
    protected Coordonable(int[] parCoord){
        coord = parCoord;
    }

    /**
     * Renvoie la distance entre cet objet coordonable et un autre.
     * @param coord2 Coordonable – L'objet avec qui mesurer la distance
     * @return int – La distance entre les deux objets
     */
    public int distance(Coordonable coord2){
        return Math.abs(coord[0]-coord2.getCoord()[0])+Math.abs(coord[1]-coord2.getCoord()[1]);
    }

    public int[] getCoord(){
        return coord;
    }
}
