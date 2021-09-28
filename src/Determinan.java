public class Determinan {

  // METHOD
  public static float detCofactor(Matrix M) {
    // KAMUS LOKAL
    int i, j, k;
    int iCol, iRow;
    float hasil;
    Matrix cof = new Matrix(M.getRow() - 1, M.getCol() - 1);
    // ALGORITMA
    hasil = 0;
    if (M.Size() == 1) {
      // Basis jika matrix 1x1
      hasil = M.getELMT(0, 0);
    } else if (M.Size() == 4) {
      // Basis jika matrix 2x2;
      hasil = (M.getELMT(0, 0) * M.getELMT(1, 1)) - (M.getELMT(0, 1) * M.getELMT(1, 0));
    } else {
      // Membuat submatrix kofaktor
      for (k = 0; k < M.getCol(); k++) {
        iRow = 0;
        for (i = 1; i < M.getRow(); i++) {
          iCol = 0;
          for (j = 0; j < M.getCol(); j++) {
            if (j != k) {
              cof.setELMT(iRow, iCol, M.getELMT(i, j));
              iCol++;
            }
          }
          iRow++;
        }
        if (k % 2 == 0) {
          hasil += M.getELMT(0, k) * detCofactor(cof);
        } else {
          hasil += -1 * M.getELMT(0, k) * detCofactor(cof);
        }
      }
    }
    return hasil;
  }

  public static float detReduksi(Matrix M) {
    // KAMUS LOKAL
    float det = 1;
    int i, j, k;
    int count = 0;
    Matrix copyM = new Matrix(M.getRow(), M.getCol());
    // ALGORITMA
    M.copyMatrix(copyM);
    System.out.println();
    System.out.println("======= ALUR REDUKSI =======");
    for (i = 0; i < copyM.getRow() - 1; i++) {
      if (copyM.getELMT(i, i) == 0) {
        boolean flag = true;
        k = i + 1;
        while (k < copyM.getRow() && flag) {
          if (copyM.getELMT(k, i) != 0) {
            copyM.switchRow(i, k);
            flag = false;
            count++;
          }
        }
        copyM.displayMatrix();
        System.out.println("===========");
      }
      for (j = i + 1; j < copyM.getRow(); j++) {
        if (copyM.getELMT(j, i) != 0) {
          copyM.operationRow(j, i, copyM.getELMT(j, i) / copyM.getELMT(i, i));
        }
      }
      copyM.displayMatrix();
      System.out.println("===========");
    }
    for (i = 0; i < copyM.getRow(); i++) {
      det *= copyM.getELMT(i, i);
    }
    det *= Math.pow(-1, count);
    return det;
  }
}
