public class Inverse {

  /* Menghasilkan matriks kofaktor dari sebuah matriks */
  public static Matrix getCofMatrix(Matrix M, int pivotrow, int pivotcol) {
    /* KAMUS LOKAL */
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

  /* Menghasilkan matriks adjoin dari sebuah matrix */
  public static Matrix getAdjoin(Matrix M) {
    /* KAMUS LOKAL */
    int i, j;
    int sign = 1;
    double cofactor;
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

  /* Menghasilkan matriks inverse menggunakan cara Adjoin */
  public static Matrix adjoinInverse(Matrix M) {
    /* KAMUS LOKAL */
    int i, j;
    Matrix norminverse = new Matrix(M.getRow(), M.getCol());
    Matrix adj = new Matrix(M.getRow(), M.getCol());
    Matrix inverse = new Matrix(M.getRow(), M.getCol());
    double valueInv, det;

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

  /* Menghasilkan matriks inverse menggunakan cara Gauss-Jordan */
  public static Matrix gaussInverse(Matrix M) {
    /* KAMUS LOKAL */
    int i, j, k;
    Matrix Mkiri = new Matrix(M.getRow(), M.getCol());
    Matrix Mkanan = new Matrix(M.getRow(), M.getCol());

    /* ALGORITMA */
    M.copyMatrix(Mkiri);
    M.copyMatrix(Mkanan);
    // Matrix kanan dijadikan matrix identitas
    Mkanan.setIdentity();
    for (i = 0; i < Mkiri.getRow(); i++) {
      // Melakukan pertukaran jika elemen diagonal ada yang 0
      if (Mkiri.getELMT(i, i) == 0) {
        boolean flag = true;
        k = i + 1;
        while (k < Mkiri.getRow() && flag) {
          if (Mkiri.getELMT(k, i) != 0) {
            Mkanan.switchRow(i, k);
            Mkiri.switchRow(i, k);
            flag = false;
          }
        }
      }
      // Melakukan pembagian koefisien jika pivot != 1
      if (Mkiri.getELMT(i, i) != 1) {
        Mkanan.divideRow(i, Mkiri.getELMT(i, i));
        Mkiri.divideRow(i, Mkiri.getELMT(i, i));
      }
      // Melakukan OBE pada pivot
      for (j = 0; j < Mkiri.getRow(); j++) {
        if (Mkiri.getELMT(j, i) != 0 && i != j) {
          Mkanan.operationRow(j, i, Mkiri.getELMT(j, i));
          Mkiri.operationRow(j, i, Mkiri.getELMT(j, i));
        }
      }
    }
    // Menghilangkan -0.0
    Matrix.changeZerovalue(Mkanan);
    return Mkanan;
  }
}