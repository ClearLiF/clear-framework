package org.clear.framework.test;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ClientThread
 * @packageName : org.clear.framework.test
 * @description : 一般类
 * @date : 2020-07-21 16:37
 **/
public class ClientThread extends Thread{
    Sequence sequence;

    public ClientThread(Sequence sequence) {
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
        ClientThread clientThread = new ClientThread(sequence);
        ClientThread clientThread2 = new ClientThread(sequence);
        ClientThread clientThread3 = new ClientThread(sequence);
        ClientThread clientThread4 = new ClientThread(sequence);
        clientThread.start();
        clientThread2.start();
        clientThread3.start();
        clientThread4.start();
    }
}
class SequenceImpl implements Sequence{
    static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    static int num=0;
    @Override
    public int getNumber() {
       threadLocal.set(threadLocal.get()+1);
        return threadLocal.get();
    }
}
