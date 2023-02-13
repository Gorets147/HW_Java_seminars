package HW_Java_seminars.seminar_6;

public class Point2d {
    private int x = 0, y = 0;
    private String sst = "0:0";
    private String[] strIn = new String[2];

    Point2d(){
        x = 0;
        y = 0;
    }
    
    Point2d(int inY, int inX){
        this.x = inX;
        this.y = inY;
    }

    public String GetStr(int inY, int inX){
        this.x = inX;
        this.y = inY;
        return Integer.toString(y) + ":" + Integer.toString(x);
    }

    public int GetY(String adr){
        this.sst = adr;
        strMass();        
        return Integer.parseInt(this.strIn[0]);        
    }
    
    public int GetX(String adr){
        this.sst = adr;
        strMass();        
        return Integer.parseInt(this.strIn[1]);
    }

    private void strMass(){
        this.strIn = this.sst.split(":");
    }
}
