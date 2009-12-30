package jhd;
import jhd.parser.ConvertToHtml;

class Main {

  private void line() {
    for (int i = 0; i < 80; i++) {
      System.out.print('*');
    }
  }

  private void copyright() {
    line();
    System.out.print("\t\t\t");
    System.out.println("jHyperDoc");

    System.out.print("\t\t");
    System.out.println("Copyright(c) http://MicroProgrammers.150m.com");

    line();
  }

  public void convert(String[] args){
    copyright();

    long start = System.currentTimeMillis();

    /* Convert the files */
    ConvertToHtml c = new ConvertToHtml(args);
    c.convertToHtml();

    long end = System.currentTimeMillis();
    double diff = end - start;
    double secs = (diff / 1000.0);
    line();
    System.out.println("Finished...");
    System.out.println("Time Taken: " + secs + " Seconds");
    line();
  }

   public static void main(String args[]) {
     Main m = new Main();
     m.convert(args);
   }
}