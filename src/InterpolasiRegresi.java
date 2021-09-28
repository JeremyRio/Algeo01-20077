package tubes.matrixadt;

public class InterpolasiRegresi {

  /* INTERPOLASI */
  public static float interpolasiSPL(Matrix M, int N, float x) {
    int i, j;
    float tafsir = 0;
    float a, koef;
    Matrix hasil;
    Matrix polasi = new Matrix(N, N + 1);
    for (i = 0; i < N; i++) {
      a = M.getELMT(i, 0);
      koef = 1;
      for (j = 0; j < N; j++) {
        polasi.setELMT(i, j, koef);
        koef *= a;
      }
      polasi.setELMT(i, j, M.getELMT(i, M.getCol() - 1));
    }
    hasil = SPL.inverseSPL(polasi);
    for (i = 0; i < N; i++) {
      tafsir += Math.pow(x, i) * hasil.getELMT(i, 0);
    }
    return tafsir;
  }

  /* REGRESI */

  public static float regresiGandaSPL(Matrix M, Matrix x) {
    int i;
    float tafsir;
    Matrix hasil;
    hasil = SPL.inverseSPL(M);
    tafsir = hasil.getELMT(0, 0);
    for (i = 0; i < x.getRow(); i++) {
      tafsir += x.getELMT(i, 0) * hasil.getELMT(i + 1, 0);
    }
    return tafsir;
  }
}
