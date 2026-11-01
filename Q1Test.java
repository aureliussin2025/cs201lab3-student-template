public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     static class Node {
          int value;
          Node left, right;

          Node(int value) {
               this.value = value;
          }
     }

     public static String verify(String traversal1, String traversal2, String traversal3) {
          try {
               int[][] traversals = {
                         parse(traversal1), parse(traversal2), parse(traversal3)
               };

               if (!sameValues(traversals[0], traversals[1])
                         || !sameValues(traversals[0], traversals[2])) {
                    return "Invalid traversals";
               }

               int[][] orders = {
                         {0, 1, 2}, {0, 2, 1}, {1, 0, 2},
                         {1, 2, 0}, {2, 0, 1}, {2, 1, 0}
               };
               String[] names = {"Inorder", "Preorder", "Postorder"};

               for (int[] order : orders) {
                    int preorderIndex = indexOf(order, 1);
                    Node root = build(traversals[preorderIndex]);
                    java.util.ArrayList<java.util.ArrayList<Integer>> actual =
                              new java.util.ArrayList<>();

                    for (int i = 0; i < 3; i++) {
                         actual.add(new java.util.ArrayList<>());
                    }

                    inorder(root, actual.get(0));
                    preorder(root, actual.get(1));
                    postorder(root, actual.get(2));

                    boolean valid = true;
                    for (int i = 0; i < 3; i++) {
                         if (!matches(traversals[i], actual.get(order[i]))) {
                              valid = false;
                              break;
                         }
                    }

                    if (valid) {
                         return "Traversal 1 - " + names[order[0]]
                                   + ", Traversal 2 - " + names[order[1]]
                                   + ", Traversal 3 - " + names[order[2]];
                    }
               }
          } catch (Exception e) {
               return "Invalid traversals";
          }

          return "Invalid traversals";
     }

     static int[] parse(String traversal) {
          String[] parts = traversal.split("-");
          int[] values = new int[parts.length];
          java.util.HashSet<Integer> seen = new java.util.HashSet<>();

          for (int i = 0; i < parts.length; i++) {
               values[i] = Integer.parseInt(parts[i].trim());
               if (!seen.add(values[i])) {
                    throw new IllegalArgumentException();
               }
          }

          return values;
     }

     static boolean sameValues(int[] first, int[] second) {
          if (first.length != second.length) {
               return false;
          }

          java.util.HashSet<Integer> values = new java.util.HashSet<>();
          for (int value : first) {
               values.add(value);
          }
          for (int value : second) {
               if (!values.remove(value)) {
                    return false;
               }
          }

          return values.isEmpty();
     }

     static int indexOf(int[] order, int value) {
          for (int i = 0; i < order.length; i++) {
               if (order[i] == value) {
                    return i;
               }
          }
          return -1;
     }

     static Node build(int[] values) {
          Node root = null;
          for (int value : values) {
               root = insert(root, value);
          }
          return root;
     }

     static Node insert(Node node, int value) {
          if (node == null) {
               return new Node(value);
          }
          if (value < node.value) {
               node.left = insert(node.left, value);
          } else {
               node.right = insert(node.right, value);
          }
          return node;
     }

     static void inorder(Node node, java.util.ArrayList<Integer> values) {
          if (node != null) {
               inorder(node.left, values);
               values.add(node.value);
               inorder(node.right, values);
          }
     }

     static void preorder(Node node, java.util.ArrayList<Integer> values) {
          if (node != null) {
               values.add(node.value);
               preorder(node.left, values);
               preorder(node.right, values);
          }
     }

     static void postorder(Node node, java.util.ArrayList<Integer> values) {
          if (node != null) {
               postorder(node.left, values);
               postorder(node.right, values);
               values.add(node.value);
          }
     }

     static boolean matches(int[] expected, java.util.ArrayList<Integer> actual) {
          if (expected.length != actual.size()) {
               return false;
          }
          for (int i = 0; i < expected.length; i++) {
               if (expected[i] != actual.get(i)) {
                    return false;
               }
          }
          return true;
     }
}
