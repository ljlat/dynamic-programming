import java.util.Random;
import java.util.Scanner;


class Coin {
    int money;
    int num;
    public Coin(int money, int num) {
        this.money = money;
        this.num = num;
    }
}
public class dynamicprogramming {
    public static int maxCoin(int[] n) {
        int a = 0, b = n[0], c = 0;
        for (int i = 1; i < n.length; i++) {
            c = Math.max(a + n[i], b);
            a = b;
            b = c;
        }
        return c;
    }
    /**
     * @param n        硬币
     * @param isChoose 前一个数是否使用
     * @param point    当前位置指针
     * @param money    钱
     * @return 每个位置可以选，也可以不选。当选了的时候，后一个位置不能选，返回跳过。没选的时候后一个位置可选可不选，有两种情况，返回较大的数
     */
    public static int recMaxCoin_(int[] n, boolean isChoose, int point, int money) {
        if (point == n.length) {//到最后位置 数返回钱
            return money;
        } else if (isChoose) {
            return recMaxCoin_(n, false, point + 1, money);
        } else {
            return Math.max(recMaxCoin_(n, false, point + 1, money), recMaxCoin_(n, true, point + 1, money + n[point]));
        }
    }
    public static int recMaxCoin(int[] n) {
        return Math.max(recMaxCoin_(n, false, 1, 0), recMaxCoin_(n, true, 1, n[0]));
    }
    public static int minCoin(int[] n, int num) {
        Coin[] coin = new Coin[num + 1];
        coin[0] = new Coin(0, 0);
        for (int i = 1; i <= num; i++) {
            int j = 0, t = Integer.MAX_VALUE;
            while (j < n.length && n[j] <= i) {
                int temp = F(i - n[j], i, coin);
                if (temp == -1) {
                    System.out.println("找零问题出错!!! money = " + (i - n[j]) + " 找不到对应最小硬币个数！");
                }
                t = Math.min(temp, t);
                j++;
            }
            coin[i] = new Coin(i, t + 1);
        }
        return coin[num].num;
    }
    public static int recMinCoin(int[] n, int num) {
        int min = num;
        for (int i : n) {
            if (i == num)
                return 1;
        }
        int mincoin = 0;
        for (int i : n) {
            if (i < num) {
                mincoin = 1 + recMinCoin(n, num - i);
                if (mincoin < min) {
                    min = mincoin;
                }
            }
        }
        return min;
    }
    public static int F(int money, int maxIndex, Coin[] coins) {
        for (int i = 0; i < maxIndex; i++) {
            if (coins[i].money == money)
                return coins[i].num;
        }
        return -1;
    }
    public static int[] initArray(int len) {
        int[] n = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            n[i] = random.nextInt(9)+1;
        }
        return n;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入币值最大化硬币个数:");
        int len = in.nextInt();
        System.out.println("请输入最小找零数:");
        int money = in.nextInt();
        int[] n = initArray(len);
        int[] m = {1,3,5};
        int p=0;
        System.out.println("随机生成的硬币:");
        for(int s:n){
            p++;
            System.out.print(s+" ");
            if(p%10==0)
                System.out.println();
        }
        System.out.println();
        long a=System.nanoTime();
        System.out.println("币值最大化 动态规划 最大币值:" + maxCoin(n));
        long b = System.nanoTime();
        System.out.println("耗时:" + (double)(b - a)/1000000 + "ms" );
        a=System.nanoTime();
        System.out.println("币值最大化 递归求解 最大币值:" + recMaxCoin(n));
        b=System.nanoTime();
        System.out.println("耗时:" + (double)(b - a)/1000000 + "ms");
        System.out.println("找零初始硬币:");
        for(int s:m)
            System.out.print(s+" ");
        System.out.println();
        a=System.nanoTime();
        System.out.println("找零 动态规划最少硬币个数:" + minCoin(m, money) + "  零钱: "+money);
        b=System.nanoTime();
        System.out.println("耗时:" + (double)(b - a)/1000000 + "ms");
        a=System.nanoTime();
        System.out.println("找零 递归求解最少硬币个数:" + recMinCoin(m, money) + "  零钱: "+money);
        b=System.nanoTime();
        System.out.println("耗时:" + (double)(b - a)/1000000+ "ms");
        in.close();
    }
}