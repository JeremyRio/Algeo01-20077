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

  // KONSTRUKTOR
  public Matrix(int r, int c) {
    this.M = new float[r][c];
    this.row = r;
    this.col = c;
  }

  // METHOD
  // GETTER
  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }

  public float getELMT(int i, int j) {
    return (this.M[i][j]);
  }

  // SETTER
  public void setELMT(int i, int j, float value) {
    this.M[i][j] = value;
  }

  // INPUT/OUTPUT
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
      // if (i != this.row - 1) {
      System.out.println();
      // }
    }
  }

  // OPERATION
  public int Size() {
    // ALGORITMA
    return (this.row * this.col);
  }

  public void operationRow(int i1, int i2, float k) {
    // KAMUS LOKAL
    int j;
    // ALGORITMA
    for (j = 0; j < col; j++) {
      // setELMT(i1, j, getELMT(i1, j) - k * getELMT(i2, j));
      this.M[i1][j] -= k * this.M[i2][j];
    }
  }

  public void divideRow(int i, float k) {
    // KAMUS LOKAL
    int j;
    // ALGORITMA
    System.out.println(k);
    for (j = 0; j < col; j++) {
      // setELMT(i, j, getELMT(i, j) / k);
      this.M[i][j] /= k;
    }
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

  public void setIdentity() {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (i == j) {
          this.M[i][j] = 1.0f;
        } else {
          this.M[i][j] = 0.0f;
        }
      }
    }
  }

  public void Cramer(Matrix M) {
    int lastCol = M.getCol() - 1;
    Determinan det = new Determinan();
    float detCramer, detM, hasil;
    Matrix copy1;
    detM = det.detCofactor(M);
    for (int k = 0; k < lastCol; k++) {
      copy1 = new Matrix(M.getRow(), lastCol);
      M.copyMatrix(copy1);
      // System.out.println("======");
      for (int i = 0; i < M.getRow(); i++) {
        copy1.setELMT(i, k, M.getELMT(i, lastCol));
      }
      // copy1.displayMatrix();
      detCramer = det.detCofactor(copy1);
      // System.out.println(detCramer);
      // System.out.println(detM);
      hasil = detCramer / detM;
      System.out.println("[x" + (k + 1) + "] = " + detCramer + "/" + detM + " = " + hasil);
    }

  }

  public Matrix multiplyMatrix(Matrix m1, Matrix m2) {
    // KAMUS LOKAL
    int i, j, k = 0;
    Matrix m3;
    float temp = 0;
    // ALGORITMA
    m1.displayMatrix();
    System.out.println("*");
    m2.displayMatrix();
    System.out.println("====");
    m3 = new Matrix(m1.getRow(), m2.getCol());
    // CreateMatrix(ROWS(m1), COLS(m2), &m3);
    for (i = 0; i < m1.getRow(); i++) {
      for (j = 0; j < m2.getCol(); j++) {
        temp = 0;
        for (k = 0; k < m1.getCol(); k++) {
          // System.out.println(m1.getELMT(i, k) + "*" + m2.getELMT(k, j));
          temp += m1.getELMT(i, k) * m2.getELMT(k, j);
          // temp += ELMT(m1, i, k) * ELMT(m2, k, j);
        }
        m3.setELMT(i, j, temp);
      }
    }
    return m3;
  }

  public Matrix gaussInverse(Matrix M) {
    // KAMUS LOKAL
    int i, j, k;
    Matrix copyM = new Matrix(M.getRow(), M.getCol());
    Matrix idM = new Matrix(M.getRow(), M.getCol());
    // ALGORITMA
    M.displayMatrix();
    M.copyMatrix(copyM);
    M.copyMatrix(idM);
    idM.setIdentity();
    for (i = 0; i < copyM.getRow(); i++) {
      if (copyM.getELMT(i, i) == 0) {
        boolean flag = true;
        k = i + 1;
        while (k < copyM.getRow() && flag) {
          if (copyM.getELMT(k, i) != 0) {
            idM.switchRow(i, k);
            copyM.switchRow(i, k);
            flag = false;
          }
          // idM.displayMatrix();
          // copyM.displayMatrix();
        }
      }

      if (copyM.getELMT(i, i) != 1) {
        idM.divideRow(i, copyM.getELMT(i, i));
        copyM.divideRow(i, copyM.getELMT(i, i));
      }
      // System.out.println("===========");
      // copyM.displayMatrix();
      // idM.displayMatrix();

      for (j = 0; j < copyM.getRow(); j++) {
        if (copyM.getELMT(j, i) != 0 && i != j) {
          idM.operationRow(j, i, copyM.getELMT(j, i));
          copyM.operationRow(j, i, copyM.getELMT(j, i));
        }
      }
      // System.out.println("===========");
      // idM.displayMatrix();
    }
    // System.out.println("===========");
    // copyM.displayMatrix();
    // System.out.println("===========");
    // idM.displayMatrix();
    return idM;
  }

  public void inverseSPL(Matrix M) {
    Matrix kons = new Matrix(M.getRow(), 1);
    Matrix copy = new Matrix(M.getRow(), M.getCol() - 1);
    Matrix hasil;
    for (int i = 0; i < M.getRow(); i++) {
      kons.setELMT(i, 0, M.getELMT(i, M.getCol() - 1));
    }
    M.copyMatrix(copy);
    copy = M.gaussInverse(copy);
    hasil = M.multiplyMatrix(copy, kons);
    hasil.displayMatrix();
  }
}