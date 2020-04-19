class TrieNode
{
    TrieNode[] arr;
    boolean end;
    public TrieNode(){
        this.arr = new TrieNode[26];
    }
}

class Trie
{
    TrieNode root;

    Trie()
    {
        root = new TrieNode();
    }

    public void insertWord(String dictionaryWords)
    {
        TrieNode r = root;
        for(int i = 0; i < dictionaryWords.length(); i++)
        {
            char present = dictionaryWords.charAt(i);
            int index = present - 'A';
            if(r.arr[index]==null)
            {
                TrieNode temp = new TrieNode();
                r.arr[index]=temp;
                r = temp;
            }
            else
            {
                r = r.arr[index];
            }
        }
        r.end = true;
    }

    public boolean searchPrefix(String prefix)
    {
        TrieNode pref = root;
        for(int i=0; i<prefix.length(); i++)
        {
            char current = prefix.charAt(i);
            int index = current - 'A';
            if(pref.arr[index]!=null)
            {
                pref = pref.arr[index];
            }
            else
            {
                return false;
            }
        }
        if(pref == root)
            return false;
        return true;
    }
}