import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class MyMap<K,V> {
    private int capacity;// bucket size
    private int size; //Number of entries in the hashmap
    private final int INITIAL_CAPACITY = 15;
    private List<Node<K,V>> bucket;
     public MyMap(){
         bucket = new ArrayList<>();
         capacity = INITIAL_CAPACITY;
         size = 0;
         for (int  i = 0; i < capacity;i++){
             bucket.add(null);
         }
     }


}


private class Node<K,V>{
    K key;
    V value;
    Node<K,V> next;
    public Node(K key, V value){
        this.key =key;
        this.value = value;
        this.next = null;
    }
};
