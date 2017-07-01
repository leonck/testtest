/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE HIL_RC_INPUTS_RAW PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

/**
* Sent from simulation to autopilot. The RAW values of the RC channels received. The standard PPM modulation is as follows: 1000 microseconds: 0%, 2000 microseconds: 100%. Individual receivers/transmitters might violate this specification.
*/
public class msg_hil_rc_inputs_raw extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW = 92;
    public static final int MAVLINK_MSG_LENGTH = 33;
    private static final long serialVersionUID = MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW;


      
    /**
    * Timestamp (microseconds since UNIX epoch or microseconds since system boot)
    */
    public long time_usec;
      
    /**
    * RC channel 1 value, in microseconds
    */
    public int chan1_raw;
      
    /**
    * RC channel 2 value, in microseconds
    */
    public int chan2_raw;
      
    /**
    * RC channel 3 value, in microseconds
    */
    public int chan3_raw;
      
    /**
    * RC channel 4 value, in microseconds
    */
    public int chan4_raw;
      
    /**
    * RC channel 5 value, in microseconds
    */
    public int chan5_raw;
      
    /**
    * RC channel 6 value, in microseconds
    */
    public int chan6_raw;
      
    /**
    * RC channel 7 value, in microseconds
    */
    public int chan7_raw;
      
    /**
    * RC channel 8 value, in microseconds
    */
    public int chan8_raw;
      
    /**
    * RC channel 9 value, in microseconds
    */
    public int chan9_raw;
      
    /**
    * RC channel 10 value, in microseconds
    */
    public int chan10_raw;
      
    /**
    * RC channel 11 value, in microseconds
    */
    public int chan11_raw;
      
    /**
    * RC channel 12 value, in microseconds
    */
    public int chan12_raw;
      
    /**
    * Receive signal strength indicator, 0: 0%, 255: 100%
    */
    public short rssi;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket();
        packet.len = MAVLINK_MSG_LENGTH;
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW;
              
        packet.payload.putUnsignedLong(time_usec);
              
        packet.payload.putUnsignedShort(chan1_raw);
              
        packet.payload.putUnsignedShort(chan2_raw);
              
        packet.payload.putUnsignedShort(chan3_raw);
              
        packet.payload.putUnsignedShort(chan4_raw);
              
        packet.payload.putUnsignedShort(chan5_raw);
              
        packet.payload.putUnsignedShort(chan6_raw);
              
        packet.payload.putUnsignedShort(chan7_raw);
              
        packet.payload.putUnsignedShort(chan8_raw);
              
        packet.payload.putUnsignedShort(chan9_raw);
              
        packet.payload.putUnsignedShort(chan10_raw);
              
        packet.payload.putUnsignedShort(chan11_raw);
              
        packet.payload.putUnsignedShort(chan12_raw);
              
        packet.payload.putUnsignedByte(rssi);
        
        return packet;
    }

    /**
    * Decode a hil_rc_inputs_raw message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.time_usec = payload.getUnsignedLong();
              
        this.chan1_raw = payload.getUnsignedShort();
              
        this.chan2_raw = payload.getUnsignedShort();
              
        this.chan3_raw = payload.getUnsignedShort();
              
        this.chan4_raw = payload.getUnsignedShort();
              
        this.chan5_raw = payload.getUnsignedShort();
              
        this.chan6_raw = payload.getUnsignedShort();
              
        this.chan7_raw = payload.getUnsignedShort();
              
        this.chan8_raw = payload.getUnsignedShort();
              
        this.chan9_raw = payload.getUnsignedShort();
              
        this.chan10_raw = payload.getUnsignedShort();
              
        this.chan11_raw = payload.getUnsignedShort();
              
        this.chan12_raw = payload.getUnsignedShort();
              
        this.rssi = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_hil_rc_inputs_raw(){
        msgid = MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_hil_rc_inputs_raw(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW;
        unpack(mavLinkPacket.payload);        
    }

                                
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW -"+" time_usec:"+time_usec+" chan1_raw:"+chan1_raw+" chan2_raw:"+chan2_raw+" chan3_raw:"+chan3_raw+" chan4_raw:"+chan4_raw+" chan5_raw:"+chan5_raw+" chan6_raw:"+chan6_raw+" chan7_raw:"+chan7_raw+" chan8_raw:"+chan8_raw+" chan9_raw:"+chan9_raw+" chan10_raw:"+chan10_raw+" chan11_raw:"+chan11_raw+" chan12_raw:"+chan12_raw+" rssi:"+rssi+"";
    }
}
        