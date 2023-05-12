package module;

public class Coordonable {
    protected int[] coord;

    protected Coordonable(int[] parCoord){
        coord = parCoord;
    }

    public int distance(Coordonable coord2){
        return Math.abs(coord[0]-coord2.getCoord()[0])+Math.abs(coord[1]-coord2.getCoord()[1]);
    }

    public int[] getCoord(){
        return coord;
    }
}
