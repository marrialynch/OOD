import java.util.HashMap;

public class Level1 {
  //Level 1 - 3 functions


  public static void main(String[] args) {

  }

  public static class CloudStorage {
    private static String CREATED = "created";
    private static String OVERWRITTEN = "overwritten";

    private static String EMPTY = "";
    HashMap<String, Integer> map = new HashMap<>();

    public String ADD_FILE(String fileName, int size) {
      if (map.put(fileName, size) == null) {
        return CREATED;
      }
      return OVERWRITTEN;
    }

    // string representing the size of the file
    public String GET_FILE_SIZE(String fileName) {
      Integer res = map.get(fileName);
      if (res == null) {
        return EMPTY;
      }
      return res.toString();
    }

    public  boolean MOVE_FILE(String nameFrom, String nameTo) {
        if (map.get(nameFrom) == null || map.get(nameTo) != null ) {
          return false;
        }
        map.put(nameTo, map.get(nameFrom));
        map.remove(nameFrom);
        return true;
    }
  }
}
