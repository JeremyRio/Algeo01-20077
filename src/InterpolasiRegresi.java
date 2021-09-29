public class InterpolasiRegresi {

  /* INTERPOLASI */
  public static float interpolasiSPL(Matrix M, float x) {
    // KAMUS LOKAL
    String rumus;
    int i, j;
    float taksir = 0;
    float a, koef;
    Matrix hasil;
    Matrix polasi = new Matrix(M.getRow(), M.getRow() + 1);

    // ALGORITMA
    for (i = 0; i < M.getRow(); i++) {
      a = M.getELMT(i, 0);
      koef = 1;
      for (j = 0; j < M.getRow(); j++) {
        polasi.setELMT(i, j, koef);
        koef *= a;
      }
      polasi.setELMT(i, j, M.getELMT(i, M.getCol() - 1));
    }
    hasil = SPL.inverseSPL(polasi);
    rumus = String.valueOf(hasil.getELMT(0, 0));
    for (i = 1; i < M.getRow(); i++) {
      rumus += " + " + hasil.getELMT(i, 0) + " X^" + i;
    }
    for (i = 0; i < M.getRow(); i++) {
      taksir += Math.pow(x, i) * hasil.getELMT(i, 0);
    }
    System.out.println();
    System.out.println("f(X) = " + rumus);
    return taksir;
  }

  /* REGRESI */
  public static float regresiGandaSPL(Matrix M, Matrix x) {
    int i;
    float taksir;
    Matrix hasil;
    hasil = SPL.inverseSPL(M);
    taksir = hasil.getELMT(0, 0);
    for (i = 0; i < x.getRow(); i++) {
      taksir += x.getELMT(i, 0) * hasil.getELMT(i + 1, 0);
    }
    return taksir;
  }
}
