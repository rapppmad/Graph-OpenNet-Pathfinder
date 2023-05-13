//package AfterUTP.Test;
import java.util.Arrays;
import java.util.Scanner;

//Input diawali dengan inisiasi graph dan diakhiri dengan pemberian source dan destination yang akan dicari
//        (jumlah-node) (jumlah-edge)
//        (list-nama-node)
//        (source) (destination) (cost)
//        (source-to-find) (destination-to-find)
// OUTPUT -> Output yang dikeluarkan aplikasi adalah cost terendah dari source ke destination dari input terakhir
public class Solution {
    public static void main(String[] args) {
        Scanner inputUser = new Scanner(System.in);
        int jumlahSimpul = inputUser.nextInt();
        int jumlahEdge = inputUser.nextInt();
        Graph graph = new Graph(jumlahSimpul);
        inputUser.nextLine();
        String[] simpul = inputUser.nextLine().split(" ");
        for (String vertex : simpul) {
            graph.tambahSimpul(vertex);
        }
        
        for (int i = 0; i < jumlahEdge; i++) {
            String[] edge = inputUser.nextLine().split(" ");
            for (int j = 2; j < edge.length; j += 2) {
                String from = edge[j - 2];
                String to = edge[j - 1];
                int weight = Integer.parseInt(edge[j]);
                graph.addEdge(from, to, weight);
            }
        }
        String[] findArray = inputUser.nextLine().split(" ");
        int shortestPathCost = graph.shortestPathCost(findArray[0], findArray[1]);
        System.out.println(shortestPathCost);
    }
}
class Graph {
    protected int jumlahSimpul;
    protected String[] simpul;
    protected int[] isi;
    protected boolean[] isVisited;
    public int[][] adjacencyMatrix;
    public Graph() {
    }
    public Graph(int jmlSimpulNode) {
        this.jumlahSimpul = jmlSimpulNode;
        adjacencyMatrix = new int[jmlSimpulNode][jmlSimpulNode];
        simpul = new String[jmlSimpulNode];
        isi = new int[jmlSimpulNode];
        isVisited = new boolean[jmlSimpulNode];
        Arrays.fill(isi, Integer.MAX_VALUE);
    }
    private int cariSimpul(String vertexName) {
        for (int i = 0; i < jumlahSimpul; i++) {
            if (simpul[i].equals(vertexName)) {
                return i;
            }
        }
        return -1;
    }
    public void tambahSimpul(String simpul) {
        for (int i = 0; i < jumlahSimpul; i++) {
            if (this.simpul[i] == null) {
                this.simpul[i] = simpul;
                break;
            }
        }
    }
    public void addEdge(String from, String to, int weight) {
        int x = cariSimpul(from);
        int y = cariSimpul(to);
        if (x != -1 && y != -1) {
            adjacencyMatrix[x][y] = weight;
            if (x != y) {
                adjacencyMatrix[y][x] = weight;
            }
        }
    }
    public void print() {
        for (String s : simpul) {
            if (s != null) {
                System.out.print(s + " ");
            }
        }
        System.out.println();
    }

    public int shortestPathCost(String start, String end) {
        int startAtFirst = cariSimpul(start);
        int endAtX = cariSimpul(end);
        isi = new int[jumlahSimpul];
        isVisited = new boolean[jumlahSimpul];
        Arrays.fill(isi, Integer.MAX_VALUE);

        isi[startAtFirst] = 0;

        PriorityQueue queue = new PriorityQueue();

        queue.enqueue(startAtFirst, 0);
        while (!queue.isEmpty()) {
            int currentNode = (int) queue.peek();

            if (currentNode == endAtX) {
                return isi[currentNode];
            }

            isVisited[currentNode] = true;

            for (int i = 0; i < jumlahSimpul; i++) {
                if (adjacencyMatrix[currentNode][i] != 0 && !isVisited[i]) {
                    int newEstimate = isi[currentNode] + adjacencyMatrix[currentNode][i];
                    if (newEstimate < isi[i]) {
                        isi[i] = newEstimate;
                        queue.enqueue(i, newEstimate);
                    }
                }
            }
        }
        return -1;
    }

}
class PriorityQueue {
    public PriorityQueue(){
        this.head = null;
        this.tail=head;
    }
    public class Node {
        Object value;
        Node next;
        int priority;
        public Node(){}
        public Node (Object value){}
        public Node(Object value, int priority) {this.value = value;this.priority = priority;}
    }
    private Node head;
    private Node tail;
    public void enqueue(Object value, int priority) {
        Node newNode = new Node(value, priority);
        if (head == null) {
            head = tail = newNode;
        } else {
            Node targetNode = head;
            Node prevTargerNode = head;
            while (newNode.priority >= targetNode.priority) {
                prevTargerNode = targetNode;
                targetNode = targetNode.next;
                if (targetNode == null) {
                    break;
                }
            }
            //ketika di head
            if (targetNode == null) {
                tail.next = newNode;
                tail = tail.next;
                return;
            }
            //ketika di head
            if (targetNode == head) {
                newNode.next = head;
                head = newNode;
                return;
            }
            //ketika di tengah tengah
            newNode.next = targetNode;
            prevTargerNode.next = newNode;
        }
    }
    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        Object result = head.value;
        head = head.next;
        return result;
    }
    public boolean isEmpty() {
        return head == null;
    }
}