package com.masum.algorithms.matrix;

public class GaussianElimination {

    static void guss(double[][] mat) {
        int length = mat.length;

        for(int i=0; i<length; i++) {
            int id = i;

            // find the row with the largest absolute value in the current column.
            for(int j=i+1; j<length;j++) {
                if(Math.abs(mat[j][i]) > Math.abs(mat[id][i])) {
                    id = j;
                }
            }

            // the largest number is not in the current row.
            // swap the current row i with row id, element by element.
            if(id!=i) {
                for(int j=i;j<=length;j++) {
                    double temp = mat[i][j];
                    mat[i][j] = mat[id][j];
                    mat[id][j] = temp;
                }
            }

            // gauss elimination does to convert the matrix into
            // diagonal form, where solving for each variable is trivial
            for(int j=0; j<length; j++) {
                if(j!=i) {
                    double factor = mat[j][i]/mat[i][i];
                    for(int k=i; k<=length; k++) {
                        mat[j][k] -= factor * mat[i][k];
                    }
                }
            }

        }

        System.out.println("Solution:");
        for (int i = 0; i < length; i++) {
            System.out.printf("x%d = %.2f%n", i + 1, mat[i][length] / mat[i][i]);
        }
    }

    public static void main(String[] args) {
        // 2x + y - z = 8
        // -3x - y + 2z = -11
        // -2x + y + 2z = -3
        double[][] matrix = {
                { 2, 1, -1, 8 },
                {-3, -1,  2, -11},
                {-2,  1,  2, -3 }
        };

        guss(matrix);

    }

}
