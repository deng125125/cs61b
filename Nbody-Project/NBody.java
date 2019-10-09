public class NBody {
    // read the radius of the universe in a .txt file
    public static double readRadius(String filename){
        In in = new In(filename);
        int number = in.readInt();
        return in.readDouble();
    }

    // return a Planet[] of all planets, including their attributes
    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[number];
        for(int i = 0; i < number; i += 1){
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    //creating an Animation of planets
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radiusOfUniverse = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        // draw the background
        StdDraw.setXscale(-radiusOfUniverse,  radiusOfUniverse);
        StdDraw.setYscale(-radiusOfUniverse,  radiusOfUniverse);

        //creating an Animation
        StdDraw.enableDoubleBuffering();
        double time = 0;

        while(time < T) {
            //draw background
            StdDraw.picture(0, 0, "images/starfield.jpg");

            double[] xForces = new double[100];
            double[] yForces = new double[100];
            int i = 0;
            for(Planet p: planets) {
                xForces[i] += p.calcNetForceExertedByX(planets);
                yForces[i] += p.calcNetForceExertedByY(planets);
                p.update(dt, xForces[i], yForces[i]);
                p.draw();
                i += 1;
            }

            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
    }
}
