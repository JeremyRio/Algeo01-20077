import java.util.*;
import java.io.*;

public class IOFile {
  /* Attribute */
  private Scanner file;
  private String fileName;
  static Scanner in = new Scanner(System.in);
  static Scanner sc = new Scanner(System.in);

  /* Konstruktor */
  public IOFile(String fileName) {
    this.fileName = fileName;
  }

  /* Method */
  public void openFile() {
    try {
      this.file = new Scanner(new File(this.fileName));
    } catch (Exception e) {
      System.out.println("Tidak ada nama File tersebut.");
      System.exit(0);
    }
  }

  public void closeFile() {
    file.close();
  }

  // Membaca banyak baris matrix dalam file
  public int readRow() {
    // KAMUS LOKAL
    int row = 0;
    // ALGORITMA
    openFile();
    while (file.hasNextLine()) {
      row++;
      file.nextLine();
    }
    closeFile();
    return row;
  }

  // Membaca banyak kolom matrix dalam file
  public int readCol() {
    // KAMUS LOKAL
    int col = 0;
    // ALGORITMA
    openFile();
    Scanner lastline = new Scanner(file.nextLine());
    while (lastline.hasNextFloat()) {
      col++;
      lastline.nextFloat();
    }
    closeFile();
    return col;
  }

  // Membaca matrix dari file
  public Matrix readFile() {
    // KAMUS LOKAL
    int i, j, m, n;
    Matrix M;
    // ALGORITMA
    m = readRow();
    n = readCol();
    M = new Matrix(m, n);
    openFile();
    for (i = 0; i < M.getRow(); i++) {
      for (j = 0; j < M.getCol(); j++) {
        M.setELMT(i, j, file.nextFloat());
      }
    }
    closeFile();
    return M;
  }

  // Membaca file matrix untuk Interpolasi dan dilakukan proses Interpolasi
  public Matrix readFileInterpolasi() {
    // KAMUS LOKAL
    int i, j, n;
    Matrix M;
    // ALGORITMA
    n = readRow() - 1;
    openFile();
    M = new Matrix(n, 2);
    for (i = 0; i < M.getRow(); i++) {
      for (j = 0; j < M.getCol(); j++) {
        M.setELMT(i, j, file.nextFloat());
      }
    }
    return M;
  }

  // Membaca Taksiran dari File
  public float readTaksiran() {
    // KAMUS LOKAL
    float x;
    // ALGORITMA
    x = file.nextFloat();
    closeFile();
    return x;
  }

  public Matrix readK(int n) {
    // KAMUS LOKAL
    int i;
    Matrix k = new Matrix(n, 1);
    // ALGORITMA
    for (i = 0; i < k.getRow(); i++) {
      k.setELMT(i, 0, file.nextFloat());
    }
    return k;
  }

  public Matrix readFileRegresi(int n) {
    // KAMUS LOKAL
    int i, j;
    Matrix M;
    // ALGORITMA
    M = new Matrix(n + 1, n + 2);
    for (i = 0; i < M.getRow(); i++) {
      for (j = 0; j < M.getCol(); j++) {
        M.setELMT(i, j, file.nextFloat());
      }
    }
    closeFile();
    return M;
  }

  public static void displaySave() {
    System.out.println();
    System.out.println("Apakah keluaran ingin disimpan dalam folder \"output\"?");
    System.out.println("1. Iya");
    System.out.println("2. Tidak");
    System.out.print(">Masukan: ");
  }

  public static void writeMatrix(Matrix M, String fileName) {
    // KAMUS LOKAL
    int i, j;
    // ALGORITMA
    try {
      PrintWriter out = new PrintWriter(fileName);
      for (i = 0; i < M.row; i++) {
        for (j = 0; j < M.col; j++) {
          out.print(M.getELMT(i, j));
          if (j != M.col - 1) {
            out.print(" ");
          }
        }
        out.println();
      }
      out.close();
    } catch (Exception e) {
      ;
    }
  }

  public static void saveFileInverse(Matrix hasil) {
    // KAMUS LOKAL
    int opsi;
    String nama;
    PrintWriter out;
    // ALGORITMA
    try {
      displaySave();
      opsi = in.nextInt();
      if (opsi == 1) {
        System.out.print(">Nama file (Contoh: test.txt): ");
        nama = sc.nextLine();
        out = new PrintWriter(new File("output/" + nama));
        writeMatrix(hasil, "output/" + nama);
        out.close();
      }
    } catch (Exception e) {
      System.out.println("Error dalam saveFileInverse");
    }
  }

  public static void saveFileSPL(Matrix hasil) {
    // KAMUS LOKAL
    int opsi;
    String nama;
    PrintWriter out;
    // ALGORITMA
    try {
      displaySave();
      opsi = in.nextInt();
      if (opsi == 1) {
        System.out.print(">Nama file (Contoh: test.txt): ");
        nama = sc.nextLine();
        out = new PrintWriter(new File("output/" + nama));
        for (int i = 0; i < hasil.getRow(); i++) {
          out.println("X" + (i + 1) + " = " + hasil.getELMT(i, 0));
        }
        out.close();
      }
    } catch (Exception e) {
      System.out.println("Error dalam saveFileSPL");
    }
  }

  public static void saveFile(String m) {
    // KAMUS LOKAL
    int opsi;
    String nama;
    PrintWriter out;
    // ALGORITMA
    try {
      displaySave();
      opsi = in.nextInt();
      if (opsi == 1) {
        System.out.print(">Nama file (Contoh: test.txt): ");
        nama = sc.nextLine();
        out = new PrintWriter(new File("output/" + nama));
        out.write(m);
        out.close();
      }
    } catch (Exception e) {
      System.out.println("Error dalam saveFile");
    }
  }
}