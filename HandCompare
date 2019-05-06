public class HandCompare {
    private int [][]num;
    private int [][]sample;
    public HandCompare(int [][]num,int[][]sample){//获取当前数组与样本数组
        this.num=num;
        this.sample=sample;
    }
    public double Compare(){
        double a=0;
        for(int i=0;i<401;i++){//这样的效果相当于得出这两张图的不重叠的面积
            for (int j=0;j<401;j++){
                if (num[i][j]!=sample[i][j]){
                    a++;
                }
            }
        }
        return a+(sample[0][0]/10.0);//在最后的数据上还能保存这个样本的类型

    }


}


