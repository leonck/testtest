package com.hubsansdk.service;

import android.content.Context;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Parser;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.application.HubsanHandleMessageApp;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.bean.CommonUrl;
import com.hubsansdk.utils.LogX;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Description 心跳
 *
 * @author shengkun.cheng
 * @date 2016/9/26
 * @see [class/class#method]
 * @since [product/model]
 */
public class MAVLinkRelayConnection extends Thread {
    protected Context parentContext;
    private RelayConnectionListener listener;
    private BufferedOutputStream mavOut;
    private InputStream mavIn;
    protected MAVLinkPacket receivedPacket;
    protected Parser parser = new Parser();
    protected byte[] readData = new byte[4096];
    protected int iavailable, i;
    public String serverIP = null;// 连接服务器的IP
    public Integer serverPort = null;// 连接服务器的端口
    private Socket socket = null;// 套节字对象
    public static boolean connected = false; // 关闭连接标志位，true表示关闭，false表示连接

    private Integer setTimeout = 2000;// 超时时间，以毫秒为单位
    public boolean timeOut = false;
    private HubsanHandleMessageApp application;
    private HubsanDrone drone;
    private int readMessageTime = 0;

    public MAVLinkRelayConnection(Context parentContext) {
        this.parentContext = parentContext.getApplicationContext();
        this.listener = (RelayConnectionListener) parentContext;
        application = (HubsanHandleMessageApp) HubsanApplication.getApplication();
        this.drone = application.drone;
        serverIP = CommonUrl.hubsanRelayIp;
        serverPort = CommonUrl.hubsanRelayPort;
    }

    public interface RelayConnectionListener {
        void onRelayReceiveMessage(MAVLinkMessage msg);

        void onRelayConnecct();

        void onRelayDisconnect();
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void run() {
        parser.stats.mavlinkResetStats();
        openConnection();
        while (connected) {
            try {
                readDataBlock();
                handleData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        closeConnection();
    }


    /**
     * 连接socket
     *
     * @return
     */
    public void openConnection() {
        try {
            closeConnection();
            InetAddress address = InetAddress.getByName(serverIP);
            socket = new Socket(address, serverPort);
            mavOut = new BufferedOutputStream((socket.getOutputStream()));
            mavIn = new BufferedInputStream(socket.getInputStream());
            connected = true;
            this.listener.onRelayConnecct();
        } catch (Exception se) {
            se.printStackTrace();
            setDefulValue();
        }
    }


    /**
     * 发送MAVLinkPacket给飞机
     *
     * @param packet
     */
    public void sendMavPacket(MAVLinkPacket packet) {
        if (connected == true) {
            byte[] buffer = packet.encodePacket();
            sendBuffer(buffer);
        }
    }


    /**
     * Description 接收到发送过来的数据
     *
     * @throws IOException
     * @see [class/class#field/class#method]
     */
    public void handleData() {
        if (iavailable < 1) {
            return;
        }
        if (connected) {
            for (i = 0; i < iavailable; i++) {
                receivedPacket = parser.mavlink_parse_char(readData[i] & 0x00ff);
                if (receivedPacket != null) {
                    MAVLinkMessage msg = receivedPacket.unpack();
                    listener.onRelayReceiveMessage(msg);
                }
            }
        }
    }

    private void setDefulValue() {
//        iavailable = 0;
        connected = false;
        this.listener.onRelayDisconnect();
//        receivedPacket = parser.mavlink_parse_char(0x00);
//        if (receivedPacket != null) {
//            MAVLinkMessage msg = receivedPacket.unpack();
//            listener.onRelayReceiveMessage(msg);
//        }
    }


    /**
     * 写数据
     *
     * @param buffer
     * @throws IOException
     */
    public void sendBuffer(byte[] buffer) {
        try {
            if (mavOut != null) {
//                LogX.e("****z中继器sendBuffer*****"+buffer);
                mavOut.write(buffer);
                mavOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataBlock() {
        try {
            if (mavIn != null) {
                iavailable = mavIn.read(readData);
            }
        } catch (IOException e) {
            e.printStackTrace();
            readMessageTime = readMessageTime + 1;
            if (readMessageTime > 5) {
                setDefulValue();
                parser.stats.mavlinkResetStats();
                readMessageTime = 0;
                LogX.e("readDataBlock IOException");
            }
        }
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (mavOut != null) {
                mavOut.close();
                mavOut = null;
            }
            if (mavIn != null) {
                mavIn.close();
                mavIn = null;
            }
            connected = false;
//            stopThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.listener.onRelayDisconnect();
    }


    /**
     * 发送数据，发送失败返回false,发送成功返回true
     *
     * @param socket
     * @param message
     * @return
     */
    public Boolean Send(Socket socket, String message) {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            return true;
        } catch (Exception se) {
            se.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     *
     * @param socket
     * @return
     */
    public Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return true;
        } catch (Exception se) {
            return false;
        }
    }


}
