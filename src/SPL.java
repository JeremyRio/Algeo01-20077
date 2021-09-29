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
  public static Matrix getRowEchelon(Matrix M) {
    /* KAMUS */
    int i,j,k,l,m,colSwitch;
    int idxColMain = 0; // Set ke 0 karena pasti diganti jika elemen pertama bukan 1
    Matrix M1 = new Matrix(M.getRow(), M.getCol());
    boolean flag, divisorFlag, opRowFlag1 = true, opRowFlag2, switchFlag;
    double divisor, kValue;
    /* ALGORITMA */
    // Copy Matriks M ke Matriks M1
    
    M.copyMatrix(M1);
    // M1.displayMatrix();
    // System.out.println("==============================");

    for (i=0;i<M.getRow();i++) {
        colSwitch = 0;
        switchFlag = true;
        // Jika elemen diagonal matriks 0 atau lebih dari 1, harus di tukar dengan baris yang tidak nol di kolom yang sama
        while (colSwitch<M1.getCol() && switchFlag) {
            if (M1.getELMT(i, colSwitch) == 0 || M1.getELMT(i, colSwitch) != 1) {
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
        j=0;
        while (j<M.getCol() && (divisorFlag == false) && opRowFlag1) {
            if ((M1.getELMT(i, j) != 0)) {
                // Set pembagi ke angka yang akan dijadikan 1 utama
                divisor = M1.getELMT(i, j);
                // Set idxColMain ke j untuk mengetahui letak kolom 1 utama
                idxColMain = j;
                // Set flag ke true agar tidak di loop dan menghasilkan divisor berbeda
                divisorFlag = true;
                // Set flag opRowFlag1 ke false agar tidak jalan ketika sisa baris dibawah 1 utama belum 0
                opRowFlag1 = false;
                
            } else if (M1.getELMT(i, j) == 1) {
                idxColMain = j;
                divisorFlag = false;   
            }
            j++;
        }

        // Membagi sisa baris yang ada 1 utama
        for(j=0;j<M1.getCol();j++) {
            M1.setELMT(i, j, (M1.getELMT(i, j)/divisor));
        }
        
        // Iterasi baris - baris dibawah 1 utama untuk dilakukan operasi agar menjadi 0
        for (l=i+1;l<M1.getRow();l++) {
            opRowFlag2 = true;
            m = idxColMain;
            // Jika bagian bawah 1 utama tidak 0, dilakukan operationRow agar menjadi 0
            while (m<M1.getCol() && opRowFlag2) {
                if (M1.getELMT(l, m) == 0) {
                    opRowFlag2 = false;
                } else if (M1.getELMT(l, m) != 0) {
                    kValue = (M1.getELMT(l, m)/M1.getELMT(i, m));
                    M1.operationRow(l, i, kValue);
                    opRowFlag2 = false;
                }
                m++;
            }
        }
    }
    Matrix.changeZerovalue(M1);
    
    return M1;
  }

  /* =============== SPL GAUSS JORDAN ============== */
  // Eliminasi matrix augmented dengan metode Gauss-Jordan
  public static Matrix elimGaussJordan(Matrix m) {
    Matrix mRes, mKoef, mCons;

    mKoef = Matrix.getMatKoef(m);
    mCons = Matrix.getMatCons(m);
    mRes = new Matrix(m.row, m.col);
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

  // Parametrik
  //  Menghasilkan nilai variabel SPL dengan metode Gauss
  public static void GaussElimination(Matrix M) {
    /* KAMUS */
    int i;
    /* ALGORITMA */
    // Jika semua diagonal isinya 1 dan ukuran matriks nxn+1, contohnya 3x4, maka solusinya pasti unik
    if (Matrix.isDiagonalOne(M) && (M.getRow() == M.getCol()-1)) {
        getGaussSolutions(M);
    // Jika semua diagonal isinya 1 dan ukuran matriks tidak nxn+1, contohnya 4x7, maka solusinya pasti banyak (berbentuk parametrik)
    } else if (Matrix.isDiagonalOne(M) && (M.getRow() != M.getCol()-1)) {
        getGaussSolutions(M);
    // Jika ada baris dibawah yang seluruh nilainya 0, maka solusinya pasti banyak (berbentuk parametrik)
    } else {
        i = M.getRow()-1;
        // Looping jika semua syarat masih dipenuhi
        while ((i >= 0) && (!Matrix.isNRowZero(M, i)) && (M.getELMT(M.getRow()-1, M.getCol()-1) == 0)) {
            i--;    
        }
        // Jika elemen matriks normal isinya 0 semua dan elemen baris dan kolom paling akhir bukan 0, maka solusinya tidak ada
        if (Matrix.isNRowZero(M, i) && (M.getELMT(M.getRow()-1, M.getCol()-1) != 0)) {
            System.out.println("SPL tidak memiliki solusi");
        } else {
            getGaussSolutions(M);
        }
    }
  }

  public static void getGaussSolutions(Matrix M) {
    /* KAMUS */
    int state[] = new int[M.getCol()-1];
    /* Penjelasan array state
       Jika isi array = 0: maka solusi undefined, belum diinisialisasi
       Jika isi array = 1: maka solusi tersebut eksak, contoh x1 = 20
       Jika isi array = 2: maka solusi tersebut parametrik, contoh x3 = a
       Jika isi array = 3: maka solusi tersebut gabungan eksak dan parametrik, contoh x2 = 2*x3 + 20 */
    
    // Array untuk menyimpan solusi dalam bentuk eksak
    double result[] = new double[M.getCol()-1];

    // Array untuk menyimpan solusi dalam bentuk string
    String Eq[] = new String[M.getCol()-1];
    int i,j,k;
    double cValue = 0;
    String cParam;
    char variabel = 'p';
    /* ALGORITMA */
    // Hitung dari paling bawah dan iterasi ke atas
    for (i=M.getRow()-1;i>=0;i--) {
        
        // Jika ada baris yang isinya 0, skip baris itu dan lanjut iterasi ke atas
        if (Matrix.isRowEmpty(M, i)) {
            continue;
        }
        // Mengambil index kolom dimana ada angka 1 utama
        j = Matrix.getLeadingOne(M, i);
        // Set indeks k yang berjalan berdampingan dengan j
        k = j;
        // Asumsi solusi eksak sebagai inisialisasi
        state[k] = 1;
        // Ganti ke elemen selanjutnya, artinya start loop dari elemen setelah 1 utama
        j++;
        // Ambil nilai konstanta terletak di kolom terakhir
        cValue = M.getELMT(i, M.getCol()-1);

        // Start looping nilai eksak untuk cValue
        while (j < M.getCol()-1) {
            // Skip yang isinya 0
            if (M.getELMT(i, j) != 0) {
                // Jika solusi bukan eksak, maka asumsikan solusi harus disubstitusikan
                // Teorinya karena kalau variabel/kolom itu punya nilai eksak, maka array state akan
                // diisi dengan angka 1, sehingga percabangan ini tidak jalan, bergeser ke percabangan selanjutnya
                if (state[j] != 1) {
                    state[k] = 3;
                }
                // Jika solusi eksak, gunakan perhitungan biasa untuk mendapat nilainya
                if (state[j] == 1) {
                    cValue -= M.getELMT(i, j) * result[j];
                } else if (state[j] == 0) { // Kalau masih undefined, artinya kolom itu tidak bisa dicapai percabangan manapun
                    state[j] = 2; // Set state menjadi bentuk variabel parametrik
                    Eq[j] = String.valueOf(variabel); // Set variabel berbentuk huruf ke dalam array Eq
                    variabel++;
                }
            }
            j++;
        }

        // Lempar nilai cValue yang didapatkan ke dalam array result
        result[k] = cValue;
        // Inisialisasi string cParam
        if (cValue != 0 || state[k] == 1) {
            // Kalau cValue punya nilai, cParam diinisialisasi dengan cValue
            cParam = cValue + "";
        } else {
            // Kalau tidak, biarkan kosong
            cParam = "";
        }

        // Set ke indeks result selanjutnya untuk diproses
        j = k + 1;

        /* BAGIAN PELEMPARAN VARIABEL KE OUTPUT */
        // Jika state 3, maka output berupa gabungan angka dan variabel, contohnya -3q + 2r
        if (state[k] == 3) {
            while (j < M.getCol()-1) {
                // Skip yang isinya 0
                if (M.getELMT(i, j) != 0) {
                    // Jika mendapat state 2, maka berupa parameter
                    if (state[j] == 2) {
                        // Desain output
                        // Jika nilai positif, maka persamaan akan menjadi negatif, karena berubah tanda melewati =
                        if (M.getELMT(i, j) > 0) {
                            // Jika nilai koefisien 1, tidak perlu ditulis angka 1
                            if (Math.abs(M.getELMT(i, j)) == 1) {
                                cParam += "-" + Eq[j] + "";
                            } else { // Jika nilai koefisien bukan 1, maka perlu ditulis koefisien itu
                                
                                cParam += "-" + Math.abs(M.getELMT(i, j)) + Eq[j];
                            }
                        } else { // Jika nilai negatif, maka persamaan akan menjadi positif, karena berubah tanda melewati =
                            if (Math.abs(M.getELMT(i, j)) == 1) {
                                cParam += "+" + Eq[j] + "";
                            } else {
                                cParam += "+" + Math.abs(M.getELMT(i, j)) + Eq[j];
                            }
                        }
                    } else if (state[j] == 3) { // Jika mendapat state 3, maka berupa nilai yang dapat disubstitusikan
                        // Desain output
                        // Aturan sama seperti di atas
                        if (M.getELMT(i, j) > 0) {
                            if (Math.abs(M.getELMT(i, j)) == 1) {
                                cParam += "-" + "(" + Eq[j] + ")";
                            } else {
                                cParam += "-" + Math.abs(M.getELMT(i, j)) + "(" +  Eq[j] + ")";
                            }
                        } else {
                            if (Math.abs(M.getELMT(i, j)) == 1) {
                                cParam += "+" + "(" + Eq[j] + ")";
                            } else {
                                cParam += "+" +  Math.abs(M.getELMT(i, j)) + "(" + Eq[j] + ")";
                            }
                        }
                    }
                }
                j++;
            }
        }

        // Output cParam yang tadi didesain ke dalam Eq
        Eq[k] = cParam;
    }

    // Final cek bagian yang null
    for (i = 0;i<M.getCol()-1;i++) {
        if (state[i] == 0) {
            state[i] = 2;
            Eq[i] = String.valueOf(variabel);
            variabel++;
        }
    }

    System.out.println("Solusi SPL: ");
    for (i=0;i<M.getCol()-1;i++) {
        System.out.println("X" + (i+1) + " = " + Eq[i]);
    } 
  }
}