package algo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Eleven {

    public static void main(String[] args) throws IOException {
        args = readFile();
        
        Queue<String> queue = new LinkedList<>();
        for (String arg : args) {
            queue.add(arg);
        }
        
        Eleven e = new Eleven();
        e.start(queue);
    }


    private static String[] readFile() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("11-in.txt");
        BufferedReader br = new BufferedReader(fr);
        String l = null;
        List<String> list = new ArrayList<>();
        while ((l = br.readLine()) != null) {
            list.add(l);
        }
        br.close();
        
        return list.toArray(new String[list.size()]);
    }
    
    private void start(Queue<String> queue) throws IOException {
        int count = Integer.parseInt(queue.poll());
    
        for (int i = 0 ; i < count ; i++) {
            doSolve(i + 1, queue);
        }
    }
    
    private void doSolve(int caseNum, Queue<String> queue) throws IOException {
        int[] question = split(queue.poll());
        int tiles = question[0];
        int targetNum = question[1];
        
//        System.out.println(String.format("%d %d", tiles, targetNum));
        
        int lastInnerLayerSideNum = getLastInnerLayerSideNum(tiles);
        int lastInnerLayerNum = lastInnerLayerSideNum * lastInnerLayerSideNum;
        if (lastInnerLayerNum + 1 >= targetNum) {
            printAnswer(caseNum, 1, 1);
            return;
        }
        
        int currLayerNum = lastInnerLayerSideNum == 0 ? lastInnerLayerSideNum + 1 : lastInnerLayerSideNum + 2;
        
        int depth = tiles - lastInnerLayerNum - 1;
        
        int min = lastInnerLayerNum + 1;
        int max = currLayerNum * currLayerNum;
//        System.out.println(String.format("mix, max : %d, %d", min, max));
        int rightLength = targetNum - min;
        int leftLength = max - targetNum + 1;
        
        if ((rightLength > depth && leftLength > depth) || leftLength < 1) {
            printAnswer(caseNum, 0, 1);
            return;
        }
        
        if (tiles == max) {
            printAnswer(caseNum, 1, 1);
            return;
        }
        
        double totalCase = Math.pow(2, depth);
        double trueCase = 0;
        
        int i = 0;
        while (depth - rightLength >= i) {
            double c = combination(depth, i);
            trueCase += c;
//            System.out.println(String.format("%d C %d = %.0f", depth, i, c));
            i++;
        }
        i = 0;
        while (depth - leftLength >= i) {
            double c = combination(depth, i);
            trueCase += c;
//            System.out.println(String.format("%d C %d = %.0f", depth, i, c));
            i++;
        }
        
        printAnswer(caseNum, trueCase, totalCase);
    }
    
    
    
    private void printAnswer(int caseNum, double trueCase, double totalCase) {
        while (trueCase > 0 && trueCase % 2 == 0) {
            trueCase /= 2;
            totalCase /= 2;
        }
        System.out.println(String.format("#%d %.0f / %.0f", caseNum, trueCase, totalCase));
    }

    private double combination(int total, int num) {
        if (num == 0) {
            return 1;
        }
        
        int big = total - num > num ? total-num : num;
        double comb = 1;
        for (int i = total ; i > big ; i--) {
            comb *= i;
        }
        
        for (int i = total - big ; i > 0 ; i--) {
            comb /= i;
        }
        
        return comb;
    }
    
    private int getLastInnerLayerSideNum(int tiles) {
        int innerLayer = (int) Math.sqrt(tiles - 1);
        if (innerLayer > 0 && innerLayer % 2 == 0) {
            innerLayer--;
        }
        
        return innerLayer;
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
