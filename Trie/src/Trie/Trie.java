package Trie;

//package trie;

import java.util.ArrayList;

/**
* This class implements a Trie.
*
*/
public class Trie {

 // prevent instantiation
 private Trie() { }

 /**
 * Builds a trie by inserting all words in the input array, one at a time,
 * in sequence FROM FIRST TO LAST */
 public static TrieNode buildTrie(String[] allWords)
 {
     /** COMPLETE THIS METHOD **/
     TrieNode root = new TrieNode(null, null, null);
     if(allWords.length==0) return root;
    
     TrieNode firstWord = new TrieNode(new Indexes(0, (short)0 , (short) (allWords[0].length()-1)), null, null); //Creates the first Node with full first word
     root.firstChild = firstWord;
    
     TrieNode ptr = root.firstChild;
     TrieNode prev= null;
    
     for(int i = 1; i < allWords.length; i++)
     {
         String prefix = "";
         String insert = allWords[i];
        
         while(ptr != null)
         {
             int wordIndex = ptr.substr.wordIndex;
             int startIndex = ptr.substr.startIndex;
             int endIndex = ptr.substr.endIndex;
             String nodePrefix = allWords[wordIndex].substring(startIndex, endIndex+1);
             if(insert.indexOf(prefix+nodePrefix) == 0)
             { //If the ptr perfectly fits the word
                 prefix = prefix + nodePrefix;
                 prev = ptr;
                 ptr = ptr.firstChild;
                 continue;
             }
            
             if(insert.charAt(startIndex) != nodePrefix.charAt(0))
             { // If the ptr does not fit the word at all, move on to sibling
                 prev = ptr;
                 ptr = ptr.sibling;
                 continue;
             }
            
             int count = 0;
             for(int j = 0; j < nodePrefix.length(); j++)
             {//Else we gotta find the common occurences cuz we need to change stuff
                 if(insert.charAt(startIndex+j) == nodePrefix.charAt(j)) count++;
                 else break;
             }
             int clipped = startIndex + count;
            
             TrieNode tempNode = new TrieNode(new Indexes(wordIndex, (short)clipped, (short)endIndex), ptr.firstChild, null);
             ptr.firstChild = tempNode;
             ptr.substr.endIndex = (short) (clipped-1);
            
             prefix = prefix + allWords[wordIndex].substring(startIndex, clipped);
            
             prev = ptr;
             ptr = ptr.firstChild;
         }
        
        
         //ptr = null, so we just gotta attach whatever is left
         Indexes finalInd = new Indexes(i, (short)(prefix.length()), (short)(insert.length()-1));
         TrieNode adding = new TrieNode(finalInd, null, null);
        
         prev.sibling = adding; //You can only add as a sibling of something
        
         ptr = root.firstChild; //Reset
         prev = null;
     }
    
     return root;
 }

 public static ArrayList<TrieNode> completionList(TrieNode root,String[] allWords, String prefix)
 {
     /** COMPLETE THIS METHOD **/
     ArrayList<TrieNode> completionList = new ArrayList<>();
     TrieNode ptr = root.firstChild, prev = null;
     String totPrefix = "";
    
     while(prefix.length() > totPrefix.length() && ptr != null)
     {
         int wordIndex = ptr.substr.wordIndex;
         int startIndex = ptr.substr.startIndex;
         int endIndex = ptr.substr.endIndex;
         prev = ptr;
         String curPrefix = allWords[wordIndex].substring(startIndex, endIndex+1);
         if(prefix.indexOf(totPrefix+curPrefix) == 0)
         {
             totPrefix = totPrefix + curPrefix;
             ptr = ptr.firstChild;
             continue;
         }
         if((totPrefix+curPrefix).indexOf(prefix) == 0)
         {
             ptr = ptr.firstChild;
             break;
         }
        
         ptr = ptr.sibling;
     }
     if(ptr == null)
     { //We somehow exited the tree. Either nothing was in common or the prefix was the actual word
         if(allWords[prev.substr.wordIndex].indexOf(prefix) == 0)
         {
             completionList.add(prev);
             return completionList;
         }
         return null;
     }
    
     //At this point, we want every leaf node of this cihld and the leaf nodes of all the siblings of ptr
     //The parent of ptr is the lowest level that Prefix has in common in the tree
     addWords(completionList, ptr);
     // FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
     // MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
     return completionList;
 }
 private static void addWords(ArrayList<TrieNode> completionList, TrieNode start)
 {
     if(start == null) return;
     if(start.firstChild == null)
     {
         completionList.add(start);
         addWords(completionList, start.sibling);
         return;
     }
     addWords(completionList, start.firstChild);
     addWords(completionList, start.sibling);
    
 }

 public static void print(TrieNode root, String[] allWords)
 {
     System.out.println("\nTRIE\n");
     print(root, 1, allWords);
 }

 private static void print(TrieNode root, int indent, String[] words)
 {
     if (root == null)
     {
         return;
     }
     for (int i=0; i < indent-1; i++)
     {
         System.out.print("    ");
     }
    
     if (root.substr != null)
     {
         String pre = words[root.substr.wordIndex]
                         .substring(0, root.substr.endIndex+1);
         System.out.println("      " + pre);
     }
    
     for (int i=0; i < indent-1; i++)
     {
         System.out.print("    ");
     }
     System.out.print(" ---");
     if (root.substr == null)
     {
         System.out.println("root");
     } else {
         System.out.println(root.substr);
     }
    
     for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling)
     {
         for (int i=0; i < indent-1; i++)
         {
             System.out.print("    ");
         }
         System.out.println("     |");
         print(ptr, indent+1, words);
     }
 }
}