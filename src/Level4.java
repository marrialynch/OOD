
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Level4 {

  public static void main(String[] args) {
    CloudStorage4 cs = new CloudStorage4();
    String test1 = cs.ADD_FILE("/dir1/dir2/a.text", 3);
    System.out.println(test1);// created
    String test2 = cs.ADD_FILE("/dir1/b.text", 1);
    System.out.println(test2);// created
    String test3 = cs.DELETE_FILES("/dir1");
    System.out.println(test3);// 2
    String test4 = cs.GET_FILE_SIZE("/dir1/dir2/a.text");
    System.out.println(test4);// ""
    String test5 = cs.RESTORE_FILES("/dir1/dir2");
    System.out.println(test5);//1
    String test6 = cs.GET_FILE_SIZE("/dir1/dir2/a.text");
    System.out.println(test6);// 3
  }

  public static class CloudStorage4 {
    private static String CREATED = "created";
    private static String OVERWRITTEN = "overwritten";

    private static String EMPTY = "";
    private HashMap<String, List<File>> map;
    private HashMap<String, List<File>> trash;


    public CloudStorage4() {
      this.map = new HashMap<>();
      this.trash = new HashMap<>();
    }

    public String DELETE_FILES(String prefix) {
      List<String> removeFiles = new ArrayList<>();

      // permanently delete trash
      for (String s : trash.keySet()) {
        if (s.startsWith(prefix)) {
          removeFiles.add(s);
        }
      }
      for (String file : removeFiles) {
        trash.remove(file);
      }

      // delete from existing
      int counter = 0;
      List<String> deleteFiles = new ArrayList<>();
      for (String s : map.keySet()) {
        if (s.startsWith(prefix)) {
          counter++;
          deleteFiles.add(s);
        }
      }

      for (String s : deleteFiles) {
        trash.put(s, map.get(s));
        map.remove(s);
      }
      return String.valueOf(counter);
    }

    public String RESTORE_FILES(String prefix) {
      int counter = 0;
      List<String> restoreFiles = new ArrayList<>();
      for (String s : trash.keySet()) {
        if (s.startsWith(prefix)) {
          restoreFiles.add(s);
          counter++;
        }
      }
      System.out.println("resttoreFiles:" + restoreFiles.toString());
      for (String s : restoreFiles) {
        map.put(s, trash.get(s));
        trash.remove(s);
      }
      return String.valueOf(counter);
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
      return String.valueOf(res.get(res.size() - 1).getSize());
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

