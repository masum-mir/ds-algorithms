package com.masum.algorithms.backtracking;

public class PermutationGenerate {
    static int[] arr;
    static int[] used;
    static int[] number;
    static void permutation(int index, int n) {
        if(index == n) {
            for(int i=0; i<n; i++) {
                System.out.print(number[i]+" ");
            }
            System.out.println();
        }

        for(int i=0; i<n; i++) {
            if(used[i] == 0) {
                used[i] = 1;
                number[index]= arr[i];
                permutation(index+1, n);
                used[i]=0; // backtrack
            }
        }
    }

    public static void main(String[] args) {

        arr = new int[]{1,2,3};
        used = new int[arr.length];
        number = new int[arr.length];
        permutation(0, arr.length);
    }
}
