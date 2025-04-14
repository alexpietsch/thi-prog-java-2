
public class Main {

    public static void main(String[] args) {
        Position p1 = new Position(10, 10);
        Position p2 = new Position(12, 18);
        Position p3 = new Position(17, 16);
        Position p4 = new Position(14, 8);
        Position p5 = new Position(12, 7);
        
        Position[] arr = new Position[] {p1, p2, p3, p4, p5};
        
        double flaeche = .0;
        double summePunkteFlaeche = .0;
        for(int i = 0; i < arr.length -1; i++) {
        	summePunkteFlaeche += (arr[i].getX()*arr[i+1].getY() - arr[i+1].getX()*arr[i].getY());
        }
        summePunkteFlaeche += (arr[arr.length - 1].getX() * arr[0].getY()) - (arr[0].getX() * arr[arr.length - 1].getY());
        flaeche = Math.abs(summePunkteFlaeche) * 0.5;
     
        double cx = 0.0;
        double cy = 0.0;

        for (int i = 0; i < arr.length - 1; i++) {
            double common = (arr[i].getX() * arr[i+1].getY()) - (arr[i+1].getX() * arr[i].getY());
            cx += (arr[i].getX() + arr[i+1].getX()) * common;
            cy += (arr[i].getY() + arr[i+1].getY()) * common;
        }
        
        double common = (arr[arr.length - 1].getX() * arr[0].getY()) - (arr[0].getX() * arr[arr.length - 1].getY());
        cx += (arr[arr.length - 1].getX() + arr[0].getX()) * common;
        cy += (arr[arr.length - 1].getY() + arr[0].getY()) * common;

        cx = Math.abs(cx / (6 * flaeche));
        cy = Math.abs(cy / (6 * flaeche));
        
        System.out.println(cx+" "+cy);
        
    }

}