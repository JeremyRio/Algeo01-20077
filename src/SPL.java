public class SPL {

  /* =============== MATRIKS BALIKAN ============== */
  // Menghasilkan nilai variabel SPL dengan metode Inverse
  public static Matrix inverseSPL(Matrix M) {
    Matrix kons = new Matrix(M.getRow(), 1);
    Matrix koef = new Matrix(M.getRow(), M.getCol() - 1);
    Matrix hasil;
    kons = Matrix.getMatCons(M);
    koef = Matrix.getMatKoef(M);
    koef = Inverse.gaussInverse(koef);
    hasil = M.multiplyMatrix(koef, kons);
    return hasil;
  }

  /* =============== CRAMER ============== */
  public static Matrix Cramer(Matrix M) {
    // KAMUS LOKAL
    double detCramer, detM;
    Matrix hasil, koef;
    // ALGORITMA
    hasil = new Matrix(M.getRow(), 1);
    koef = Matrix.getMatKoef(M);
    detM = Determinan.detCofactor(koef);

    for (int k = 0; k < M.getCol() - 1; k++) {
      koef = Matrix.getMatKoef(M);
      for (int i = 0; i < M.getRow(); i++) {
        koef.setELMT(i, k, M.getELMT(i, M.getCol() - 1));
      }
      detCramer = Determinan.detCofactor(koef);
      hasil.setELMT(k, 0, detCramer / detM);
    }
    return hasil;
  }

  /* =============== SPL GAUSS ============== */
  public Matrix getRowEchelon(Matrix M) {
    /* KAMUS */
    int i, j, k, l, m, colSwitch;
    int idxColMain = 0; // Set ke 0 karena pasti diganti jika elemen pertama bukan 1
    Matrix M1 = new Matrix(M.getRow(), M.getCol());
    boolean flag, divisorFlag, opRowFlag1 = true, opRowFlag2, switchFlag;
    double divisor, kValue;
    /* ALGORITMA */
    // Copy Matriks M ke Matriks M1

    M.copyMatrix(M1);
    M1.displayMatrix();
    System.out.println("==============================");

    for (i = 0; i < M.getRow(); i++) {
      colSwitch = 0;
      switchFlag = true;
      // Jika elemen diagonal matriks 0, harus di tukar dengan baris yang tidak nol di
      // kolom yang sama
      while (colSwitch < M1.getCol() && switchFlag) {
        if (M1.getELMT(i, colSwitch) == 0) {
          // Set flag switch ke true
          flag = true;
          // Set ke baris selanjutnya
          k = i + 1;
          if ((k < M1.getRow()) && (flag)) {
            // Mencari elemen yang bisa untuk ditukar
            if (M1.getELMT(k, colSwitch) != 0) {
              // Tukar baris ke-i dengan baris ke-k
              M1.switchRow(i, k);
              // Sekali sebuah baris ditukar tidak bisa ditukar lagi
              flag = false;
              // Set switchFlag ke false agar keluar dari loop
              switchFlag = false;
            }
          }
        } else {
          switchFlag = false;
        }
        colSwitch++;
      }

      // Mencari 1 utama dan jika sudah 1, tidak perlu dicek lagi
      // Hanya jalan setelah sisa baris dibawah 1 utama sudah menjadi 0
      // Inisialisasi divisor
      divisor = 1;
      divisorFlag = false;
      opRowFlag1 = true;
      j = 0;
      while (j < M1.getCol() && (divisorFlag == false) && opRowFlag1) {
        if ((M1.getELMT(i, j) != 0)) {
          // Set pembagi ke angka yang akan dijadikan 1 utama
          divisor = M1.getELMT(i, j);
          // Set idxColMain ke j untuk mengetahui letak kolom 1 utama
          idxColMain = j;
          // Set flag ke true agar tidak di loop dan menghasilkan divisor berbeda
          divisorFlag = true;
          // Set flag opRowFlag1 ke false agar tidak jalan ketika sisa baris dibawah 1
          // utama belum 0
          opRowFlag1 = false;

        } else if (M1.getELMT(i, j) == 1) {
          idxColMain = j;
          divisorFlag = false;
        }
        j++;
      }

      // Membagi sisa baris yang ada 1 utama
      for (j = 0; j < M1.getCol(); j++) {
        M1.setELMT(i, j, (M1.getELMT(i, j) / divisor));
      }

      // Iterasi baris - baris dibawah 1 utama untuk dilakukan operasi agar menjadi 0
      for (l = i + 1; l < M1.getRow(); l++) {
        opRowFlag2 = true;
        m = idxColMain;
        // Jika bagian bawah 1 utama tidak 0, dilakukan operationRow agar menjadi 0
        while (m < M1.getCol() && opRowFlag2) {
          if (M1.getELMT(l, m) == 0) {
            opRowFlag2 = false;
          } else if (M1.getELMT(l, m) != 0) {
            kValue = (M1.getELMT(l, m) / M1.getELMT(i, m));
            M1.operationRow(l, i, kValue);
            opRowFlag2 = false;
          }
          m++;
        }
      }

      M1.displayMatrix();
      System.out.println("==============================");
    }
    return M1;
  }

  // Menghasilkan nilai variabel SPL dengan metode Gauss
  public static void GaussElimination(Matrix M) {
    /* KAMUS */
    int i, j;
    int zeroCounter = 0;
    double result[] = new double[M.getCol() - 1];
    /* ALGORITMA */
    // Mengecek baris paling bawah
    i = (M.getRow() - 1);
    j = 0;
    while (j < M.getCol()) {
      // Menghitung jumlah angka 0 di baris paling bawah
      if (M.getELMT(i, j) == 0) {
        zeroCounter++;
      }
      j++;
    }
    // Jika baris paling bawah semuanya 0, maka SPL memiliki solusi banyak
    if (zeroCounter == M.getCol()) {
      ;
      // Jika baris paling bawah 0 tetapi ada angka di kolom paling kanan, SPL tidak
      // memiliki solusi
    } else if (zeroCounter == M.getCol() - 1) {
      System.out.println("SPL tidak memiliki solusi");
      // Jika baris paling bawah memiliki 0 yang lebih sedikit daripada jumlah kolom
      // dikurang satu, maka setiap elemen punya solusi
    } else if (zeroCounter < M.getCol() - 1) {
      // Mulai hitung dari paling bawah, karena memiliki variabel paling sedikit
      for (i = M.getRow() - 1; i >= 0; i--) {
        // Set elemen paling kanan ke array urutan paling kanan
        result[i] = M.getELMT(i, (M.getCol() - 1));

        // Pengurangan setiap baris
        for (j = i + 1; j < (M.getCol() - 1); j++) {
          result[i] -= M.getELMT(i, j) * result[j];
        }
        // Memastikan bahwa hasil akan dibagi koefisien didepan x (jika terjadi bug pada
        // matriks eselon baris)
        result[i] = result[i] / M.getELMT(i, i);
      }
      System.out.println("Solusi SPL: ");
      for (i = 0; i < M.getCol() - 1; i++) {
        System.out.println("X" + (i + 1) + " = " + result[i]);
      }
    }
  }

  /* =============== SPL GAUSS JORDAN ============== */
  // Eliminasi matrix augmenten dengan metode Gauss-Jordan
  public static Matrix elimGaussJordan(Matrix m) {
    Matrix mRes, mKoef, mCons;

    mKoef = Matrix.getMatKoef(m);
    mCons = Matrix.getMatCons(m);
    mRes = Matrix.createAug(mKoef, mCons);

    int pivotRow, pivotCol;
    boolean flag1;

    // Inisialisasi posisi pivot
    pivotRow = 0;
    pivotCol = 0;

    while (pivotRow < m.row && pivotCol < m.col - 1) {
      flag1 = false;

      // Jika elemen pivot bernilai nol, maka akan menukarkan baris tersebut
      // dengan baris lainnya
      if (mKoef.M[pivotRow][pivotCol] == 0) {
        boolean isUnderEmpty;
        isUnderEmpty = Matrix.isUnderEmpty(Matrix.createAug(mKoef, mCons), pivotRow, pivotCol);

        if (!isUnderEmpty) {
          for (int j = pivotRow + 1; j < mKoef.row; j++) {
            if ((mKoef.M[j][pivotCol]) > (mKoef.M[pivotRow][pivotCol])) {
              mKoef.switchRow(pivotRow, j);
              mCons.switchRow(pivotRow, j);

              break;
            }
          }
        } else {
          // penanda untuk mengganti pivot
          flag1 = true;
        }
      }

      // lanjut ke loop berikutnya bila ditemukan suatu baris kosong
      if (Matrix.isRowEmpty(Matrix.createAug(mKoef, mCons), pivotRow)) {
        pivotRow++;
        pivotCol++;
        continue;
      }

      // lanjut ke loop berikutnya dan posisi pivot berganti
      if (flag1) {
        pivotCol++;
        continue;
      }

      double divider;
      divider = mKoef.M[pivotRow][pivotCol];

      // loop untuk mendapatkan leading one suatu baris,
      // sekaligus menyesuaikan elemen kolom lainnya
      for (int j = pivotCol; j < mKoef.col; j++) {
        mKoef.M[pivotRow][j] = mKoef.M[pivotRow][j] / divider;
      }
      mCons.M[pivotRow][0] = mCons.M[pivotRow][0] / divider;

      // loop untuk membuat elemen-elemen diatas leading one
      // agar bernilai nol
      for (int k = 0; k < mKoef.row; k++) {
        if (k == pivotRow || mKoef.M[k][pivotCol] == 0) {
          continue;
        }
        double multiplier;
        multiplier = mKoef.M[k][pivotCol];

        for (int b = pivotCol; b < mKoef.col; b++) {
          mKoef.M[k][b] = mKoef.M[k][b] - (mKoef.M[pivotRow][b] * multiplier);
        }
        mCons.M[k][0] = mCons.M[k][0] - (multiplier * mCons.M[pivotRow][0]);
      }

      pivotRow++;
      pivotCol++;
    }

    mRes = Matrix.createAug(mKoef, mCons);

    Matrix.switchRowEmpty(mRes);
    Matrix.changeZerovalue(mRes);

    return mRes;
  }

  public static void getSolution(Matrix M) {
    /* KAMUS */
    int i, j;
    int zeroCounter = 0;
    double result[] = new double[M.getCol() - 1];
    /* ALGORITMA */
    // Mengecek baris paling bawah
    i = (M.getRow() - 1);
    j = 0;
    while (j < M.getCol()) {
      // Menghitung jumlah angka 0 di baris paling bawah
      if (M.getELMT(i, j) == 0) {
        zeroCounter++;
      }
      j++;
    }
    // Jika baris paling bawah semuanya 0, maka SPL memiliki solusi banyak
    if (zeroCounter == M.getCol()) {
      ;
      // Jika baris paling bawah 0 tetapi ada angka di kolom paling kanan, SPL tidak
      // memiliki solusi
    } else if (zeroCounter == M.getCol() - 1) {
      System.out.println("SPL tidak memiliki solusi");
      // Jika baris paling bawah memiliki 0 yang lebih sedikit daripada jumlah kolom
      // dikurang satu, maka setiap elemen punya solusi
    } else if (zeroCounter < M.getCol() - 1) {
      // Mulai hitung dari paling bawah, karena memiliki variabel paling sedikit
      for (i = M.getRow() - 1; i >= 0; i--) {
        // Set elemen paling kanan ke array urutan paling kanan
        result[i] = M.getELMT(i, (M.getCol() - 1));

        // Pengurangan setiap baris
        for (j = i + 1; j < (M.getCol() - 1); j++) {
          result[i] -= M.getELMT(i, j) * result[j];
        }
        // Memastikan bahwa hasil akan dibagi koefisien didepan x (jika terjadi bug pada
        // matriks eselon baris)
        result[i] = result[i] / M.getELMT(i, i);
      }
      System.out.println("Solusi SPL: ");
      for (i = 0; i < M.getCol() - 1; i++) {
        System.out.println("X" + (i + 1) + " = %6.f" + result[i]);
      }
    }
  }
}