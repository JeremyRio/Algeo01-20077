package tubes.matrixadt;

public class Interpolasi {

  public static Matrix getMatCons(Matrix m) {
    Matrix matCons = new Matrix(m.row, 1);
    int i;

    for (i = 0; i < matCons.row; i++) {
      matCons.M[i][0] = m.M[i][m.col - 1];
    }

    return matCons;

  }

  public void interpolasiSPL(Matrix M, int N, float x) {
    int i, j;
    float tafsir = 0;
    float a, koef;
    Matrix hasil;
    Matrix polasi = new Matrix(N, N + 1);
    // kons = getMatCons(M);
    for (i = 0; i < N; i++) {
      a = M.getELMT(i, 0);
      koef = 1;
      for (j = 0; j < N; j++) {
        polasi.setELMT(i, j, koef);
        koef *= a;
      }
      polasi.setELMT(i, j, M.getELMT(i, M.getCol() - 1));
    }
    hasil = polasi.inverseSPL(polasi);
    for (i = 0; i < N; i++) {
      tafsir += Math.pow(x, i) * hasil.getELMT(i, 0);
    }
    System.out.println(tafsir);
  }

  public void regresiGandaSPL(Matrix M, int N, Matrix x) {
    int i, j;
    float tafsir;
    float a, koef;
    Matrix hasil;
    Matrix regresi = new Matrix(N, N + 1);
    // kons = getMatCons(M);
    for (i = 0; i < N; i++) {
      a = M.getELMT(i, 0);
      koef = 1;
      for (j = 0; j < N; j++) {
        regresi.setELMT(i, j, koef);
        koef *= a;
      }
      regresi.setELMT(i, j, M.getELMT(i, M.getCol() - 1));
    }
    regresi.displayMatrix();
    hasil = regresi.inverseSPL(regresi);
    hasil.displayMatrix();
    tafsir = hasil.getELMT(0, 0);
    for (i = 0; i < N-1; i++) {
      tafsir += x.getELMT(i, 0) * hasil.getELMT(i+1, 0);
    }
    System.out.println(tafsir);
  }
}
