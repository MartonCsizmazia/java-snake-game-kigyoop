package com.codecool.snake;


import javafx.geometry.Point2D;
import java.lang.Math;
import java.util.Random;

import static java.lang.StrictMath.atan;

public class Utils {

    /*
    Converts a direction in degrees (0...360) to x and y coordinates.
    The length of this vector is the second parameter
    */
    public static Point2D directionToVector(double directionInDegrees, double length) {
        double directionInRadians = directionInDegrees / 180 * Math.PI;
        Point2D heading = new Point2D(length * Math.sin(directionInRadians), - length * Math.cos(directionInRadians));
        return heading;
    }

    /**
     * Get a random boolean for an event.
     * @param rare seconds between occurrences on average
     * @return true if the event happens
     */
    public static boolean getRandomForEvent(int rare){
        // 60 frames/sec
        return new Random().nextInt(rare * 60) == 0;
    }

    public static double vectorToDirection(Point2D startPosition, Point2D endPosition) {

        double StartX = startPosition.getX();
        double StartY = startPosition.getY();

        double EndX = endPosition.getX();
        double EndY = endPosition.getY();

        double VectorX = EndX - StartX;
        double VectorY = EndY - StartY;

        double VectorDivision = VectorY/VectorX;
        double Shortdegree = Math.toDegrees(atan(VectorDivision));

        if(Shortdegree<0){
            Shortdegree = Shortdegree*(-1);
        }

        if (VectorX > 0 && VectorY > 0){        // + +
            return Shortdegree+90;
        } else if (VectorX < 0 && VectorY > 0){  // - +
            return 180-Shortdegree+90;
        } else if (VectorX < 0 && VectorY < 0){  // - -
            return 180+Shortdegree+90;
        } else if (VectorX > 0 && VectorY < 0) {  // + -
            return 360 - Shortdegree + 90;
        } else if (VectorX == 0 && VectorY>0){   // 0 +
            return 90;
        } else if (VectorX == 0 && VectorY<0){   // 0 -
            return 270;
        } else if (VectorX>0 && VectorY == 0){   // + 0
            return 0;
        } else {                                // - 0
            return 180;
        }
    }
}
