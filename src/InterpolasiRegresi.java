public class InterpolasiRegresi {

  /* INTERPOLASI */
  /* Menghasilkan taksiran dari titik-titik interpolasi */
  public static void interpolasiSPL(Matrix M, double x) {
    /* KAMUS LOKAL */
    String rumus, m;
    int i, j;
    double taksir = 0;
    double a, koef;
    Matrix hasil;
    Matrix polasi = new Matrix(M.getRow(), M.getRow() + 1);
    /* ALGORITMA */
    // Melakukan penempatan konstanta pada kolom Matrix
    for (i = 0; i < M.getRow(); i++) {
      a = M.getELMT(i, 0);
      koef = 1;
      for (j = 0; j < M.getRow(); j++) {
        polasi.setELMT(i, j, koef);
        koef *= a;
      }
      polasi.setELMT(i, j, M.getELMT(i, M.getCol() - 1));
    }
    // Menghitung nilai Matrix polasi
    hasil = SPL.inverseSPL(polasi);
    // Membentuk rumus polinom
    rumus = "P" + (hasil.getRow() - 1) + "(X) = " + hasil.getELMT(0, 0);
    for (i = 1; i < M.getRow(); i++) {
      rumus += " + " + hasil.getELMT(i, 0) + " X^" + i;
    }
    for (i = 0; i < hasil.getRow(); i++) {
      taksir += Math.pow(x, i) * hasil.getELMT(i, 0);
    }
    System.out.println("\nPersamaan polinom yang terbentuk: ");
    System.out.println(rumus);
    System.out.println("\nHasil taksiran polinom: ");
    m = "P" + (hasil.getRow() - 1) + "(" + x + ")" + " = " + taksir;
    System.out.println(m);
    IOFile.saveFilePolinom(rumus, m);
  }

  /* REGRESI */
  /* Menghasilkan taksiran dari regresi data sampel */
  public static void regresiGandaSPL(Matrix M, Matrix X) {
    /* KAMUS LOKAL */
    String rumus, m;
    int N = X.getCol(); // Jumlah peubah x
    int i, j, k, l, idxCol;
    double temp;
    double taksir; // sebagai index element
    Matrix hasilM; // Matrix setelah Normal Estimation Equation
    Matrix hasilSPL; // Matrix hasil SPL

    /* ALGORITMA */
    // Melakukan Normal Estimation Equation pada Matrix M
    hasilM = new Matrix(N + 1, N + 2);
    for (i = 0; i < hasilM.getRow(); i++) {
      idxCol = i - 1;
      for (j = 0; j < M.getCol(); j++) {
        l = j + 1;
        temp = 0;
        for (k = 0; k < M.getRow(); k++) {
          if (i != 0) {
            temp += M.getELMT(k, j) * M.getELMT(k, idxCol);
          } else {
            temp += M.getELMT(k, j);
          }
        }
        if (j == 0 && i != 0) {
          hasilM.setELMT(i, j, hasilM.getELMT(j, i));
        }
        if (j == 0 && i == 0) {
          hasilM.setELMT(i, j, M.getRow());
        }
        hasilM.setELMT(i, l, temp);
      }
    }

    // Melakukan SPL pada Matrix hasil Normal Estimation Equation
    hasilSPL = SPL.inverseSPL(hasilM);
    // Membentuk rumus polinom
    rumus = "Y = " + hasilSPL.getELMT(0, 0);
    for (i = 1; i < hasilSPL.getRow(); i++) {
      rumus += " + " + hasilSPL.getELMT(i, 0) + " X" + i;
    }

    taksir = hasilSPL.getELMT(0, 0);

    for (i = 0; i < N; i++) {
      taksir += X.getELMT(0, i) * hasilSPL.getELMT(i + 1, 0);
    }

    System.out.println("\nPersamaan Regresi yang terbentuk: ");
    System.out.println(rumus);
    System.out.println();
    m = "Hasil taksiran regresi: " + taksir;
    System.out.println(m);
    IOFile.saveFileRegresi(rumus, m);
  }

}
