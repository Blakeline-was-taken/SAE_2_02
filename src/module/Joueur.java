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
     * Ajoute de l'expérience au joueur.
     * @param expToAdd int – Le montant d'expérience à ajouter au joueur
     */
    public void addExp(int expToAdd){
        exp = exp + expToAdd;
    }

    public int getExp(){
        return exp;
    }
}
