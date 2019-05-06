import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.rmi.server.UID;
import java.util.Arrays;
import java.util.HashMap;

public class HandListen extends MouseAdapter implements ActionListener {
    private String fileNameTamp;//文件名称与路径
    private HandJframe handJframe;
    private HandCompare handCompare;
    private JComboBox jcb;
    private Graphics g;

    private int x, y;
    private int k = 5;


    private int[][] num = new int[401][401];
    private int[][] sample = new int[401][401];

    {
        for (int i = 0; i < 401; i++) {
            for (int j = 0; j < 401; j++) {
                num[i][j] = 0;
            }
        }
    }

    public HandListen(HandJframe handJframe) {
        this.handJframe = handJframe;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

    public void setJ(JComboBox jcb) {
        this.jcb = jcb;
    }

    public void mouseGragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 30, 30);
        for (int i = x; i < x + 30; i++) {
            for (int j = y; j < y + 30; y++) {
                num[i][j] = 1;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jcb.getSelectedItem() == "0") {
            num[0][0] = 0;
        } else if (jcb.getSelectedItem() == "1") {
            num[0][0] = 1;
        } else if (jcb.getSelectedItem() == "2") {
            num[0][0] = 2;
        } else if (jcb.getSelectedItem() == "3") {
            num[0][0] = 3;
        } else if (jcb.getSelectedItem() == "4") {
            num[0][0] = 4;
        } else if (jcb.getSelectedItem() == "5") {
            num[0][0] = 5;
        }

        String path = "/Users/mac/Documents/java/样本";
        String fileName = null;
        if ("样品保存".equals(e.getActionCommand())) {// 获取当前事件源对象
            UID uid=new UID();
            fileName="样本"+uid.hashCode();
            fileNameTamp= path+fileName+".txt";
            File file=new File(fileNameTamp);
            //如果文件不存在，则创建新的文件

                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //创建文件成功后，写入内容到文件里
                }
                try {
                    FileWriter out = new FileWriter(file); //文件写入流
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            for(int i=0;i<401;i++){
                for(int j=0;j<401;j++){
                    num[i][j]=0;
                }
            }
            handJframe.paint(g);//重绘



        }
        if (("识别").equals(e.getActionCommand())) {// 取出所有的样品进行knn对比，寻找出于两类样品差距最小的一类，得出识别的结果
       File file=new File("/Users/mac/Documents/java/样本");
            String filelist[]=file.list();
            Double value[]=new Double [filelist.length];//获取相似程度的数组
            System.out.println(filelist.length);
            for(int i=0;i<filelist.length;i++) {
                File file1 = new File("/Users/mac/Documents/java/样本" + filelist[i]);
                System.out.println(filelist[i]);
                try {
                    FileReader in = new FileReader(file1);
                    for (int k = 0; k < 401; k++) {
                        for (int j = 0; j < 401; j++) {
                            sample[k][j] = in.read();//获取文档传来的数组
                        }
                    }
                    in.close();//关闭流
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                handCompare = new HandCompare(num, sample);//将需要进行对比的两个数组传入
                value[i] = handCompare.Compare();//得出相似度
            }
                Arrays.sort(value);

                int one=0;
                int zero=0;
                int two=0;
                int three=0;
                int four=0;
                int five=0;
                HashMap hashMap=new HashMap();//建立哈希函数一一对应的关系
                for(int i=0;i<k;i++){//累加和判断最后的类型
                    System.out.println(value[i]);
                    if(((int)(value[i]*10))%10==1){//入围k的数目
                        one++;
                        hashMap.put(one,1);//
                    }else if (((int)(value[i]*10))%10==0){
                        zero++;
                        hashMap.put(zero,0);
                    }else if (((int)(value[i]*10))%10==2){
                        two++;
                        hashMap.put(two,2);
                    }else if (((int)(value[i]*10))%10==3){
                        three++;
                        hashMap.put(three,3);
                    }else if (((int)(value[i]*10))%10==4){
                        four++;
                        hashMap.put(four,4);
                    }else if (((int)(value[i]*10))%10==5){
                        five++;
                        hashMap.put(five,5);
                    }

                }

            int judge[]={zero,one,three,two,four,five};
            Arrays.sort(judge);//进行升序排序
            int a=judge.length;
            JOptionPane.showMessageDialog(null, "识别为"+hashMap.get(judge[a-1])); // 弹出最后的识别结果

        }
}}