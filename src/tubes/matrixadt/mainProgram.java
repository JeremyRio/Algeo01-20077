package tubes.matrixadt;

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

    M = new Matrix(3, 4);
    F = new IOFile("test//testing.txt");
    F.readFile(M);
    // M.readMatrix();
    M.displayMatrix();
    System.out.println("\n!====!");
    Determinan d = new Determinan();
    // det = d.detReduksi(M);
    // System.out.println("A");
    // System.out.println(det);
    M.inverseSPL(M);
  }

}
