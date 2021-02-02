package Trie;

//Java program for insertion in AVL Tree 
class Node { 
 int key, height; 
 Node left, right; 

 Node(int d) { 
     key = d; 
     height = 1; 
 } 
} 

class AVLTree { 

 Node root; 

 // A utility function to get the height of the tree 
 int height(Node N) { 
     if (N == null) 
         return 0; 

     return N.height; 
 } 

 // A utility function to get maximum of two integers 
 int max(int a, int b) { 
     return (a > b) ? a : b; 
 } 

 // A utility function to right rotate subtree rooted with y 
 // See the diagram given above. 
 Node rightRotate(Node y) { 
     Node x = y.left; 
     Node T2 = x.right; 

     // Perform rotation 
     x.right = y; 
     y.left = T2; 

     // Update heights 
     y.height = max(height(y.left), height(y.right)) + 1; 
     x.height = max(height(x.left), height(x.right)) + 1; 

     // Return new root 
     return x; 
 } 

 // A utility function to left rotate subtree rooted with x 
 // See the diagram given above. 
 Node leftRotate(Node x) { 
     Node y = x.right; 
     Node T2 = y.left; 

     // Perform rotation 
     y.left = x; 
     x.right = T2; 

     //  Update heights 
     x.height = max(height(x.left), height(x.right)) + 1; 
     y.height = max(height(y.left), height(y.right)) + 1; 

     // Return new root 
     return y; 
 } 

 // Get Balance factor of node N 
 int getBalance(Node N) { 
     if (N == null) 
         return 0; 

     return height(N.left) - height(N.right); 
 } 
}
