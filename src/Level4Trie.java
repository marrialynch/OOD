import java.util.*
public class Level4Trie {

  public static void main(String[] args) {

  }

  private static class CloudStorage {

  }

  private static class TrieFileSystem {
    private static String CREATED = "created";
    private static String OVERWRITTEN = "overwritten";

    private static String EMPTY = "";
    private final Node root;
    public TrieFileSystem() {
      this.root = new Node(' ');
    }

    public String addFile(File file) {
      Node cur = root;
      String res = CREATED;
      for (int i = 0; i < file.filename.length(); i++) {
        char c = file.filename.charAt(i);
        if (cur.children.containsKey(c)) {
          cur = cur.children.get(c);
          if (i == file.filename.length() - 1) {
            res = OVERWRITTEN;
          }
        } else {
          Node newNode = new Node(c);
          cur.children.put(c, newNode);
          cur = newNode;
        }
      }
      // root to cur is the filename
      cur.name = file.filename;
      cur.fileSystem.add(file);
      return res;
    }


    public String getFileSize(String filename) {
      Node node = this.findNode(filename);
      if (node.isFileNode() && node.getLatestFile() != null) {
        return String.valueOf(node.getLatestFile().size);
      }
      return EMPTY;
    }

    public boolean copyFile(String fromName, String toName) {
      Node fromFile = this.findNode(fromName);
      Node toFile = this.findNode(toName);
      if (fromFile == null || !fromFile.isFileNode()
        || (toFile != null && toFile.isFileNode())) {
        return false;
      }

      return true;
    }

    private Node findNode(String prefix) {
      Node cur = root;
      Node result = null;
      for (int i = 0; i < prefix.length(); i++) {
        if (cur.children.containsKey(prefix.charAt(i))) {
          cur = cur.children.get(prefix.charAt(i));
          if (i == prefix.length() - 1) {
            result = cur;
          }
        } else {
          break;
        }
      }
      return result;
    }

  }
  private static class Node {
    private Character c;

    private HashMap<Character, Node> children;
    private String name;
    private List<File> fileSystem;
    private List<File> trash;

    public Node(char c) {
      this.c = c;
      this.children = new HashMap<>();
    }

    public boolean isFileNode() {
      return name != null;
    }

    public File getLatestFile() {
      if (fileSystem.isEmpty()) {
        return null;
      }
      return this.fileSystem.get(this.fileSystem.size() - 1);
    }
  }

  private static class File {
    private final String filename;
    private final int size;

    public File(String filename, int size) {
      this.filename = filename;
      this.size = size;
    }
  }
}
