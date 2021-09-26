
package tubes.matrixadt;

import java.util.*;
import java.io.*;

public class IOFile {
  private Scanner file;
  private String fileName;

  public IOFile(String fileName) {
    this.fileName = fileName;
  }

  public void openFile() {
    try {
      this.file = new Scanner(new File(this.fileName));
    } catch (Exception e) {
      System.out.println("Tidak ada nama File tersebut.");
    }
  }

  public void closeFile() {
    file.close();
  }

  public void readFile(Matrix M) {
    // KAMUS LOKAL
    int i, j;
    // ALGORITMA
    openFile();
    while (file.hasNext()) {
      for (i = 0; i < M.getRow(); i++) {
        for (j = 0; j < M.getCol(); j++) {
          M.setELMT(i, j, file.nextFloat());
        }
      }
    }
    file.close();
  }

}
