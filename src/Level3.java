import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Level3 {

  public static void main(String[] args) {
    CloudStorage3 ct = new CloudStorage3();
    String test1 = ct.ADD_FILE("/file-a.text", 6);
    System.out.println(test1); // created
    String test2 = ct.ADD_FILE("/file-a.text", 3);
    System.out.println(test2); // overwritten
    String test8 = ct.GET_LARGEST_N("/file", 2);
    System.out.println(test8); // file-a.text(3)
    String test3 = ct.GET_VERSION("/file-a.text", 2);
    System.out.println(test3); // 3
    String test4 = ct.GET_VERSION("/file-a.text", 4);
    System.out.println(test4); // ""
    String test5 = ct.GET_VERSION("/file-a.text", 1);
    System.out.println(test5); // 6
    boolean test6 = ct.DELETE_VERSION("/file-a.text", 1);
    System.out.println(test6); // true
    String test7 = ct.GET_VERSION("/file-a.text", 1);
    System.out.println(test7); // 3
  }

  public static class CloudStorage3 {
    private static String CREATED = "created";
    private static String OVERWRITTEN = "overwritten";

    private static String EMPTY = "";
    private HashMap<String, List<File>> map;


    public CloudStorage3() {
      this.map = new HashMap<>();
    }

    public String ADD_FILE(String fileName, int size) {
      if (map.containsKey(fileName)) {
        map.get(fileName).add(new File(fileName, size));
        return OVERWRITTEN;
      } else {
        map.put(fileName, new ArrayList<>());
        map.get(fileName).add(new File(fileName, size));
        return CREATED;
      }
    }

    public  boolean MOVE_FILE(String nameFrom, String nameTo) {
      if (map.get(nameFrom) == null || map.get(nameTo) != null ) {
        return false;
      }
      map.put(nameTo, map.get(nameFrom));
      map.remove(nameFrom);
      return true;
    }

    public String GET_VERSION(String fileName, int version) {
      List<File> res = map.get(fileName);
      if (res == null || res.size() < version) {
        return EMPTY;
      }
      return String.valueOf(res.get(version - 1).getSize());
    }
    public String GET_FILE_SIZE (String fileName) {
      List<File> res = map.get(fileName);
      if ( res == null) {
        return EMPTY;
      }
      return res.get(res.size() - 1).toString();
    }

    public String GET_LARGEST_N(String filePrefix, int n) {
      List<File> list = new ArrayList<>();

      for (String s : map.keySet()) {
        if (s.startsWith(filePrefix)) {
          List<File> versions = map.get(s);
          list.add(versions.get(versions.size() - 1));
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

    public boolean DELETE_VERSION (String fileName, int version) {
      List<File> res = map.get(fileName);
      if (res == null || res.size() < version) {
        return false;
      }
      res.remove(version - 1);
      return true;
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

