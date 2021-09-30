import java.util.*;

class MenuUI {
  static Scanner in = new Scanner(System.in); // Input String
  static Scanner sc = new Scanner(System.in); // Input integer/double

  public static void displayTipe() {
    System.out.println();
    System.out.println("==================================");
    System.out.println("Tipe masukan yang akan digunakan?");
    System.out.println("1. Keyboard");
    System.out.println("2. File");
    System.out.println("==================================");
    System.out.print(">Masukan: ");
  }

  public static void main(String[] args) {
    boolean isRun = true;

    System.out.println("==========================================");
    System.out.println("      (^///^) SELAMAT DATANG (^///^)      ");
    System.out.println("==========================================");

    while (isRun) {
      String message;
      String pilihan;
      int m, n, tipe;
      Matrix ab, a, hasilM, k;
      double hasil, x;
      IOFile f;
      String temp;

      System.out.println();

      System.out.println("============== MENU MATRIKS ==============");
      System.out.println("1. Sistem Persamaan Linier");
      System.out.println("2. Determinan");
      System.out.println("3. Matriks Balikan");
      System.out.println("4. Interpolasi Polinom");
      System.out.println("5. Regresi linier berganda");
      System.out.println("6. Keluar");
      System.out.println("============== MENU MATRIKS ==============");

      System.out.print(">Masukan: ");
      pilihan = in.nextLine();

      switch (pilihan) {
        case "1":
          System.out.println();
          System.out.println("============== SPL ==============");
          System.out.println("1. Metode eleminasi Gauss");
          System.out.println("2. Metode eleminasi Gauss-Jordan");
          System.out.println("3. Metode matriks balikan");
          System.out.println("4. Kaidah Cramer");
          System.out.println("0. Kembali ke menu utama");
          System.out.println("============== SPL ==============");
          System.out.print(">Masukan: ");
          pilihan = in.nextLine();

          if (pilihan.equals("0")) {
            break;
          } else {
            displayTipe();
            tipe = sc.nextInt();

            if (tipe == 1) {
              System.out.print(">Baris: ");
              m = sc.nextInt();
              System.out.print(">Kolom: ");
              n = sc.nextInt();
              System.out.println(">Matriks: ");
              ab = new Matrix(m, n);
              ab.readMatrix();
            } else {
              System.out.print(">Masukkan path file: ");
              String path = in.nextLine();
              f = new IOFile(path);
              ab = f.readFile();
            }

            System.out.println();
            switch (pilihan) {
              case "1":
                hasilM = SPL.getRowEchelon(ab);
                SPL.GaussElimination(hasilM);

                break;
              case "2":
                hasilM = SPL.elimGaussJordan(ab);
                SPL.GaussElimination(hasilM);

                break;
              case "3":
                if ((ab.getCol() - 1 != ab.getRow())) {
                  temp = "Metode tidak dapat digunakan karena determinan tidak dapat dikalkulasi";
                  System.out.println(temp);
                  IOFile.saveFile(temp);
                } else {
                  hasil = Determinan.detCofactor(Matrix.getMatKoef(ab));
                  if (hasil == 0) {
                    temp = "Metode tidak dapat digunakan karena determinan = 0";
                    System.out.println(temp);
                    IOFile.saveFile(temp);
                  } else {
                    hasilM = SPL.inverseSPL(ab);
                    System.out.println("Solusi Inverse: ");
                    for (int i = 0; i < hasilM.getRow(); i++) {
                      System.out.printf("X" + (i + 1) + " = %.6f", hasilM.getELMT(i, 0));
                      System.out.println();
                    }
                    IOFile.saveFileSPL(hasilM);
                  }
                }
                break;

              case "4":
                if ((ab.getCol() - 1 != ab.getRow())) {
                  temp = "Metode tidak dapat digunakan karena tidak bisa dicari determinan";
                  System.out.println(temp);
                  IOFile.saveFile(temp);
                } else {
                  hasil = Determinan.detCofactor(Matrix.getMatKoef(ab));
                  if (hasil == 0) {
                    temp = "Metode tidak dapat digunakan karena determinan = 0";
                    System.out.println(temp);
                    IOFile.saveFile(temp);
                  } else {
                    hasilM = SPL.Cramer(ab);
                    System.out.println("Solusi Cramer: ");
                    for (int i = 0; i < hasilM.getRow(); i++) {
                      System.out.printf("X" + (i + 1) + " = " + hasilM.getELMT(i, 0));
                      System.out.println();
                    }
                    IOFile.saveFileSPL(hasilM);
                  }
                }
                break;
            }
            System.out.println();
            System.out.println("==================================");
            System.out.println("Apakah anda ingin sudahi program?");
            System.out.println("1. Ya");
            System.out.println("0. Kembali ke menu utama");
            System.out.println("==================================");
            System.out.print(">Masukan: ");
            pilihan = in.nextLine();

            switch (pilihan) {
              case "1":
                isRun = false;
                break;
              case "0":
                break;
              default:
                isRun = false;
                break;
            }
            break;
          }

        case "2":
          System.out.println();
          System.out.println("============== DETERMINAN ==============");
          System.out.println("1. Metode ekspansi kofaktor");
          System.out.println("2. Metode reduksi baris");
          System.out.println("0. Kembali ke menu utama");
          System.out.println("============== DETERMINAN ==============");
          System.out.print(">Masukan: ");
          pilihan = in.nextLine();

          if (pilihan.equals("0")) {
            break;

          } else {
            displayTipe();
            tipe = sc.nextInt();

            if (tipe == 1) {
              System.out.print(">Masukkan N: ");
              n = sc.nextInt();
              System.out.println(">Matriks: ");
              a = new Matrix(n, n);
              a.readMatrix();
            } else {
              System.out.print(">Masukkan path file: ");
              String path = in.nextLine();
              f = new IOFile(path);
              a = f.readFile();
            }

            switch (pilihan) {
              case "1":
                hasil = Determinan.detCofactor(a);
                System.out.println();

                message = "Determinan Kofaktor = " + hasil;
                System.out.println(message);
                IOFile.saveFile(message);
                break;
              case "2":
                hasil = Determinan.detReduksi(a);
                System.out.println();
                message = "Determinan Reduksi = " + hasil;
                System.out.println(message);
                IOFile.saveFile(message);
                break;
            }
            System.out.println();
            System.out.println("==================================");
            System.out.println("Apakah anda ingin sudahi program?");
            System.out.println("1. Ya");
            System.out.println("0. Kembali ke menu utama");
            System.out.println("==================================");
            System.out.print(">Masukan: ");
            pilihan = in.nextLine();

            switch (pilihan) {
              case "1":
                isRun = false;
                break;
              case "0":
                break;
              default:
                isRun = false;
                break;
            }

            break;
          }

        case "3":
          System.out.println();
          System.out.println("============== MATRIKS BALIKAN ==============");
          System.out.println("1. Metode Gauss-Jordan");
          System.out.println("2. Metode Adjoint");
          System.out.println("0. Kembali ke menu utama");
          System.out.println("============== MATRIKS BALIKAN ==============");
          System.out.print(">Masukan: ");
          pilihan = in.nextLine();

          if (pilihan.equals("0")) {
            break;

          } else {
            displayTipe();
            tipe = sc.nextInt();

            if (tipe == 1) {
              System.out.print(">Masukkan N: ");
              n = sc.nextInt();
              System.out.println(">Matriks: ");
              a = new Matrix(n, n);
              a.readMatrix();
            } else {
              System.out.print(">Masukkan path file: ");
              String path = in.nextLine();
              f = new IOFile(path);
              a = f.readFile();
            }

            System.out.println();
            switch (pilihan) {
              case "1":
                hasil = Determinan.detCofactor(a);
                if (hasil == 0) {
                  temp = "Tidak ada matrix balikan karena determinan = 0";
                  System.out.println(temp);
                  IOFile.saveFile(temp);
                } else {
                  hasilM = Inverse.adjoinInverse(a);
                  System.out.println("Hasil Inverse Gauss-Jordan: ");
                  hasilM.displayMatrix();
                  IOFile.saveFileInverse(hasilM);
                }
                break;

              case "2":
                hasil = Determinan.detCofactor(a);
                if (hasil == 0) {
                  temp = "Tidak ada matrix balikan karena determinan = 0";
                  System.out.println(temp);
                  IOFile.saveFile(temp);
                } else {
                  hasilM = Inverse.gaussInverse(a);
                  System.out.println("Hasil Inverse Adjoint: ");
                  hasilM.displayMatrix();
                  IOFile.saveFileInverse(hasilM);
                }
                break;
            }
            System.out.println();
            System.out.println("==================================");
            System.out.println("Apakah anda ingin sudahi program?");
            System.out.println("1. Ya");
            System.out.println("0. Kembali ke menu utama");
            System.out.println("==================================");
            System.out.print(">Masukan: ");
            pilihan = in.nextLine();

            switch (pilihan) {
              case "1":
                isRun = false;
                break;
              case "0":
                break;
              default:
                isRun = false;
                break;
            }

            break;
          }

        case "4":
          displayTipe();
          tipe = sc.nextInt();

          if (tipe == 1) {
            System.out.print(">Masukkan jumlah data: ");
            n = sc.nextInt();
            a = new Matrix(n, 2);
            System.out.println(">Masukkan Data: ");
            a.readMatrix();
            System.out.print(">Masukkan nilai yang ditaksir: ");
            x = sc.nextDouble();
          } else {
            System.out.print(">Masukkan path file: ");
            String path = in.nextLine();
            f = new IOFile(path);
            a = f.readFile();
            System.out.print(">Masukkan nilai yang ditaksir: ");
            x = sc.nextDouble();
          }

          InterpolasiRegresi.interpolasiSPL(a, x);

          System.out.println();
          System.out.println("==================================");
          System.out.println("Apakah anda ingin sudahi program?");
          System.out.println("1. Ya");
          System.out.println("0. Kembali ke menu utama");
          System.out.println("==================================");
          System.out.print(">Masukan: ");
          pilihan = in.nextLine();

          switch (pilihan) {
            case "1":
              isRun = false;
              break;
            case "0":
              break;
            default:
              isRun = false;
              break;
          }
          break;

        case "5":
          displayTipe();
          tipe = sc.nextInt();
          if (tipe == 1) {
            System.out.print(">Masukkan jumlah peubah x: ");
            n = sc.nextInt();
            System.out.print(">Masukkan jumlah data sampel: ");
            m = sc.nextInt();
            System.out.println(">Masukkan data sampel: ");
            a = new Matrix(m, n + 1);
            a.readMatrix();
            System.out.println(">Masukkan nilai X yang akan ditaksir: ");
            k = new Matrix(1, n);
            k.readMatrix();
          } else {
            System.out.print(">Masukkan path file: ");
            String path = in.nextLine();
            f = new IOFile(path);
            a = f.readFile();
            k = new Matrix(1, a.getCol() - 1);
            System.out.println(">Masukkan nilai X yang akan ditaksir: ");
            k.readMatrixRegresi(a.getCol() - 1);
          }

          InterpolasiRegresi.regresiGandaSPL(a, k);

          System.out.println();
          System.out.println("==================================");
          System.out.println("Apakah anda ingin sudahi program?");
          System.out.println("1. Ya");
          System.out.println("0. Kembali ke menu utama");
          System.out.println("==================================");
          System.out.print(">Masukan: ");
          pilihan = in.nextLine();

          switch (pilihan) {
            case "1":
              isRun = false;
              break;
            case "0":
              break;
            default:
              isRun = false;
              break;
          }
          break;

        case "6":
          isRun = false;
          break;
        default:
          isRun = false;
          break;
      }
    }

    System.out.println();
    System.out.println("==========================================");
    System.out.println("      (^///^) SELAMAT TINGGAL (^///^)     ");
    System.out.println("==========================================");

  }

}
