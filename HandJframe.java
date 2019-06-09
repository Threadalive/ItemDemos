import javax.swing.*;
import java.awt.*;

public class HandJframe extends JPanel {
    private Graphics g;
    private HandListen handListen;//监听器



    public void Plaette(){
        JFrame jf=new JFrame();
        jf.setSize(600,600);
        jf.setTitle("手写识别");

        //结束进程
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);   //窗口不可更改
        jf.setLayout(new FlowLayout());//流式布局管理器
        this.setPreferredSize(new Dimension(400,400));//400，400的面板
        this.setBackground(Color.black);
        jf.add(this);
        handListen=new HandListen(this);
        //set button
        String chose[]={"sample save","indentify"};
        for(int i=0;i<chose.length;i++){
            JButton jbu=new JButton(chose[i]);
            jf.add(jbu);
            jbu.addActionListener(handListen);
        }


     //下拉框
        JComboBox jcb;
        String str1[]={"0","1","2","3","4","5"};
        jcb=new JComboBox(str1);
        jf.add(jcb);
        handListen.setJ(jcb);


        this.addMouseMotionListener(handListen);
        this.addMouseListener(handListen);

        jf.setVisible(true);
//传画笔
        g=this.getGraphics();
        handListen.setG(g);

    }



public static void main(String args[]){
        HandJframe handJframe=new HandJframe();
        handJframe.Plaette();
}

public   void paint(Graphics g){
        super.paint(g);
}

}
