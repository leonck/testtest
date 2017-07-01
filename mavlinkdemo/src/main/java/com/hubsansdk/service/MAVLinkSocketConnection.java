package com.hubsansdk.service;

import android.content.Context;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Parser;
import com.MAVLink.common.msg_manual_control;
import com.MAVLink.common.msg_mission_item;
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
public class MAVLinkSocketConnection extends Thread {
    protected Context parentContext;
    private MavLinkSocketConnectionListener listener;
    private BufferedOutputStream mavOut;
    private InputStream mavIn;
    protected MAVLinkPacket receivedPacket;
    protected Parser parser = new Parser();
    protected byte[] readData = new byte[4096];
    protected int iavailable = 0, i;
    public String serverIP = null;// 连接服务器的IP
    public Integer serverPort = null;// 连接服务器的端口
    private Socket socket = null;// 套节字对象
    public static boolean connected = false; // 关闭连接标志位，true表示关闭，false表示连接

    private Integer setTimeout = 2000;// 超时时间，以毫秒为单位
    public boolean timeOut = false;
    private HubsanHandleMessageApp application;
    private HubsanDrone drone;
    private int readMessageTime = 0,writeMessageTime = 0;

    public MAVLinkSocketConnection(Context parentContext) {
        this.parentContext = parentContext.getApplicationContext();
        this.listener = (MavLinkSocketConnectionListener) parentContext;
        application = (HubsanHandleMessageApp) HubsanApplication.getApplication();
        this.drone = application.drone;
        // 得到socket的地址
        serverIP = CommonUrl.hubsanHeadIp;
        serverPort = Integer.parseInt(CommonUrl.hubsanHeadPort);
    }

    public interface MavLinkSocketConnectionListener {
        void onReceiveMessage(MAVLinkMessage msg);

        void onConnecct();

        void onDisconnect();
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
            this.listener.onConnecct();
        } catch (Exception se) {
            se.printStackTrace();
            setDefulValue();
            LogX.e("创建连接失败 ******333333333 Exception*******");
        }
    }


    /**
     * 发送MAVLinkPacket给飞机
     *
     * @param packet
     */
    public void sendMavPacket(MAVLinkPacket packet) {
//        LogX.e("发送数据:"+packet.msgid);
//        msg_mission_item data = (msg_mission_item)packet.unpack();
//            LogX.e("udp发送遥感数据  编号" + packet.msgid+"     "+data.toString());
        if (packet.msgid==39){
            msg_mission_item data = (msg_mission_item)packet.unpack();
            LogX.e("发送的航点数据：（"+data.toString());
        }

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
//                    LogX.e("接受到数据：" + receivedPacket.msgid);
                    MAVLinkMessage msg = receivedPacket.unpack();
                    listener.onReceiveMessage(msg);
                }
            }
        }
    }

    private void setDefulValue() {
        connected = false;
        this.listener.onDisconnect();
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
                mavOut.write(buffer);
                mavOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogX.e("====写数据===IOException======");
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
                parser.stats.mavlinkResetStats();
                LogX.e("readDataBlock IOException");
                setDefulValue();
                readMessageTime = 0;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.listener.onDisconnect();
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
