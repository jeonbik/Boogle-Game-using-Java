import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
    public static void main(String [] args)
    {
        Trie trie = new Trie();
        List<Node> node = new ArrayList<Node>();
        //Scanner scan = new Scanner(System.in);
        int numOfDictionaryWords;
        int numOfBoggleBoards=0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, Integer> scoreSet = new HashMap<Integer, Integer>();
        scoreSet.put(1,0);
        scoreSet.put(2,0);
        scoreSet.put(3,1);
        scoreSet.put(4,1);
        scoreSet.put(5,2);
        scoreSet.put(6,3);
        scoreSet.put(7,5);
        scoreSet.put(8,11);
        //System.out.println("Enter the num of dictionary words: ");
        try{
                numOfDictionaryWords = Integer.parseInt(reader.readLine());
                for(int i = 0; i < numOfDictionaryWords; i++)
                {
                    String dictionary = reader.readLine();
                    if(dictionary.length()<=8)
                        trie.insertWord(dictionary);
                }
                reader.readLine();
                numOfBoggleBoards = Integer.parseInt(reader.readLine());
            }
        catch(IOException e)
            {
                e.printStackTrace();
            }

        String[][] boggle = new String[numOfBoggleBoards][4];
        try{
            
            for(int i = 0; i < numOfBoggleBoards; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    boggle[i][j] = reader.readLine();
                }
                if(i!=numOfBoggleBoards-1)
                    reader.readLine();
            }
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
        for(int k = 0; k < numOfBoggleBoards; k++)
        {   
            for(int i = 0; i < 4; i++)
            {

                for(int j = 0; j < 4; j++)
                {
                    node.add(new Node(boggle[k][i].charAt(j)));
                }

            }

            for(int i =0; i < 12; i++)
            {
                
                if(i==3||i==7||i==11)
                {
                    node.get(i).addneighbours(node.get(i+4));
                }
                else
                {
                    node.get(i).addneighbours(node.get(i+1));
                    node.get(i).addneighbours(node.get(i+4));
                    node.get(i).addneighbours(node.get(i+5));
                }
            }

            for(int i =4; i < 16; i++)
            {
                
                if(i==4||i==8||i==12)
                {
                    node.get(i).addneighbours(node.get(i-4));
                    
                }
                else
                {
                    node.get(i).addneighbours(node.get(i-5));
                    node.get(i).addneighbours(node.get(i-4));
                    node.get(i).addneighbours(node.get(i-1));
                }
            }

            for(int i =1; i < 12; i++)
            {
                    node.get(i).addneighbours(node.get(i-1));
                    node.get(i).addneighbours(node.get(i+3));
                    node.get(i).addneighbours(node.get(i+4));
                    if(i==3||i==7||i==11)
                    {
                        i++;
                    }
            }

            for(int i =4; i < 16; i++)
            {

                    node.get(i).addneighbours(node.get(i-4));
                    node.get(i).addneighbours(node.get(i-3));
                    node.get(i).addneighbours(node.get(i+1));

                    if(i==6||i==10||i==14)
                    {
                        i++;
                    }
            }
            
            String string = "";
            HashMap<String, Integer> stringSets = new HashMap<String, Integer>();
            for(int i =0; i < 16; i++)
            {
                HashMap<String, Integer> stringSet = new HashMap<String, Integer>();
                
                TrieNode root = trie.root;
                stringSets.putAll(dfs(root, node.get(i), string+node.get(i).data, trie, stringSet));
                
                
                for(int j =0; j < 16; j++)
                {
                    node.get(j).visited=false;
                }


            }
            int maxLength = 0;
            int maxScore = 0;
            int wordCount = 0;
            String longestWord = null;
            for(String str: stringSets.keySet())
            {
                if(str.length() > maxLength)
                {
                    maxLength = str.length();
                    longestWord = str;
                }
                if(str.length() == maxLength)
                {
                    if(longestWord.compareTo(str)>0)
                    {
                        longestWord = str;
                    }
                }
                maxScore += scoreSet.get(str.length());
                wordCount++;
            }
            System.out.println(maxScore + " " + longestWord + " " + wordCount);
            stringSets.clear();
            node.clear();

        }
    }

    public static HashMap<String, Integer> dfs(TrieNode root, Node node, String string, Trie trie, HashMap<String, Integer> stringSet)
    {
        
        List<Node> neighbours=node.getNeighbours();
        node.visited=true;
        int indexed = node.data - 'A';

        if(root.arr[indexed]!=null){

            for (int i = 0; i < neighbours.size(); i++) {
                Node n=neighbours.get(i);
                int index = n.data - 'A';
                TrieNode temp=root.arr[indexed];

                if(temp.arr[index] != null)
                {

                    if(trie.searchPrefix(string+n.data)){
                    if(n!=null && !n.visited)
                        {
                            
                            if(temp.arr[index].end==true)
                                stringSet.put(string + n.data, (string + n.data).length());
                            dfs(temp, n, string+n.data, trie, stringSet);
                        }
                    }

                }
                

            }
            node.visited = false;
        }

        return stringSet;
    }
}