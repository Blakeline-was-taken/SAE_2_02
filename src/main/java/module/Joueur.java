package module;

public class Joueur extends Coordonable{
    private int exp;

    /**
     * Constructeur de la classe Joueur ne prenant aucun paramètre.
     */
    public Joueur(){
        super(new int[] {0, 0});
        exp = 0;
    }

    /**
     * Mets à jour les coordonnées du joueur à celles données en paramètre.
     * @param parCoord int[2] – les coordonnées
     */
    public int deplacer(int[] parCoord){
        int dist = Math.abs(parCoord[0] - coord[0]) + Math.abs(parCoord[1] - coord[1]);
        coord = parCoord;
        return dist;
    }

    /**
     * Mets à jour les coordonnées du joueur à celles de l'objet coordonable donné en paramètre.
     * @param parCoord Coordonable – les coordonnées
     */
    public int deplacer(Coordonable parCoord){
        int dist = distance(parCoord);
        coord = parCoord.getCoord();
        return dist;
    }

    /**
     * Ajoute de l'expérience au joueur.
     * @param expToAdd int – Le montant d'expérience à ajouter au joueur
     */
    public void addExp(int expToAdd){
        exp = exp + expToAdd;
    }

    public String toString() {
        return "Joueur - " + super.toString() + ", exp = " + exp;
    }

    public int getExp(){
        return exp;
    }
}
