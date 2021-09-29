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
    Scanner sc = new Scanner(System.in);
    int i, j;
    // ALGORITMA
    for (i = 0; i < this.row; i++) {
      // Create list of float value
      String[] lineInput;
      lineInput = sc.nextLine().split(" ");
      for (j = 0; j < this.col; j++) {
        // fill matrix from lineInput content
        this.M[i][j] = Integer.parseInt(lineInput[j]);
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

  public void switchRow(int i1, int i2) {
    // KAMUS LOKAL
    int j;
    float temp;
    // ALGORITMA
    for (j = 0; j < col; j++) {
      // temp = getELMT(i1, j);
      // setELMT(i1, j, getELMT(i2, j));
      // setELMT(i2, j, temp);
      temp = this.M[i1][j];
      this.M[i1][j] = this.M[i2][j];
      this.M[i2][j] = temp;
    }
  }

  public void copyMatrix(Matrix outM) {
    // KAMUS LOKAL
    int i, j;
    // ALGORITMA
    for (i = 0; i < outM.getRow(); i++) {
      for (j = 0; j < outM.getCol(); j++) {
        outM.setELMT(i, j, this.getELMT(i, j));
      }
    }
  }

  public Matrix transpose(Matrix M) {
    /* KAMUS */
    int i,j;
    Matrix Mout = new Matrix(M.getRow(), M.getCol());
    /* ALGORITMA */
    for(i=0;i<M.getRow();i++) {
        for(j=0;j<M.getCol();j++) {
            Mout.setELMT(j, i, M.getELMT(i, j));
        }
    }
  return Mout;
}

  public void operationRow(int i1, int i2, float k) {
  // KAMUS LOKAL
  int j;
  // ALGORITMA
  for (j = 0; j < col; j++) {
      setELMT(i1, j, getELMT(i1, j) - k * getELMT(i2, j));
    }
  }

  public boolean isDiagonalOne(Matrix M) {
    /* KAMUS LOKAL */
    int i;
    boolean flag = true;
    /* ALGORITMA  */
    i = 0;
    while (i<M.getRow() && flag) {
        if (M.getELMT(i, i) != 1) {
            flag = false;
        }
        i++;
    }
    return flag;
  }

  public int getLeadingOne(Matrix M, int row) {
    /* KAMUS */
    int j = 0;
    int idxLead = -1;
    boolean found = false;
    /* ALGORITMA  */
    // Iterasi untuk mencari leading one di tiap baris
    while (j<M.getCol() && (found == false)) {
        if (M.getELMT(row, j) == 1) {
            idxLead = j;
            found = true;
        }
        j++;
    }
    return idxLead;
  }
  public void changeZerovalue(Matrix m){
    /* KAMUS */
    int i,j;
    /* ALGORITMA */
    for(i = 0; i < m.row; i++){
        for(j = 0; j < m.col; j++){
            if(m.M[i][j] == -0.0){
                m.M[i][j] = Math.abs(m.M[i][j]);
            }
        }
    }
}
}