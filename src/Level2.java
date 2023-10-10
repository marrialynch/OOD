import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class Level2 {

  public static void main(String[] args) {
    CloudStorage2 ct = new CloudStorage2();
    String test1 = ct.ADD_FILE("/dir/file1.txt", 5);
    System.out.println(test1);
    String test2 = ct.ADD_FILE("/dir/file2", 20);
    System.out.println(test2);
    String test3 = ct.ADD_FILE("/dir/deeper/file3.mov", 9);
    System.out.println(test3);
//    System.out.println(ct.GET_LARGEST_N("/dir", 2));
//    System.out.println(ct.GET_LARGEST_N("/dir/file", 3));
    System.out.println(ct.GET_LARGEST_N("/another", 2));

  }

  public static class CloudStorage2 {
    private static String CREATED = "created";
    private static String OVERWRITTEN = "overwritten";

    private static String EMPTY = "";
    private HashMap<String, File> map;


    public CloudStorage2() {
      this.map = new HashMap<>();
    }

    public String ADD_FILE(String fileName, int size) {
      if (map.put(fileName, new File(fileName, size)) == null) {
        return CREATED;
      }
      return OVERWRITTEN;
    }

    // string representing the size of the file
    public String GET_FILE_SIZE(String fileName) {
      if ( map.get(fileName)== null) {
        return EMPTY;
      }
      return String.valueOf(map.get(fileName).getSize());
    }

    public  boolean MOVE_FILE(String nameFrom, String nameTo) {
      if (map.get(nameFrom) == null || map.get(nameTo) != null ) {
        return false;
      }
      map.put(nameTo, new File(nameTo, map.get(nameFrom).getSize()));
      map.remove(nameFrom);
      return true;
    }

    public String GET_LARGEST_N(String filePrefix, int n) {
      List<File> list = new ArrayList<>();

      for (String s : map.keySet()) {
        if (s.startsWith(filePrefix)) {
          list.add(map.get(s));
        }
      }

      if (list.size() == 0) {
        return EMPTY;
      }

      n = Math.min(list.size(), n);

      Collections.sort(list, (file1, file2) -> {
        if (file1.getSize() > file2.getSize()) {
          return -1;
        } else if (file1.getSize() == file2.getSize()) {
          return file1.getName().compareTo(file2.getName());
        } else{
          return 1;
        }
      });

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < n; i++) {
        File cur = list.get(i);
        sb.append(cur.getName());
        sb.append("(");
        sb.append(cur.getSize());
        sb.append(")");
        if (i != n - 1) {
          sb.append(",");
        }
      }

      return sb.toString();
    }
    public static class File{
      private String name;
      private int size;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public int getSize() {
        return size;
      }

      public void setSize(int size) {
        this.size = size;
      }

      public File(String name, int size) {
        this.name = name;
        this.size = size;
      }
    }

  }
}
