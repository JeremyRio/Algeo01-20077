/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubes.matrixadt;

/**
 *
 * @author Jeremy
 */

import java.util.*;

public class Matrix {
  // ATRIBUT
  float[][] M;
  int row;
  int col;

  // METHOD
  public Matrix(int r, int c) { // KONSTRUKTOR:
    this.M = new float[r][c];
    this.row = r;
    this.col = c;
  }

  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }

  public float getELMT(int i, int j) {
    return (this.M[i][j]);
  }

  public void setELMT(int i, int j, float value) {
    this.M[i][j] = value;
  }

  public void readMatrix() {
    // KAMUS LOKAL
    Scanner input = new Scanner(System.in);
    int i, j;
    // ALGORITMA
    for (i = 0; i < this.row; i++) {
      for (j = 0; j < this.col; j++) {
        this.M[i][j] = input.nextFloat();
      }
    }
  }

  public void displayMatrix() {
    // KAMUS LOKAL
    int i, j;
    // ALGORITMA
    for (i = 0; i < this.row; i++) {
      for (j = 0; j < this.col; j++) {
        System.out.print(M[i][j]);
        if (j != this.col - 1) {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

  public int Size() {
    // ALGORITMA
    return (this.row * this.col);
  }

  public void operasiOBE(Matrix M, int i1, int i2, float k) {
    // KAMUS LOKAL
    int j;
    // ALGORITMA
    for (j = 0; j < M.col; j++) {
      M.setELMT(i1, j, M.getELMT(i1, j) + k * M.getELMT(i2, j));
    }
  }

  public void perkalianOBE(Matrix M, int i, float k) {
    // KAMUS LOKAL
    int j;
    // ALGORITMA
    for (j = 0; j < M.col; j++) {
      M.setELMT(i, j, k * M.getELMT(i, j));
    }
  }
}
