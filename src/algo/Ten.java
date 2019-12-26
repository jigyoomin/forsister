package algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

public class Ten {

    public static void main(String[] args) throws IOException {
        Ten ten = new Ten();

        ten.start();
    }


    private BufferedReader br;
    
    private Stack<Dest> path;
    
    private int minDiff;

    private void start() throws IOException {

        FileReader fr = new FileReader("10-in.txt");
        br = new BufferedReader(fr);

        try {
            int count = Integer.parseInt(getLine());      
    
            for (int i = 0 ; i < count ; i++) {
                doSolve(i + 1);
            }
        } catch (Exception e) {
            
        } finally {
            br.close();
        }
    }
    
    private void doSolve(int caseNum) throws IOException {
        int[] first = split(getLine());
        int nodeCount = first[0];
        int lineCount = first[1];
        
        List<Dest> graph[] = new List[nodeCount + 1];
        
        createGraph(lineCount, graph);
//        printGraph(graph);
        
        int[] target = split(getLine());
//        System.out.println(String.format("Start: %d, End: %d", target[0], target[1]));
        
        int start = target[0];
        int end = target[1];
        
        Set<Integer> visited = new HashSet<Integer>();
        path = new Stack<>();
        visited.add(start);
        List<Dest> startNode = graph[start];
        
        minDiff = Integer.MAX_VALUE;
        search(graph, startNode, end, visited);
        System.out.println(String.format("#%d %d", caseNum, minDiff));
    }

    private void search(List<Dest>[] graph, List<Dest> node, int end, Set<Integer> visited) {
        
        for (Dest dest : node) {
            
//            System.out.print(dest.dst);
            
            if (visited.contains(dest.dst)) { 
                // System.out.println("방문 한 노드 "); 
                continue; 
            }
            
//            System.out.print(" ");
            
            
            if (dest.dst == end) {
//                System.out.print("end 도착 - ");
                path.push(dest);
//                System.out.println(path);
//                System.out.println(String.format("Diff --- %d", getMixMaxDiff()));
                int minMaxDiff = getMinMaxDiff();
                if (minDiff > minMaxDiff) {
                    minDiff = minMaxDiff;
                }
                path.pop();
                continue; 
            }
            
            visited.add(dest.dst);
            path.push(dest);
            
            search(graph, graph[dest.dst], end, visited);
            visited.remove(dest.dst);
            path.pop();
        }
    }
    
    private int getMinMaxDiff() {
        int max = -1;
        int min = Integer.MAX_VALUE;
        ListIterator<Dest> iter = path.listIterator();
        while (iter.hasNext()) {
            Dest next = iter.next();
            if (next.length > max) max = next.length;
            if (next.length < min) min = next.length;
        }
        return max - min;
    }

    private void printGraph(List<Dest>[] graph) {
        for (List<Dest> d : graph) {
            System.out.println(d);
        }
    }

    private void createGraph(int lineCount, List<Dest>[] graph) throws IOException {
        for (int i = 0 ; i < lineCount ; i++) {
            int[] oneLine = split(getLine());
            
            List<Dest> node1 = graph[oneLine[0]];
            List<Dest> node2 = graph[oneLine[1]];

            if (node1 == null) {
                node1 = new ArrayList<Dest>();
                graph[oneLine[0]] = node1;
            }
            
            if (node2 == null) {
                node2 = new ArrayList<Dest>();
                graph[oneLine[1]] = node2;
            }
            
            node1.add(new Dest(oneLine[1], oneLine[2]));
            node2.add(new Dest(oneLine[0], oneLine[2]));
        }
    }

    private String getLine() throws IOException {
        return br.readLine();
    }
    
    private int[] split(String str) {
        String[] split = str.split(" ");
        int[] intArray = new int[split.length];
        for (int i = 0 ; i < split.length ; i++) {
            intArray[i] = Integer.parseInt(split[i]);
        }
        
        return intArray;
    }
    
    class Dest {
        int dst;
        int length;
        
        public Dest(int dst, int length) {
            this.dst = dst;
            this.length = length;
        }

        @Override
        public String toString() {
            return "Dest [dst=" + dst + ", length=" + length + "]";
        }
    }
}
