
// Huffman Coding in Java
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Map;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class HuffmanNode {
  int item;
  char c;
  HuffmanNode left;
  HuffmanNode right;
}

// For comparing the nodes
class ImplementComparator implements Comparator<HuffmanNode> {
  public int compare(HuffmanNode x, HuffmanNode y) {
    return x.item - y.item;
  }
}

// IMplementing the huffman algorithm
public class Huffman {
  static LinkedHashMap<Character, String> dict = new LinkedHashMap();

  public static void printCode(HuffmanNode root, String s) {
    if (root.left == null && root.right == null) {

      System.out.println(root.c + "   |  " + s);
      if (dict.containsKey(root.c)) {
        return;
      } else {
        dict.put(root.c, s);
      }
      return;
    }
    printCode(root.left, s + "0");
    printCode(root.right, s + "1");

  }

  public static String readFile(String path, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  public static void main(String[] args) throws IOException {
    LinkedHashMap<Character, Integer> map = new LinkedHashMap();

    String text = readFile("D:\\year3\\sem5\\ADSA\\Huffman_Encoding\\Julius_Caesar.txt", StandardCharsets.UTF_8);
    for (char c : text.toCharArray()) {
      if (map.containsKey(c)) {
        map.put(c, ((int) map.get(c)) + 1);
      } else {
        map.put(c, 1);
      }
    }

    Character[] charArray = new Character[map.size()];
    Integer[] charfreq = new Integer[map.size()];
    int index = 0;
    for (Map.Entry<Character, Integer> mapEntry : map.entrySet()) {
      charArray[index] = mapEntry.getKey();
      charfreq[index] = mapEntry.getValue();
      index++;
    }
    int n = map.size();
    PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new ImplementComparator());
    for (int i = 0; i < n; i++) {
      HuffmanNode hn = new HuffmanNode();

      hn.c = charArray[i];
      hn.item = charfreq[i];

      hn.left = null;
      hn.right = null;

      q.add(hn);
    }

    HuffmanNode root = null;

    while (q.size() > 1) {

      HuffmanNode x = q.peek();
      q.poll();

      HuffmanNode y = q.peek();
      q.poll();

      HuffmanNode f = new HuffmanNode();

      f.item = x.item + y.item;
      f.c = '-';
      f.left = x;
      f.right = y;
      root = f;

      q.add(f);
    }
    System.out.println(" Char | Huffman code ");
    System.out.println("--------------------");
    printCode(root, "");
    System.out.println(dict);
    String encodedString = "";
    for (char c : text.toCharArray()) {
      encodedString += dict.get(c);
    }
    System.out.println(encodedString);
    System.out.println("Original String size: " + text.length() * 8);
    System.out.println("Compressed String size: " + encodedString.length());
    float compressionratio = (float) text.length() * 8 / encodedString.length();
    System.out.println("Compression ratio: " + compressionratio);
  }
}