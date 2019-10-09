public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }
    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        double r_2 = (this.xxPos - p.xxPos) * (this.xxPos - p.xxPos)+
                (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos);
        return java.lang.Math.sqrt(r_2);
    }
    public double calcForceExertedBy(Planet p){
        return G*this.mass*p.mass/(this.calcDistance(p)*this.calcDistance(p));
    }
    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - xxPos;
        if(dx == 0) {
            return 0;
        }
        return this.calcForceExertedBy(p) * dx / this.calcDistance(p);
    }
    public double calcForceExertedByY(Planet p){
        double dy = p.yyPos - yyPos;
        if(dy == 0) {
            return 0;
        }
        return this.calcForceExertedBy(p) * dy / this.calcDistance(p);
    }
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double sum = 0;
        /**
        for(int i = 0; i < allPlanets.length; i += 1){
            sum += this.calcForceExertedByX(allPlanets[i]);
        }
         */
        for(Planet p : allPlanets){
            sum += this.calcForceExertedByX(p);
        }
        return sum;
    }
    public double calcNetForceExertedByY(Planet[] allPlanets){
        double sum = 0;
        /**
        for(int i = 0; i < allPlanets.length; i += 1){
            sum += this.calcForceExertedByY(allPlanets[i]);
        }
         */
        for(Planet p : allPlanets) {
            sum += this.calcForceExertedByY(p);
        }
        return sum;
    }

    // new information
    public void update(double dt,double xxForce,double yyForce) {
        double xxAcc = xxForce / mass;
        double yyAcc = yyForce / mass;
        xxVel += xxAcc * dt;
        yyVel += yyAcc * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
