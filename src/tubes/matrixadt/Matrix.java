package tubes.matrixadt;

import java.util.*;

public class Matrix {
  /* Attribute */
  float[][] M;
  int row;
  int col;

  /* Konstruktor */
  public Matrix(int r, int c) {
    this.M = new float[r][c];
    this.row = r;
    this.col = c;
  }

  /* Method */
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
      this.M[i1][j] -= k * this.M[i2][j];
    }
  }

  public void divideRow(int i, float k) {
    // KAMUS LOKAL
    int j;
    // ALGORITMA
    for (j = 0; j < col; j++) {
      this.M[i][j] /= k;
    }
  }

  public void switchRow(int i1, int i2) {
    // KAMUS LOKAL
    int j;
    float temp;
    // ALGORITMA
    for (j = 0; j < col; j++) {
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
    // Mengubah matriks menjadi matriks identitas
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

  public Matrix transpose(Matrix M) {
    // KAMUS LOKAL
    int i, j;
    Matrix Mout = new Matrix(M.getRow(), M.getCol());
    // ALGORITMA
    for (i = 0; i < M.getRow(); i++) {
      for (j = 0; j < M.getCol(); j++) {
        Mout.setELMT(j, i, M.getELMT(i, j));
      }
    }
    return Mout;
  }

  public Matrix multiplyMatrix(Matrix m1, Matrix m2) {
    // KAMUS LOKAL
    int i, j, k = 0;
    Matrix m3 = new Matrix(m1.getRow(), m2.getCol());
    float temp = 0;
    // ALGORITMA
    for (i = 0; i < m1.getRow(); i++) {
      for (j = 0; j < m2.getCol(); j++) {
        temp = 0;
        for (k = 0; k < m1.getCol(); k++) {
          temp += m1.getELMT(i, k) * m2.getELMT(k, j);
        }
        m3.setELMT(i, j, temp);
      }
    }
    return m3;
  }

  public void Cramer(Matrix M) {
    // Mendapatkan Matrix sebenarnya
    // KAMUS LOKAL
    int lastCol = M.getCol() - 1;
    Determinan det = new Determinan();
    float detCramer, detM;
    Matrix copy, hasil;
    // ALGORITMA
    hasil = new Matrix(lastCol, 0);
    copy = new Matrix(M.getRow(), lastCol);
    detM = det.detCofactor(M);
    for (int k = 0; k < lastCol; k++) {
      M.copyMatrix(copy);
      for (int i = 0; i < M.getRow(); i++) {
        copy.setELMT(i, k, M.getELMT(i, lastCol));
      }
      detCramer = det.detCofactor(copy);
      hasil.setELMT(k, 0, detCramer / detM);
    }
  }

  public Matrix gaussInverse(Matrix M) {
    // KAMUS LOKAL
    int i, j, k;
    Matrix copyM = new Matrix(M.getRow(), M.getCol());
    Matrix idM = new Matrix(M.getRow(), M.getCol());
    // ALGORITMA
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
        }
      }

      if (copyM.getELMT(i, i) != 1) {
        idM.divideRow(i, copyM.getELMT(i, i));
        copyM.divideRow(i, copyM.getELMT(i, i));
      }

      for (j = 0; j < copyM.getRow(); j++) {
        if (copyM.getELMT(j, i) != 0 && i != j) {
          idM.operationRow(j, i, copyM.getELMT(j, i));
          copyM.operationRow(j, i, copyM.getELMT(j, i));
        }
      }
    }
    return idM;
  }

  public Matrix inverseSPL(Matrix M) {
    Matrix kons = new Matrix(M.getRow(), 1);
    Matrix copy = new Matrix(M.getRow(), M.getCol() - 1);
    Matrix hasil;
    for (int i = 0; i < M.getRow(); i++) {
      kons.setELMT(i, 0, M.getELMT(i, M.getCol() - 1));
    }
    M.copyMatrix(copy);
    copy = M.gaussInverse(copy);
    hasil = M.multiplyMatrix(copy, kons);
    return hasil;
  }
}