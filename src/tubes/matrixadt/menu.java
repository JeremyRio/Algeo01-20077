package tubes.matrixadt;

import java.util.Scanner;

class menu {

  public static void main(String[] args) {
    String pilihan;

    System.out.println("============== MENU MATRIKS ==============");
    System.out.println("1. Sistem Persamaan Linier");
    System.out.println("2. Determinan");
    System.out.println("3. Matriks Balikan");
    System.out.println("4. Interpolasi Polinom");
    System.out.println("5. Regresi linier berganda");
    System.out.println("6. Keluar");
    System.out.println("==========================================");

    Scanner in = new Scanner(System.in);
    int m, n;
    Matrix ab, a;
    Determinan det;
    float hasil;

    System.out.print("Masukan: ");
    pilihan = in.nextLine();
    switch (pilihan) {
      case "1":
        System.out.println("============== SPL ==============");
        System.out.println("1 Metode eleminasi Gauss");
        System.out.println("2 Metode eleminasi Gauss-Jordan");
        System.out.println("3 Metode matriks balikan");
        System.out.println("4 Kaidah Cramer");
        System.out.println("============== SPL ==============");
        System.out.print("Masukan: ");
        pilihan = in.nextLine();
        m = in.nextInt();
        n = in.nextInt();
        ab = new Matrix(m, n);
        ab.readMatrix();
        switch (pilihan) {
          case "1":
            break;
          case "2":
            break;
          case "3":
            break;
          case "4":
            break;
        }
        break;
      case "2":
        System.out.println("\n============== DETERMINAN ==============");
        System.out.println("1 Metode ekspansi kofaktor");
        System.out.println("2 Metode reduksi baris");
        System.out.println("============== DETERMINAN ==============");
        System.out.print("Masukan: ");
        pilihan = in.nextLine();
        n = in.nextInt();
        a = new Matrix(n, n);
        a.readMatrix();
        det = new Determinan();
        switch (pilihan) {
          case "1":
            hasil = det.detCofactor(a);
            System.out.println(hasil);
            break;
          case "2":
            hasil = det.detReduksi(a);
            System.out.println(hasil);
            break;
        }
        break;

      case "3":
        System.out.println("\n============== MATRIKS BALIKAN ==============");
        System.out.println("1 Metode eleminasi Gauss-Jordan");
        System.out.println("2 Metode determinan-adjoint");
        System.out.println("============== MATRIKS BALIKAN ==============");
        break;
      case "4":
        break;
      case "5":
        break;
      case "6":
        break;
      default:
        break;
    }
    /*
     * try { File file = new File("test//haha.txt"); if (!file.exists()) {
     * file.createNewFile(); } } catch (IOException e) { e.printStackTrace(); }
     */

  }

}
