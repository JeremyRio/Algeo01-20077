package tubes.matrixadt;

import java.util.Scanner;
import java.io.*;

/**
 *
 * @author Jeremy
 */

class mainProgram {

  public static void main(String[] args) {
    Matrix M;
    IOFile F;
    float det;

    /*
     * try { File file = new File("test//haha.txt"); if (!file.exists()) {
     * file.createNewFile(); } } catch (IOException e) { e.printStackTrace(); }
     */
    float x = 9.2f;
    int N = 3;
    M = new Matrix(N, 2);
    F = new IOFile("test//testing.txt");
    // F.readFile(M);
    // M.readMatrix();
    M.displayMatrix();
    System.out.println("\n!====!");
    InterpolasiRegresi inter = new InterpolasiRegresi();
    inter.interpolasiSPL(M, N, x);
    // Determinan d = new Determinan();
    // det = d.detReduksi(M);
    // System.out.println("A");
    // System.out.println(det);
    // M.inverseSPL(M);
    char a = 'r';
    System.out.println(a);
  }

}
