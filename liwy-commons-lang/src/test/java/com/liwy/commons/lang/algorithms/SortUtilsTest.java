package com.liwy.commons.lang.algorithms;

import com.liwy.commons.lang.algorithms.SortUtils;
import org.junit.Test;

public class SortUtilsTest {
	private void printArray(int[] array) {
		System.out.print("[");
		for(int i=0;i<array.length;i++) {
			if(i!=0) {
				System.out.print(",");
			}
			System.out.print(array[i]);
		}
		System.out.println("]");
	}
	
	
	@Test
	public void insertSortTest() {
		System.out.println("插入排序：");
		int[] array = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.insertSort(array);
		printArray(array);
		
		System.out.println("希尔排序：");
		int[] array1 = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.shellSort(array1);
		printArray(array1);
		
		System.out.println("冒泡排序：");
		int[] array2 = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.bubbleSort(array2);
		printArray(array2);
		
		System.out.println("快速排序：");
		int[] array3 = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.quickSort(array3,0,array3.length-1);
		printArray(array3);
		
		System.out.println("选择排序：");
		int[] array4 = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.selectSort(array4);
		printArray(array4);
		
		System.out.println("堆排序：");
		int[] array5 = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.selectSort(array5);
		printArray(array5);
		
		System.out.println("归并排序：");
		int[] array6 = {3,1,4,-9,0,8,7,12,5,1};
		SortUtils.mergeSort(array6);
		printArray(array6);
		
		System.out.println("基数排序：");
		int[] array7 = {3,1,4,0,8,7,12,5,100,23};
		SortUtils.radixSort(array7,3);
		printArray(array7);
	}
	
}
