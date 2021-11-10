package SDLCAssignment.Concurrency;

public class ParallelSum {

    private ParallelWorker[] pSums;
	private int numOfThreads;
	
	public ParallelSum(int numOfThreads) {
		this.numOfThreads = numOfThreads;
		this.pSums = new ParallelWorker[numOfThreads];
	}
	
	public int parSum(int[] nums) {

		// the more threads the fewer integers we can assign to a single worker
		
		int size = (int) Math.ceil(nums.length * 1.0 / numOfThreads);

		// create as many parallel workers as the number of cores
		for (int i = 0; i < numOfThreads; i++) {
			pSums[i] = new ParallelWorker(nums, i * size, (i + 1) * size);
			pSums[i].start();
		}

		try {
			for (ParallelWorker worker : pSums) {
				worker.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int total = 0;

		for (ParallelWorker worker : pSums) {
			total += worker.getPartialSum();
		}

		return total;
	}
    
}
