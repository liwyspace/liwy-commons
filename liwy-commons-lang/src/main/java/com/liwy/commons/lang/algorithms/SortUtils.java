package com.liwy.commons.lang.algorithms;

/**
 * 排序算法工具类
 * 
 * @author liwy-psbc
 * 
 */
public class SortUtils {
	/**
	 * 直接插入排序
	 * 
	 * 先将序列的第1个记录看成是一个有序的子序列，然后从第2个记录逐个进行插入， 直至整个序列有序为止。
	 * 
	 * 平均时间复杂度：O(n^2) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(n)
	 * 
	 * @param array
	 *            待排序数组
	 */
	public static void insertSort(int[] array) {
		for (int i = 1; i < array.length; i++) { // 从第二个元素开始逐一排序
			if (array[i - 1] > array[i]) {// / 若第i个元素大于i-1元素，直接插入。小于的话，移动有序表后插入
				int temp = array[i]; // 待排序元素
				int p = i - 1; // 待比较元素的位置
				while (p >= 0 && temp < array[p]) { // 如果待排序元素小于待比较元素
					array[p + 1] = array[p]; // 将带比较元素后移一个位置
					p--; // 带比较元素的位置前移一个位置
				}
				array[p + 1] = temp; // 将待排序元素赋值到已比较元素的位置
			}
		}
	}

	/**
	 * 希尔排序(缩小增量排序)
	 * 
	 * 先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序， 待整个序列中的记录"基本有序"时，再对全体记录进行依次直接插入排序。
	 * 
	 * 平均时间复杂度：O(nlog2n) 最坏时间复杂度：O(nlog2n) 最好时间复杂度：
	 * 
	 * @param array 待排序数组
	 */
	public static void shellSort(int[] array) {
		for (int gap = array.length / 2; gap > 0; gap /= 2) {
			// 步长
			for (int i = 0; i < gap; i++) { // 直接插入排序
				for (int j = i + gap; j < array.length; j += gap) {
					if (array[j] < array[j - gap]) {
						int temp = array[j]; // 待排序元素
						int p = j - gap; // 待比较元素的位置
						while (p >= 0 && temp < array[p]) { // 如果待排序元素小于待比较元素
							array[p + gap] = array[p]; // 将带比较元素后移一个位置
							p -= gap; // 带比较元素的位置前移一个位置
						}
						array[p + gap] = temp; // 将待排序元素赋值到已比较元素的位置
					}
				}
			}
		}
	}

	/**
	 * 冒泡排序
	 * 
	 * 比较相邻的前后二个数据，如果前面数据大于后面的数据，就将二个数据交换。
	 * 
	 * 平均时间复杂度：O(n^2) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(n)
	 * 
	 * @param array
	 */
	public static void bubbleSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 1; j < array.length - i; j++) {
				if (array[j - 1] > array[j]) {
					int temp = array[j - 1];
					array[j - 1] = array[j];
					array[j] = temp;
				}
			}
		}
	}

	/**
	 * 快速排序
	 * 
	 * 先从数列中取出一个数作为基准数; 分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边;
	 * 再对左右区间重复第二步，直到各区间只有一个数。
	 * 
	 * 平均时间复杂度：O(nlog2n) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(nlog2n)
	 * 
	 * @param array
	 */
	public static void quickSort(int[] array, int l, int r) {
		if (l < r) {
			int i = l, j = r, temp = array[l];
			while (i < j) {
				while (i < j && array[j] >= temp) { // 从右向左找第一个小于x的数
					j--;
				}
				if (i < j) {
					array[i] = array[j];
					i++;
				}

				while (i < j && array[i] < temp) { // 从左向右找第一个大于等于x的数
					i++;
				}
				if (i < j) {
					array[j] = array[i];
					j--;
				}
			}
			array[i] = temp;
			quickSort(array, l, i - 1); // 递归调用
			quickSort(array, i + 1, r);
		}
	}

	/**
	 * 直接选择排序
	 * 
	 * 平均时间复杂度：O(n^2) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(n^2)
	 * 
	 * @param array
	 */
	public static void selectSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			//获取最小的元素位置k
			int k = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[k] > array[j]) {
					k = j;
				}
			}
			
			// 最小元素与第i位置元素互换
			if (k != i) {
				int tmp = array[i];
				array[i] = array[k];
				array[k] = tmp;
			}
		}
	}

	/**
	 * 堆排序
	 * 
	 * 平均时间复杂度：O(nlog2n) 最坏时间复杂度：O(nlog2n) 最好时间复杂度：O(nlog2n)
	 * 
	 * @param array
	 */
	public static void heapSort(int[] array) {
		//初始堆进行调整
		//将array[0..length-1]建成堆 
		//调整完之后第一个元素是序列的最小的元素 
	    for (int i=(array.length-1)/2 ; i>=0; i--) {  //最后一个有孩子的节点的位置 i=  (length-1)/2  
	        heapAdjust(array,i,array.length);  
	    }
	    
	    //从最后一个元素开始对序列进行调整  
	    for (int i=array.length-1; i>0; i--)  
	    {  
	        //交换堆顶元素H[0]和堆中最后一个元素  
	        int temp = array[i]; 
	        array[i] = array[0]; 
	        array[0] = temp;  
	        //每次交换堆顶元素和堆中最后一个元素之后，都要对堆进行调整  
	        heapAdjust(array,0,i);  
	  } 
	}
	
	/** 
	 * 调整arry[s],使其成为大顶堆.即将对第s个结点为根的子树筛选,  
	 * 
	 * @param arry 待调整的堆数组
	 * @param s 待调整的数组元素的位置
	 * @param length 数组的长度
	 * 
	 */  
	private static void heapAdjust(int arry[],int s, int length) {  
	    int tmp  = arry[s];  
	    int child = 2*s+1; //左孩子结点的位置。(i+1 为当前调整结点的右孩子结点的位置)  
	    while (child < length) {  
	        if(child+1 <length && arry[child]<arry[child+1]) { // 如果右孩子大于左孩子(找到比当前待调整结点大的孩子结点)  
	            child++;
	        }  
	        if(arry[s]<arry[child]) {  // 如果较大的子结点大于父结点  
	        	arry[s] = arry[child]; // 那么把较大的子结点往上移动，替换它的父结点  
	            s = child;       // 重新设置s ,即待调整的下一个结点的位置  
	            child = 2*s+1;  
	        }  else {            // 如果当前待调整结点大于它的左右孩子，则不需要调整，直接退出  
	             break;  
	        }  
	        arry[s] = tmp;         // 当前待调整的结点放到比其大的孩子结点位置上  
	    }
	}  
	  
	  

	/**
	 * 归并排序
	 * 
	 * 平均时间复杂度：O(nlog2n) 最坏时间复杂度：O(nlog2n) 最好时间复杂度：O(nlog2n)
	 * 
	 * @param array
	 */
	public static void mergeSort(int[] array) {
		for (int gap = 1; gap < array.length; gap = 2 * gap) {
			int i = 0;
			// 归并gap长度的两个相邻子表
			for (i = 0; i + 2 * gap - 1 < array.length; i = i + 2 * gap) {
				merge(array, i, i + gap - 1, i + 2 * gap - 1);
			}
			// 余下两个子表，后者长度小于gap
			if (i + gap - 1 < array.length) {
				merge(array, i, i + gap - 1, array.length - 1);
			}
		}
	}
	
	/**
	 * 将array[fir...mid]和array[mid-1...las]归并后覆盖到array[fir...las]中
	 * @param array
	 * @param fir
	 * @param mid
	 * @param las
	 */
	private static void merge(int[] array, int fir, int mid, int las) {
		int i=fir; //第一段开始位置
		int j=mid+1; //第二段开始位置
		int[] temp = new int[las-fir+1];  //临时存放归并后的数组
		int k=0;
		
	    while(i<=mid && j<=las){
	        if(array[j] < array[i]) {
	        	temp[k++] = array[j++];
	        } else {
	        	temp[k++] = array[i++];
	        }
	    }
	    
	    while(i <= mid)  temp[k++] = array[i++];  
	    while(j <= las)  temp[k++] = array[j++];
	    while(0 < k--) array[fir + k] = temp[k];  //将临时数组中的数据复制到数组中
	}

	/**
	 * 基数排序</br>
	 * 非比较排序，暂不支持小数和负数
	 * 
	 * 
	 * 平均时间复杂度：O(d(n+r)) 最坏时间复杂度：O(d(n+r)) 最好时间复杂度：O(d(n+r))
	 * 
	 * @param array
	 * @param d 数组中最大元素的位数
	 */
	public static void radixSort(int[] array, int d) {
        int m = 1;
        int n = 1;
        int[][] temp = new int[10][array.length]; //数组的第一维表示可能的余数0-9，即定义10个桶
        int[] order = new int[10]; //数组orderp[i]用来表示该位是i的数的个数
        while(m <= d) {  //桶排序的次数，即最大数的位数
            for(int i = 0; i < array.length; i++) {
                int lsd = ((array[i] / n) % 10);
                temp[lsd][order[lsd]] = array[i];
                order[lsd]++;
            }
            int k = 0;
            for(int i = 0; i < 10; i++) {
                if(order[i] != 0) {
                    for(int j = 0; j < order[i]; j++) {
                    	array[k] = temp[i][j];
                        k++;
                    }
                }
                order[i] = 0;
            }
            n *= 10;
            m++;
        }
	}

}
