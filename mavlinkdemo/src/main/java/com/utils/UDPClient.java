package com.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
 * Description 
 *          发送摇杆数据
 * @author ShengKun.Cheng
 * @date  2015年11月9日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
@SuppressWarnings("unused")
public class UDPClient {
    
    private DatagramSocket socket;
    
    private int serverPort = 8867;
    
    private int hostPort;
    
    private InetAddress hostAdd;
    
    protected byte[] readData = new byte[4096];
    
    public UDPClient(Context context) {
    
        super();
    }
    
    public void sendBuffer(byte[] buffer) throws IOException {
    
        new UdpSender().execute(buffer);
        
    }
    
    /**
     * 
     * Description 
     *          发送数据
     * @author ShengKun.Cheng
     * @date  2015年11月9日
     * @version 
     * @see [class/class#method]
     * @since [product/model]
     */
    private class UdpSender extends AsyncTask<byte[], Integer, Integer> {
        
        @Override
        protected Integer doInBackground(byte[]... params) {
            try {
                byte[] buffer = params[0];
                //首先创建一个DatagramSocket对象
                if (socket==null) {
                    socket = new DatagramSocket(serverPort);
                }
                //创建一个InetAddree
                InetAddress serverAddress = InetAddress.getByName(Constants.TCP_PREF_SERVER_IP);
                //            String str = "hello"; //这是要传输的数据
                //            byte data[] = str.getBytes(); //把传输内容分解成字节
                
                //创建一个DatagramPacket对象，并指定要讲这个数据包发送到网络当中的哪个、地址，以及端口号
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                //调用socket对象的send方法，发送数据
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
                
            }
            return null;
        }
    }
    
    /**
     * 读取地址
     * Description 
     * @throws IOException
     * @see [class/class#field/class#method]
     */
    public void readDataBlock() {
    
        try {
            DatagramPacket packet = new DatagramPacket(readData, readData.length);
            socket.receive(packet);
            hostAdd = packet.getAddress();
            hostPort = packet.getPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 连接UDP
     * Description 
     * @throws IOException
     * @see [class/class#field/class#method]
     */
    public void getUdpStream() {
    
        try {
            if (socket == null) {
                socket = new DatagramSocket(serverPort);
            }
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
