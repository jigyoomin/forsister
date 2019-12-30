package algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Eleven {

    public static void main(String[] args) throws IOException {
        Eleven e = new Eleven();
        e.start();
    }
    
    private BufferedReader br;
    
    private void start() throws IOException {

        FileReader fr = new FileReader("11-in.txt");
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
        int[] question = split(getLine());
        int tiles = question[0];
        int targetNum = question[1];
        
//        System.out.println(String.format("%d %d", tiles, targetNum));
        
        
        int lastInnerLayerSideNum = getLastInnerLayerSideNum(tiles);
        int lastInnerLayerNum = lastInnerLayerSideNum * lastInnerLayerSideNum;
        if (lastInnerLayerNum + 1 >= targetNum) {
            printAnswer(caseNum, 1, 1);
            return;
        }
        
        int currLayerNum = lastInnerLayerSideNum + 2;
        
        int depth = tiles - lastInnerLayerNum - 1;
        
        int min = lastInnerLayerNum + 1;
        int max = currLayerNum * currLayerNum;
        int rightLength = targetNum - min;
        int leftLength = max - targetNum + 1;
        
        if ((rightLength > depth && leftLength > depth) || leftLength < 1) {
            printAnswer(caseNum, 0, 1);
            return;
        }
        
        int totalCase = (int) Math.pow(2, depth);
        int trueCase = 0;
        
        int i = 0;
        while (depth - rightLength >= i) {
            trueCase += combination(depth, i++);
        }
        i = 0;
        while (depth - leftLength >= i) {
            trueCase += combination(depth, i++);
        }
        
        printAnswer(caseNum, trueCase, totalCase);
    }
    
    
    
    private void printAnswer(int caseNum, int trueCase, int totalCase) {
        while (trueCase > 0 && trueCase % 2 == 0) {
            trueCase /= 2;
            totalCase /= 2;
        }
        System.out.println(String.format("#%d %d / %d", caseNum, trueCase, totalCase));
    }

    private int combination(int total, int num) {
        if (num == 0) {
            return 1;
        }
        
        int big = total - num > num ? total-num : num;
        int comb = 1;
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
    
    private boolean isInInnerLayer(int lastInnerLayerSideNum, int targetNum)  {
        if ( lastInnerLayerSideNum * lastInnerLayerSideNum + 1 >= targetNum) {
            return true;
        }
        
        return false;
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
