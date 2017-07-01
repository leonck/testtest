/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE HUBSAN_SD_STATUS PACKING
package com.MAVLink.hubsan;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

/**
* obtain sd status.
*/
public class msg_hubsan_sd_status extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_HUBSAN_SD_STATUS = 57;
    public static final int MAVLINK_MSG_LENGTH = 11;
    private static final long serialVersionUID = MAVLINK_MSG_ID_HUBSAN_SD_STATUS;


      
    /**
    * SD card capacity,size in MB
    */
    public float sd_capacity;
      
    /**
    * SD card surplus capacity,size in MB
    */
    public float sd_surplus;
      
    /**
    * System ID
    */
    public short target_system;
      
    /**
    * Component ID
    */
    public short target_component;
      
    /**
    * sd card status,as defined by MAV_SD_STATUS enum
    */
    public short sd_status;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket();
        packet.len = MAVLINK_MSG_LENGTH;
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_HUBSAN_SD_STATUS;
              
        packet.payload.putFloat(sd_capacity);
              
        packet.payload.putFloat(sd_surplus);
              
        packet.payload.putUnsignedByte(target_system);
              
        packet.payload.putUnsignedByte(target_component);
              
        packet.payload.putUnsignedByte(sd_status);
        
        return packet;
    }

    /**
    * Decode a hubsan_sd_status message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.sd_capacity = payload.getFloat();
              
        this.sd_surplus = payload.getFloat();
              
        this.target_system = payload.getUnsignedByte();
              
        this.target_component = payload.getUnsignedByte();
              
        this.sd_status = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_hubsan_sd_status(){
        msgid = MAVLINK_MSG_ID_HUBSAN_SD_STATUS;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_hubsan_sd_status(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_HUBSAN_SD_STATUS;
        unpack(mavLinkPacket.payload);        
    }

              
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_HUBSAN_SD_STATUS -"+" sd_capacity:"+sd_capacity+" sd_surplus:"+sd_surplus+" target_system:"+target_system+" target_component:"+target_component+" sd_status:"+sd_status+"";
    }
}
        