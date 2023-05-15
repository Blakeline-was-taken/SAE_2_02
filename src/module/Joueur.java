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
    public void deplacer(int[] parCoord){
        coord = parCoord;
    }

    /**
     * Mets à jour les coordonnées du joueur à celles de l'objet coordonable donné en paramètre.
     * @param parCoord Coordonable – les coordonnées
     */
    public void deplacer(Coordonable parCoord){
        coord = parCoord.getCoord();
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
