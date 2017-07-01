/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE HEARTBEAT PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

/**
* The heartbeat message shows that a system is present and responding. The type of the MAV and Autopilot hardware allow the receiving system to treat further messages from this system appropriate (e.g. by laying out the user interface based on the autopilot).
*/
public class msg_heartbeat extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_HEARTBEAT = 0;
    public static final int MAVLINK_MSG_LENGTH = 9;
    private static final long serialVersionUID = MAVLINK_MSG_ID_HEARTBEAT;


      
    /**
    * A bitfield for use for autopilot-specific flags.
    */
    public long custom_mode;
      
    /**
    * Type of the MAV (quadrotor, helicopter, etc., up to 15 types, defined in MAV_TYPE ENUM)
    */
    public short type;
      
    /**
    * Autopilot type / class. defined in MAV_AUTOPILOT ENUM
    */
    public short autopilot;
      
    /**
    * System mode bitfield, see MAV_MODE_FLAG ENUM in mavlink/include/mavlink_types.h
    */
    public short base_mode;
      
    /**
    * System status flag, see MAV_STATE ENUM
    */
    public short system_status;
      
    /**
    * MAVLink version, not writable by user, gets added by protocol because of magic data type: uint8_t_mavlink_version
    */
    public short mavlink_version;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket();
        packet.len = MAVLINK_MSG_LENGTH;
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_HEARTBEAT;
              
        packet.payload.putUnsignedInt(custom_mode);
              
        packet.payload.putUnsignedByte(type);
              
        packet.payload.putUnsignedByte(autopilot);
              
        packet.payload.putUnsignedByte(base_mode);
              
        packet.payload.putUnsignedByte(system_status);
              
        packet.payload.putUnsignedByte(mavlink_version);
        
        return packet;
    }

    /**
    * Decode a heartbeat message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.custom_mode = payload.getUnsignedInt();
              
        this.type = payload.getUnsignedByte();
              
        this.autopilot = payload.getUnsignedByte();
              
        this.base_mode = payload.getUnsignedByte();
              
        this.system_status = payload.getUnsignedByte();
              
        this.mavlink_version = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_heartbeat(){
        msgid = MAVLINK_MSG_ID_HEARTBEAT;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_heartbeat(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_HEARTBEAT;
        unpack(mavLinkPacket.payload);        
    }

                
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_HEARTBEAT -"+" custom_mode:"+custom_mode+" type:"+type+" autopilot:"+autopilot+" base_mode:"+base_mode+" system_status:"+system_status+" mavlink_version:"+mavlink_version+"";
    }
}
        