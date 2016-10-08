import java.util.Arrays;

/**
 * Created by Админ on 09.07.2016.
 */
public class MathVector {
    public static void main(String[] args) {
        MathVector vector1 = new MathVector(1);
        MathVector vector2 = new MathVector(new double[] {1, 2, 3, 4});
        vector1.setLength(3);
        vector2.setLength(3);
        vector1.set(0, vector2.get(2));
        vector1.set(1, vector2.get(1));
        vector1.set(2, vector2.get(0));
        System.out.println("v1=" + vector1 + ", v2=" + vector2);
        vector1.plus(vector2);
        vector2.minus(vector1);
        vector2.negate();
        vector1.minus(vector2);
        System.out.println("v1=" + vector1 + ", v2=" + vector2);
        System.out.println(vector1.multi(vector2));
        System.out.println("len1=" + vector1.length() + ", len2=" + vector2.length());
        vector1.normalize();
        vector2.normalize();
        System.out.println("len1=" + vector1.length() +
                       ", len2=" + vector2.length());
    }
    ///////////////////////////////////////////////////////////////////////

    private double[] coordinate;

    public MathVector(int length) {
        coordinate = new double[length];
    }

    public MathVector(double[] coordinate) {
        this.coordinate = coordinate;
    }

    public void set(int i, double coordinate) {
        this.coordinate[i] = coordinate;
    }

    public double get(int i) {
        return coordinate[i];
    }

    public void setLength(int length) {
        double[] newMass = new double[length];
        int minLength;
        if (newMass.length < getLength()) {
            minLength = newMass.length;
        } else {
            minLength = getLength();
        }
        for (int i = 0; i < minLength; i++) {
            newMass[i] = this.coordinate[i];
        }
        this.coordinate = newMass;
    }

    public int getLength() {
        return this.coordinate.length;
    }

    private void plus(MathVector vector2) {
        if (getLength() != vector2.getLength()) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < getLength(); i++) {
                this.coordinate[i] += vector2.get(i);
            }
        }
    }

    private void minus(MathVector vector1) {
        if (getLength() != vector1.getLength()) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < getLength(); i++) {
                this.coordinate[i] -= vector1.get(i);
            }
        }
    }

    private void negate() {
        for (int i = 0; i < getLength(); i++) {
            if (this.coordinate[i] != 0.0) {
                this.coordinate[i] = this.coordinate[i] * -1;
            }
        }
    }

    private int multi(MathVector vector2) {
        int result = 0;
        if (getLength() != vector2.getLength()) {
            throw new IllegalArgumentException();
        } else {
            double[] newMass = new double[getLength()];
            for (int i = 0; i < getLength(); i++) {
                newMass[i] = this.coordinate[i] *= vector2.get(i);
            }
            for (int i = 0; i < getLength(); i++) {
                result += newMass[i];
            }
        }
        return result;
    }

    private void normalize() {
        double invVectorLength, transientVector = 0.0;
        for (int i = 0; i < getLength(); i++) {
            transientVector += (this.coordinate[i] * this.coordinate[i]);
        }
        invVectorLength = 1/(Math.sqrt(transientVector));
        for (int i = 0; i < getLength(); i++) {
            this.coordinate[i] = this.coordinate[i] * invVectorLength;
        }
    }

    private double length() {
        double transientVactor = 0.0;
        for (int i = 0; i < getLength(); i++) {
            transientVactor += this.coordinate[i] * this.coordinate[i];
        }
        return Math.sqrt(transientVactor);
    }

    public String toString() {
        return Arrays.toString(coordinate);
    }
}
