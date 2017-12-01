package sortcomparison;

import java.util.*;

public class BasicSorter implements Sorter {

	@Override
	public void insertionSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		String temp;
		for (int i = firstIndex; i < numberToSort; i++) {
			temp = data.get(i);
			for (int j = i; j > 0 && (data.get(j - 1).compareTo(data.get(j)) > 0); j--) {
				data.set(j, data.get(j - 1));
				data.set(j - 1, temp);
			}
		}
	}

	@Override
	public void quickSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		if (numberToSort < 16)
			insertionSort(data, firstIndex, numberToSort);
		else {
			if (numberToSort - firstIndex > 1) {
				int pivot = partition(data, firstIndex, numberToSort - firstIndex);
				quickSort(data, firstIndex, pivot);
				quickSort(data, pivot + 1, numberToSort);
			}
		}
	}

	@Override
	public int partition(ArrayList<String> data, int firstIndex, int numberToPartition) {
		getMedian(data, firstIndex, numberToPartition);

		String pivot = data.get(firstIndex);
		int TooBigNdx = firstIndex + 1;
		int TooSmallNdx = firstIndex + numberToPartition - 1;
		String temp;
		while (TooBigNdx < TooSmallNdx) {
			while ((TooBigNdx < TooSmallNdx) && (data.get(TooBigNdx).compareTo(pivot)) <= 0) {
				TooBigNdx++;
			}
			while ((TooSmallNdx > firstIndex) && (data.get(TooSmallNdx).compareTo(pivot) > 0)) {
				TooSmallNdx--;
			}
			if (TooBigNdx < TooSmallNdx) {
				temp = data.get(TooBigNdx);
				data.set(TooBigNdx, data.get(TooSmallNdx));
				data.set(TooSmallNdx, temp);
			}
		}
		if (pivot.compareTo(data.get(TooSmallNdx)) >= 0) {
			temp = data.get(TooSmallNdx);
			data.set(TooSmallNdx, data.get(firstIndex));
			data.set(firstIndex, temp);
			return TooSmallNdx;
		} else
			return firstIndex;
	}

	private void getMedian(ArrayList<String> data, int firstIndex, int numberToPartition) {
		int mid = (firstIndex + (firstIndex + numberToPartition - 1)) / 2;
		String temp;

		// Let (element at mid) < (element at first).
		if (data.get(firstIndex).compareTo(data.get(mid)) < 0) {
			temp = data.get(firstIndex);
			data.set(firstIndex, data.get(mid));
			data.set(mid, temp);
		}
		// Let (element at first) < (element at last).
		if (data.get(firstIndex).compareTo(data.get(firstIndex + numberToPartition - 1)) > 0) {
			temp = data.get(firstIndex);
			data.set(firstIndex, data.get(firstIndex + numberToPartition - 1));
			data.set(firstIndex + numberToPartition - 1, temp);
		}
		// Let (element at mid) < (element at last).
		if (data.get(mid).compareTo(data.get(firstIndex + numberToPartition - 1)) > 0) {
			temp = data.get(mid);
			data.set(mid, data.get(firstIndex + numberToPartition - 1));
			data.set(firstIndex + numberToPartition - 1, temp);
		}
		// After executed, it should look like mid < first < last.
	}

	@Override
	public void mergeSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		if (numberToSort > 1) {
			if (numberToSort < 16)
				insertionSort(data, firstIndex, numberToSort);

			int left;
			int right;
			if (numberToSort % 2 == 0)
				left = right = numberToSort / 2;
			else {
				left = numberToSort / 2;
				right = numberToSort - left;
			}
			mergeSort(data, firstIndex, left);
			mergeSort(data, firstIndex + left, right);

			if (data.get(left + firstIndex - 1).compareTo(data.get(left + firstIndex)) <= 0) {
				for (int i = left + firstIndex - 1; i < left + firstIndex; i++)
					data.set(left + firstIndex - 1, data.get(i));
			} else
				merge(data, firstIndex, left, right);
		}
	}

	@Override
	public void merge(ArrayList<String> data, int firstIndex, int leftSegmentSize, int rightSegmentSize) {
		ArrayList<String> temp = new ArrayList<String>();
		int left = 0;
		int right = 0;

		while ((left < leftSegmentSize) && (right < rightSegmentSize)) {
			if (data.get(firstIndex + left).compareTo(data.get(firstIndex + leftSegmentSize + right)) < 0) {
				temp.add(data.get(firstIndex + left));
				left++;
			} else if (data.get(firstIndex + left).compareTo(data.get(firstIndex + leftSegmentSize + right)) > 0) {
				temp.add(data.get(firstIndex + leftSegmentSize + right));
				right++;
			} else if (data.get(firstIndex + left).compareTo(data.get(firstIndex + leftSegmentSize + right)) == 0) {
				temp.add(data.get(firstIndex + left));
				temp.add(data.get(firstIndex + leftSegmentSize + right));
				left++;
				right++;
			}
		}
		while (left < leftSegmentSize) {
			temp.add(data.get(firstIndex + left));
			left++;
		}
		while (right < rightSegmentSize) {
			temp.add(data.get(firstIndex + leftSegmentSize + right));
			right++;
		}

		for (int i = 0; i < temp.size(); i++)
			data.set(firstIndex + i, temp.get(i));
	}

	@Override
	public void heapSort(ArrayList<String> data) {
		heapify(data);
		int numUnsorted = data.size();
		while (numUnsorted > 1) {
			numUnsorted--;
			String temp = data.get(numUnsorted);
			data.set(numUnsorted, data.get(0));
			data.set(0, temp);
			reheapifyDown(data, numUnsorted);
		}
	}

	private void reheapifyDown(ArrayList<String> data, int numUnsorted) {
		int curNdx = 0;
		int bigChildNdx = 0;
		boolean bHeap = false;
		while (!bHeap && ((curNdx * 2 + 1) < numUnsorted)) {
			if ((curNdx * 2 + 2) < numUnsorted) {
				if (data.get(curNdx * 2 + 1).compareTo(data.get(curNdx * 2 + 2)) >= 0)
					bigChildNdx = curNdx * 2 + 1;
				else
					bigChildNdx = curNdx * 2 + 2;
			} else
				bigChildNdx = curNdx * 2 + 1;

			if (data.get(curNdx).compareTo(data.get(bigChildNdx)) < 0) {
				String temp = data.get(curNdx);
				data.set(curNdx, data.get(bigChildNdx));
				data.set(bigChildNdx, temp);
				curNdx = bigChildNdx;
			} else
				bHeap = true;
		}
	}

	@Override
	public void heapify(ArrayList<String> data) {
		int newNdx;
		for (int i = 0; i < data.size(); i++) {
			newNdx = i;
			while ((newNdx != 0) && (data.get(newNdx).compareTo(data.get((newNdx - 1) / 2)) > 0)) {
				String temp = data.get((newNdx - 1) / 2);
				data.set(((newNdx - 1) / 2), data.get(newNdx));
				data.set(newNdx, temp);
				newNdx = (newNdx - 1) / 2;
			}
		}
	}
}
