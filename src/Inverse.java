package tubes.matrixadt;

public class Inverse {

  public static Matrix getCofMatrix(Matrix M, int pivotrow, int pivotcol) {
    /* KAMUS */
    int i, j;
    int icof = 0, jcof = 0;
    Matrix cof = new Matrix((M.getRow() - 1), (M.getCol() - 1));

    /* ALGORITMA */
    // Hanya jalan kalau ukuran matriks lebih dari 2x2
    if (M.Size() != 4) {
      // Iterasi elemen matriks M
      for (i = 0; i < M.getRow(); i++) {
        for (j = 0; j < M.getCol(); j++) {
          // Kalau indeks tidak bertabrakan dengan pivotrow dan pivotcol, maka isi elemen
          // matriks M dipindah ke matriks cof
          if ((i != pivotrow) && (j != pivotcol)) {
            cof.setELMT(icof, jcof, M.getELMT(i, j));
            jcof++;

            /*
             * Kalau indeks kolom matriks cof sudah mencapai ukuran kolom matriks M dikurang
             * 1 (Karena matriks kofaktor selalu berukuran lebih kecil 1 dari matriks
             * sebelumnya), maka reset indeks kolom matriks cof dan iterasi indeks baris
             * matriks cof
             */
            if (jcof == (M.getCol() - 1)) {
              jcof = 0;
              icof++;
            }
          }
        }
      }
    }

    return cof;
  }

  public static Matrix getAdjoin(Matrix M) {
    /* KAMUS */
    int i, j;
    int sign = 1;
    float cofactor;
    Matrix cof = new Matrix(M.getRow(), M.getCol());
    Matrix tempcof = new Matrix(M.getRow(), M.getCol());
    /* ALGORITMA */
    // Basis kalau ukuran 3x3
    for (i = 0; i < M.getRow(); i++) {
      for (j = 0; j < M.getCol(); j++) {
        // Switch sign antara negatif dan positif
        // Jika i+j genap, maka sign positif
        if ((i + j) % 2 == 0) {
          sign = 1;
        } else {
          sign = -1;
        }

        // Mengambil matriks kofaktor
        tempcof = getCofMatrix(M, i, j);

        // Mencari determinan matriks kofaktor dikalikan dengan sign
        cofactor = Determinan.detCofactor(tempcof) * sign;

        // Mengisi matriks adj dengan cofaktor
        cof.setELMT(i, j, cofactor);
      }
    }
    return (cof.transpose(cof));
  }

  public static Matrix adjoinInverse(Matrix M) {
    int i, j;
    Matrix norminverse = new Matrix(M.getRow(), M.getCol());
    Matrix adj = new Matrix(M.getRow(), M.getCol());
    Matrix inverse = new Matrix(M.getRow(), M.getCol());
    float valueInv, det;

    /* ALGORITMA */
    // Ambil matriks adjoin dari M dan masukkan ke adj
    adj = getAdjoin(M);

    // Ambil determinan dari matriks M dan masukkan ke det
    det = Determinan.detCofactor(M);

    // Iterasi setiap elemen matriks adj dan matriks inverse
    // Jika matriks 2x2, maka gunakan cara normal
    if (M.Size() == 4) {
      // a ditukar dengan d; b dan c dikali (-1)
      norminverse.setELMT(0, 0, M.getELMT(1, 1));
      norminverse.setELMT(1, 1, M.getELMT(0, 0));
      norminverse.setELMT(0, 1, (-1) * M.getELMT(0, 1));
      norminverse.setELMT(1, 0, (-1) * M.getELMT(1, 0));

      for (i = 0; i < M.getRow(); i++) {
        for (j = 0; j < M.getCol(); j++) {
          valueInv = (norminverse.getELMT(i, j) / det);
          inverse.setELMT(i, j, valueInv);
        }
      }
    } else {
      for (i = 0; i < M.getRow(); i++) {
        for (j = 0; j < M.getCol(); j++) {
          // Elemen adjoin dibagi determinan
          valueInv = (adj.getELMT(i, j) / det);
          inverse.setELMT(i, j, valueInv);
        }
      }
    }

    return inverse;
  }

  public static Matrix gaussInverse(Matrix M) {
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
    Matrix.changeZerovalue(copyM);
    return copyM;
  }
}