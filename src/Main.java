public class Main {

  public static void main(String[] args) {


  }

  public static class File {
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