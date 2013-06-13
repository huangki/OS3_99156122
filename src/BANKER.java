import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BANKER {

	public static void main(String[] args) throws FileNotFoundException {
		File f1 = new File("Allocation.txt");//已讀檔的方式進行Allocation 的檔案
		File f2 = new File("Max.txt");//已讀檔的方式進行MAX 的檔案
		File f3 = new File("Available.txt");//已讀檔的方式進行Available 的檔案
		Scanner in1 = new Scanner(f1);//宣告三個scanner 分別讀三個檔案
		Scanner in2 = new Scanner(f2);//宣告三個scanner 分別讀三個檔案
		Scanner in3 = new Scanner(f3);//宣告三個scanner 分別讀三個檔案
		int n = 0;//設立一個變數 當作變數讀入
		int count1 = 0;// 紀錄available的空間個數已宣告陣列
		int count2 = 0;// 紀錄有幾個process的空間個數已宣告陣列
		while (in3.hasNext()) {
			n = in3.nextInt();//先讀available 
			count1++;//計數器 知道陣列多大
		}
		while (in1.hasNext()) {
			n = in1.nextInt();//開始讀allocation
			count2++;// 計數器 技術
		}
		in1.close();//關閉資源
		in3.close();//關閉資源
		int Allo[][] = new int[count2 / count1][count1];//c2/c1可以知道 有幾個process 宣告allocation存放陣列
		int Max[][] = new int[count2 / count1][count1];// 宣存放陣列MAx
		int Avail[] = new int[count1];//available 存放陣列
		int Need[][] = new int[count2 / count1][count1];//宣存放陣列need
		int TAllo[][] = new int[count2 / count1][count1];//宣存放陣列第二次使用的allocation
		in1 = new Scanner(f1);//重新開啟掃描器
		in2 = new Scanner(f2);//重新開啟掃描器
		in3 = new Scanner(f3);//重新開啟掃描器
		int c1 = 0, l1 = 0;//控制2微陣列的位置
		while (in1.hasNext()) {//開始讀檔輸入
			Allo[c1][l1] = in1.nextInt();//把數字填入allocation陣列
			l1++;//填完column
			if (l1 % count1 == 0) {//判斷跳行
				c1++;//row 增加
				l1 = 0;//把 column規0
			}
		}
		int c2 = 0, l2 = 0;//控制2微陣列的位置
		while (in2.hasNext()) {
			Max[c2][l2] = in2.nextInt();//把數字填入max陣列
			l2++;//填完column
			if (l2 % count1 == 0) {
				c2++;//row 增加
				l2 = 0;//把 column規0
			}
		}
		int c3 = 0;//控制陣列的位置
		while (in3.hasNext()) {//開始讀檔
			Avail[c3] = in3.nextInt();//將數字填入avail陣列
			c3++;//陣列位置增加
		}
		for (int i = 0; i < (count2 / count1); i++) {
			for (int j = 0; j < (count1); j++) {
				TAllo[i][j] = Allo[i][j];//設一個暫存的allocation 陣列 儲存
			}
		}
		for (int i = 0; i < (count2 / count1); i++) {
			for (int j = 0; j < (count1); j++) {//由兩個count相除可以看出process個數
				Need[i][j] = (Max[i][j] - Allo[i][j]);//用max-allocation可以得到need
			}
		}
		System.out.println("Allocation ");//印出allocation字樣
		for (int i = 0; i < (count2 / count1); i++) {
			for (int j = 0; j < (count1); j++) {
				System.out.print(Allo[i][j] + " ");//由雙重迴圈 印出陣列內的每一個值
			}
			System.out.println();
		}
		System.out.println("Max ");//印出max字樣
		for (int i = 0; i < (count2 / count1); i++) {
			for (int j = 0; j < (count1); j++) {
				System.out.print(Max[i][j] + " ");//由雙重迴圈 印出陣列內的每一個值
			}
			System.out.println();

		}
		System.out.println("Available ");//印出available字樣
		for (int i = 0; i < Avail.length; i++) {
			System.out.print(Avail[i] + " ");//由雙重迴圈 印出陣列內的每一個值
		}
		System.out.println();
		System.out.println("Need ");//印出need字樣
		for (int i = 0; i < (count2 / count1); i++) {
			for (int j = 0; j < (count1); j++) {
				System.out.print(Need[i][j] + " ");//由雙重迴圈 印出陣列內的每一個值
			}
			System.out.println();
		}
		int times = ((count2 / count1) * ((count2 / count1) - 1)) / 2;//計算出此演算法最多會執行(n*n-1)/2次
		int cc = 0;
		int work[] = new int[count1];//設一個work陣列 長度與available相同
		for (int i = 0; i < work.length; i++) {//使用一個迴圈跑 available 陣列長度的次數
			work[i] = Avail[i];//初始work 把available的值放入
		}
		int n1 = 0;
		int f[] = new int[(count2 / count1)];//宣告一陣列來判斷是否該地process 已被執行
		boolean ff = true;//布林值為判斷的標準
		while (cc < times) {
			if (check(work, Need, n1) == true) {//檢查是否need小於等於work
				workadd(work, TAllo, n1);//以上符合 開始增加work
				cccheck(f, n1);//記錄哪個process已被執行
			}
			cc++;//執行次數
			n1++;//陣列的位置
			if (n1 >= (count2 / count1)) {
				n1 = 0;
			}
		}
		for (int i = 0; i < f.length; i++) {
			if (f[i] == 0) {//如果判斷式的位置為零代表不被執行
				ff = false;//就判定為unsafe
				break;//結束判斷迴圈
			}
		}
		System.out.println("(1)執行safety Algorithm:");//因出字樣
		if (ff == true) {//如果判斷式為成立的  那麼演算法為安全
			System.out.println("safe");
		
		System.out.println("(2)執行Resource-Request Algorithm:");
		Scanner in4 = new Scanner(System.in);//設立一個掃描器讓使用者輸入
		System.out.print("請輸入欲提出request的process:(數字)");
		int s = in4.nextInt();//讀入使用者輸入的process代號
		System.out.print("請輸入欲提出request的值(空格區分)");
		int Rqest[] = new int[work.length];//此為裝置request值得陣列
		for (int i = 0; i < Rqest.length; i++) {
			Rqest[i] = in4.nextInt();//讓使用者輸入requese值
		}
		int rwork[] = new int[work.length];
		int F[] = new int[(count2 / count1)];
		int dd = 0;
		int n2 = 0;
		if (RRAstep1(Rqest, Need, s) == true) {//檢查第一步驟是否成立
			if (RRAstep2(Rqest, Avail) == true) {//檢查第二步驟是否成立
				Cavail(Avail, Rqest);
				for (int i = 0; i < rwork.length; i++) {
					rwork[i] = Avail[i];
				}
				CAllo(Allo, Rqest, s);//改變allocation
				CNeed(Need, Rqest, s);//改變need
				while (dd < times) {//在執行一次safey 演算法
					if (Rcheck(rwork, Need, n2) == true) {//再次檢查woek是否有比need小
						Rworkadd(rwork, Allo, n2); //增加work
						Rcccheck(F, n2); //確定完成的process
					}
					dd++;
					n2++;
					if (n2 >= (count2 / count1)) {
						n2 = 0;
					}
				}
				for (int i = 0; i < f.length; i++) {
					if (F[i] == 0) {
						ff = false;
						break;
					}
				}
				if (ff == true) {
					System.out.println("safe"); 
				} else {
					System.out.println("unsafe"); 
				}

			} else {
				System.out.print("error Request >Available");//印出第二步驟錯誤字樣
			}
		} else {
			System.out.print("error  Request >Need");//印出第一步驟錯誤字樣
		}
		} else {
			System.out.println("unsafe");//如果判斷式為非  演算法為不安全的
		}
	}

	private static void Rcccheck(int[] f, int n2) {
		f[n2] = 1;
	}

	private static int[] Rworkadd(int[] rwork, int[][] allo, int n2) {
		for (int i = 0; i < rwork.length; i++) {
			rwork[i] += allo[n2][i];
			allo[n2][i] = 0; 
		}
		return rwork; 
	}

	private static boolean Rcheck(int[] rwork, int[][] need, int n2) {
		int ck = 0;
		for (int i = 0; i < rwork.length; i++) {
			if (rwork[i] >= need[n2][i]) {//比較need是否小於等於work
				ck++;//往下比較
			}
		}
		return (ck == rwork.length);//回傳
	}

	private static int[][] CNeed(int[][] need, int[] rqest, int s) {
		for (int i = 0; i < rqest.length; i++) {
			need[s][i] -= rqest[i];//need扣掉request
		}
		return need;//回傳need
	}

	private static int[][] CAllo(int[][] allo, int[] rqest, int s) {
		for (int i = 0; i < rqest.length; i++) {
			allo[s][i] += rqest[i];//allocation 要加上request
		}
		return allo;//回傳allocation
	}

	private static int[] Cavail(int[] avail, int[] rqest) {
		for (int i = 0; i < avail.length; i++) {
			avail[i] -= rqest[i];//將available 扣掉request的值
		}
		return avail;
	}

	private static boolean RRAstep2(int[] rqest, int[] avail) {
		int d = 0;
		for (int i = 0; i < rqest.length; i++) {
			if (rqest[i] <= avail[i]) {//檢查request值有沒有比available小
				d++;//往下檢查
			}
		}
		return (d == rqest.length);//回傳是否為真
	}

	private static boolean RRAstep1(int[] rqest, int[][] need, int s) {
		int d = 0;
		for (int i = 0; i < rqest.length; i++) {
			if (rqest[i] <= need[s][i]) {//檢查每個requese值有沒有比need小
				d++;//往下檢查
			}
		}
		return (d == rqest.length);//回傳是否為真
	}

	private static void cccheck(int[] f, int n1) {
		f[n1] = 1;//或process i 被執行完  就用1代表
	}

	private static boolean check(int[] work, int[][] need, int n1) {
		int ck = 0;
		for (int i = 0; i < work.length; i++) {
			if (work[i] >= need[n1][i]) {//檢查work中的每個值是否都比need大
				ck++;//往下檢查
			}
		}
		return (ck == work.length);//回傳是否為真
	}

	private static int[] workadd(int[] work, int[][] tallo, int n1) {
		for (int i = 0; i < work.length; i++) {
			work[i] += tallo[n1][i];//把allocation累加到work中
			tallo[n1][i] = 0;//加完後allocation歸零
		}
		return work;//回傳累加的work值
	}
}