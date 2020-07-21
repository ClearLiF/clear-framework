package org.clear.framework.test.threadLocal2;

import org.clear.framework.test.Sequence;
import org.clear.framework.test.threadLocal.MyThreadLocal;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ClientThread
 * @packageName : org.clear.framework.test
 * @description : 一般类
 * @date : 2020-07-21 16:37
 **/
public class ClientThread2 extends Thread{
    Sequence sequence;

    public ClientThread2(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            System.out.println(Thread.currentThread().getName()+"=>"+sequence.getNumber());
        }

    }

    public static void main(String[] args) {
        SequenceImpl sequence = new SequenceImpl();
        ClientThread2 clientThread = new ClientThread2(sequence);
        ClientThread2 clientThread2 = new ClientThread2(sequence);
        ClientThread2 clientThread3 = new ClientThread2(sequence);
        ClientThread2 clientThread4 = new ClientThread2(sequence);
        clientThread.start();
        clientThread2.start();
        clientThread3.start();
        clientThread4.start();
    }
}

class SequenceImpl implements Sequence{
    static MyThreadLocal<Integer> threadLocal = new MyThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    static int num=0;
    @Override
    public int getNumber() {
       threadLocal.set(threadLocal.get()+1);
        return threadLocal.get();
    }
}
