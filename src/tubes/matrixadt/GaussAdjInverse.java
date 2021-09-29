package tubes.matrixadt;

public class GaussAdjInverse {

    public Matrix getCofMatrix(Matrix M, int pivotrow, int pivotcol) {
        /* KAMUS */
        int i,j;
        int icof = 0, jcof = 0;
        Matrix cof = new Matrix((M.getRow()-1), (M.getCol()-1));

        /* ALGORITMA */
        // Hanya jalan kalau ukuran matriks lebih dari 2x2
        if (M.Size() != 4) {
            // Iterasi elemen matriks M
            for(i=0;i<M.getRow();i++) {
                for(j=0;j<M.getCol();j++) {
                    // Kalau indeks tidak bertabrakan dengan pivotrow dan pivotcol, maka isi elemen matriks M dipindah ke matriks cof
                    if ((i != pivotrow) && (j != pivotcol)) {
                        cof.setELMT(icof, jcof, M.getELMT(i, j));
                        jcof++;
                        
                        /* Kalau indeks kolom matriks cof sudah mencapai ukuran kolom matriks M dikurang 1
                        (Karena matriks kofaktor selalu berukuran lebih kecil 1 dari matriks sebelumnya),
                        maka reset indeks kolom matriks cof dan iterasi indeks baris matriks cof */
                        if (jcof == (M.getCol()-1)) {
                            jcof = 0;
                            icof++;
                        }
                    }
                }
            }
        }
            
        return cof;
    }

    public float detCofactor(Matrix M) {
        // KAMUS LOKAL
        int i, j, k;
        int iCol, iRow;
        float hasil;
        Matrix cof = new Matrix(M.getRow() - 1, M.getCol() - 1);
        // ALGORITMA
        hasil = 0;
        if (M.Size() == 1) {
            hasil = M.getELMT(0, 0);
        } else if (M.Size() == 4) {
            hasil = (M.getELMT(0, 0) * M.getELMT(1, 1)) - (M.getELMT(0, 1) * M.getELMT(1, 0));
        } else {
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

    public Matrix getAdjoin(Matrix M) {
        /* KAMUS */
        int i,j;
        int sign = 1;
        float cofactor;
        Matrix cof = new Matrix(M.getRow(), M.getCol());
        Matrix tempcof = new Matrix(M.getRow(), M.getCol());
        /* ALGORITMA */
        // Basis kalau ukuran 3x3
        for(i=0;i<M.getRow();i++) {
            for(j=0;j<M.getCol();j++) {
                // Switch sign antara negatif dan positif
                // Jika i+j genap, maka sign positif
                if ((i+j) % 2 == 0) {
                    sign = 1;
                } else {
                    sign = -1;
                }

                // Mengambil matriks kofaktor
                tempcof = getCofMatrix(M, i, j);

                // Mencari determinan matriks kofaktor dikalikan dengan sign
                cofactor = detCofactor(tempcof) * sign;

                // Mengisi matriks adj dengan cofaktor
                cof.setELMT(i, j, cofactor);
            }
        }
        return (cof.transpose(cof));
    }

    public Matrix getInverseMatrix(Matrix M) {
        int i,j;
        Matrix norminverse = new Matrix(M.getRow(), M.getCol());
        Matrix adj = new Matrix(M.getRow(), M.getCol());
        Matrix inverse = new Matrix(M.getRow(), M.getCol());
        float valueInv, det;

        /* ALGORITMA */
        // Ambil matriks adjoin dari M dan masukkan ke adj
        adj = getAdjoin(M);

        // Ambil determinan dari matriks M dan masukkan ke det
        det = detCofactor(M);

        // Iterasi setiap elemen matriks adj dan matriks inverse
        // Jika matriks 2x2, maka gunakan cara normal
        if (M.Size() == 4) {
            // a ditukar dengan d; b dan c dikali (-1)
            norminverse.setELMT(0, 0, M.getELMT(1, 1));
            norminverse.setELMT(1, 1, M.getELMT(0, 0));
            norminverse.setELMT(0, 1, (-1)*M.getELMT(0, 1));
            norminverse.setELMT(1, 0, (-1)*M.getELMT(1, 0));

            for(i=0;i<M.getRow();i++) {
                for(j=0;j<M.getCol();j++) {
                    valueInv = (norminverse.getELMT(i, j) / det);
                    inverse.setELMT(i, j, valueInv);
                }
            }
        } else {
            for(i=0;i<M.getRow();i++) {
                for(j=0;j<M.getCol();j++) {
                    // Elemen adjoin dibagi determinan
                    valueInv = (adj.getELMT(i, j) / det);
                    inverse.setELMT(i, j, valueInv);
                }
            }
        }
        
        return inverse;
    }
    public Matrix getRowEchelon(Matrix M) {
        /* KAMUS */
        int i,j,k,l,m,colSwitch;
        int idxColMain = 0; // Set ke 0 karena pasti diganti jika elemen pertama bukan 1
        Matrix M1 = new Matrix(M.getRow(), M.getCol());
        boolean flag, divisorFlag, opRowFlag1 = true, opRowFlag2, switchFlag;
        float divisor, kValue;
        /* ALGORITMA */
        // Copy Matriks M ke Matriks M1
        
        M.copyMatrix(M1);
        M1.displayMatrix();
        System.out.println("==============================");

        for (i=0;i<M.getRow();i++) {
            colSwitch = 0;
            switchFlag = true;
            // Jika elemen diagonal matriks 0, harus di tukar dengan baris yang tidak nol di kolom yang sama
            while (colSwitch<M1.getCol() && switchFlag) {
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
            j=0;
            while (j<M1.getCol() && (divisorFlag == false) && opRowFlag1) {
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

            
            M1.displayMatrix();
            System.out.println("==============================");
        }
        M1.changeZerovalue(M1);
        
        return M1;
    }

    public void GaussElimination(Matrix M) {
        /* KAMUS */
        int i,j;
        int zeroCounter = 0;
        float result[] = new float[M.getCol()-1];
        /* ALGORITMA */
        // Mengecek baris paling bawah
        i = (M.getRow()-1);
        j = 0;
        while (j<M.getCol()) {
            // Menghitung jumlah angka 0 di baris paling bawah
            if (M.getELMT(i, j) == 0) {
                zeroCounter++;
            }
            j++;
        }
        // Jika baris paling bawah semuanya 0, maka SPL memiliki solusi banyak
        if (zeroCounter == M.getCol()) {
            M.getGaussSolutions(M);
        // Jika baris paling bawah 0 tetapi ada angka di kolom paling kanan, SPL tidak memiliki solusi
        } else if (zeroCounter == M.getCol()-1) {
            System.out.println("SPL tidak memiliki solusi");
        // Jika baris paling bawah memiliki 0 yang lebih sedikit daripada jumlah kolom dikurang satu, maka setiap elemen punya solusi
        } else if ((zeroCounter < M.getCol()-1) && (isDiagonalOne(M))) {
            M.getGaussSolutions(M);
        }
    }

    public void getGaussSolutions(Matrix M) {
        /* KAMUS */
        int state[] = new int[M.getCol()-1];
        /* Penjelasan array state
           Jika isi array = 0: maka solusi undefined, belum diinisialisasi
           Jika isi array = 1: maka solusi tersebut eksak, contoh x1 = 20
           Jika isi array = 2: maka solusi tersebut parametrik, contoh x3 = a
           Jika isi array = 3: maka solusi tersebut gabungan eksak dan parametrik, contoh x2 = 2*x3 + 20 */
        
        // Array untuk menyimpan solusi dalam bentuk eksak
        float result[] = new float[M.getCol()-1];

        // Array untuk menyimpan solusi dalam bentuk string
        String Eq[] = new String[M.getCol()-1];
        int i,j,k;
        float cValue, tempValue = 0;
        String cParam;
        char variabel = 'p';
        /* ALGORITMA */
        // Hitung dari paling bawah dan iterasi ke atas
        for (i=M.getRow()-1;i>=0;i--) {
            

            // Jika ada baris yang isinya 0, skip baris itu dan lanjut iterasi ke atas
            if (isRowZero(M, i)) {
                continue;
            }
            // Mengambil index kolom dimana ada angka 1 utama
            j = getLeadingOne(M, i);
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
                                    tempValue = M.getELMT(i, j);
                                    cParam += "-" + Math.abs(M.getELMT(i, j)) + Eq[j];
                                    System.out.println(tempValue);
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
