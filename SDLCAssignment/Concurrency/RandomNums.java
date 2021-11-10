package SDLCAssignment.Concurrency;

import java.util.*;

public class RandomNums {

    public static int sum(int[] numbers) {
        int result = 0;
        for (int i = 0; i < numbers.length; i++) {
            result += numbers[i];
        }
        return result;
        }
    
    public static void main(String[] args) {
      Random r = new Random();
      int numberOfProcessors = Runtime.getRuntime().availableProcessors();
      int[] numbers = new int[200000000];

        for (int i=0; i<numbers.length; i++) {
            numbers[i] = r.nextInt(10) + 1;
        }
      long start = System.currentTimeMillis();
      System.out.println("Sum: " + sum(numbers) + " generated in " + (System.currentTimeMillis()-start) + "ms");
      
      ParallelSum parallelSum = new ParallelSum(numberOfProcessors);
      System.out.println("Parallel Sum: " + parallelSum.parSum(numbers) + " generated in " + (System.currentTimeMillis()-start) + "ms");
   }
   
}
