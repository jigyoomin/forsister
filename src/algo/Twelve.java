package algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Twelve {

    public static void main(String[] args) throws IOException {
        Twelve t = new Twelve();

        t.start();
    }

    private BufferedReader br;



    private void start() throws IOException {
        FileReader fr = new FileReader("12-in.txt");
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
        int[] split = split(getLine());
        int n = split[0]; // 한 라인의 부품 수
        int m = split[1]; // 라인 수
        int k = split[2]; // 엔지니어 수 
        
        String engs = getLine(); // 엔지니어
        Set<Character> engsSet = new HashSet<>();
        char[] engsCharArray = engs.toCharArray();

        Map<Character, int[]> counts = new HashMap<>(); 
        for (int i = 0 ; i < engsCharArray.length ; i++) {
            engsSet.add(engsCharArray[i]);
            if (!counts.containsKey(engsCharArray[i])) {
                int[] count = new int[n];
                Arrays.fill(count, 0);
                counts.put(engsCharArray[i], count);
            }
        }
        
        
        for (int i = 0 ; i < m ; i++) {
            String line = getLine();
            char[] charArray = line.toCharArray();
            for (int j = 0 ; j < n ; j++) {
                if (!engsSet.contains(charArray[j])) { continue; }
                int[] count = null;
                count = counts.get(charArray[j]);
                count[j]++;
            }
        }
        
        for (int i = 0 ; i < engsCharArray.length ; i++) {
            System.out.print(engsCharArray[i]);
            System.out.print(" : ");
            int[] js = counts.get(engsCharArray[i]);
            for (int j = 0 ; j < js.length ; j++) {
                System.out.print(js[j]);
                System.out.print(" ");
            }
            
            System.out.println();
        }
        System.out.println();
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
}
