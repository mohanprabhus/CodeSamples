package com.example.tree;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


enum OperatorEnum{
    PLUS, MULTIPLY
}

class OperatorNode extends TreeNode<OperatorEnum> {
    public OperatorNode(OperatorEnum content) {
        super(content);
    }
}

class NumberNode extends TreeNode<Integer> {
    public NumberNode(Integer content) {
        super(content);
    }
}



public class TreeTest {

   // The tree is represented as a hierarchical set of TreeNode objects
   private TreeNode<String> tree;


   public void createSubTree(TreeNode<String> parent, int childCount, int levelCount, String label) {

      String newLabel;

      for (int cCount = 1;  cCount <= childCount;  cCount++) {
         newLabel = label + '.' + cCount;
         TreeNode<String> child = new TreeNode<>(newLabel);
         parent.addChildren(child);
         if (levelCount > 1) {
            createSubTree(child, childCount, levelCount - 1, newLabel);
         }
      }
   }


   /**
    *  Creates a tree of a specific size.
    *
    * @param childCount The number of children each node has
    * @param depth      The number of levels of the tree
    *
    * @return A TreeNode as the root node of the tree
    */
   public TreeNode<String> createTree(int childCount, int depth) {
      tree = new TreeNode<>("Root");
      createSubTree(tree, childCount, depth, "Node");

      return tree;
   }


   @Test
   public void renderTest()  {
      // Create a sample tree
      TreeNode<String> tree = createTree(3, 3);

      @SuppressWarnings("serial")
      List<TreeNode<String>> path = new ArrayList<TreeNode<String>>() {{
                         add(new TreeNode<String>("Node.2")); 
                         add(new TreeNode<String>("Node.2.3"));
                         add(new TreeNode<String>("Node.2.3.1"));
                      }};

      TreeNode<String> node = tree.findNode(path);

      System.err.println(node.getPathString());
      for (int i = 0;  i < 500;  i++) {
         node.addChildren(new TreeNode<String>("Node.2.3.1." + i));
      }

      // visualize the tree using plain old ASCII
      TreeAsciiRenderer tv = new TreeAsciiRenderer(System.err);
      tv.renderHierarchical(tree);
      tv.renderFlat(tree);
   }
   

   @Test
   public void visitorTest() {
       TreeNode<String> tree = createTree(3, 3);

       TreeNodeVisitor<String> visitor = new TreeNodeOutputVisitor<>();
       TreeTraversal<String> tt1 = new DepthFirstTraversal<>();
       tt1.traversePreOrder(tree, visitor);

       TreeTraversal<String> tt2 = new BreadthFirstTraversal<>();
       tt2.traversePreOrder(tree, visitor);

       TreeNodeCounterVisitor<String> visitor2 = new TreeNodeCounterVisitor<>();
       tt1.traversePreOrder(tree,  visitor2);;
       System.err.println("Number of nodes: " + visitor2.getResult());

       // visit() on the visitor implements the operation

       // accept() on the element implements the traversal (!)
       // here we modified the pattern to allow to implement different traversal
       // algorithms. Hence, the TreeNodeTraversal.traverse() takes the 
       // role of the accept() method.
     }

   
   @Test
   public void expressionTest() {
       OperatorNode top = new OperatorNode(OperatorEnum.PLUS);
       OperatorNode left = new OperatorNode(OperatorEnum.MULTIPLY);
       OperatorNode right = new OperatorNode(OperatorEnum.MULTIPLY);
//       left.addChildren(new NumberNode(2));
//       left.addChildren(new NumberNode(4));
//       right.addChildren(new NumberNode(6));
//       right.addChildren(new NumberNode(8));
       top.addChildren(left);
       top.addChildren(right);

       TreeNodeVisitor<String> visitor = new TreeNodeExpressionVisitor<>();
       TreeTraversal<String> tt1 = new DepthFirstTraversal<>();
       tt1.traversePreOrder(tree, visitor);
     }


   
   @Test
   public void iteratorTest() {
       TreeNode<String> tree = createTree(3, 3);

       TreeNodeIterator<String> iter = new TreeNodeIterator<>(tree);

       System.err.println(iter.next());
       System.err.println(iter.next());
       System.err.println(iter.next());

       System.err.println(iter.prev());
       System.err.println(iter.prev());
       System.err.println(iter.prev());
       System.err.println(iter.prev());
       
       System.err.println(iter.next());
       System.err.println(iter.next());
   }

}
