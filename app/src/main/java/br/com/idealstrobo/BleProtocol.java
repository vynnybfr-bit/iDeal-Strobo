package br.com.idealstrobo;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Protocol helpers based on the published 8none1/idealLED implementation.
 * Hardware transmission must be validated against the exact controller first.
 */
public final class BleProtocol {
    private BleProtocol() {}
    private static final byte[] KEY = {
        0x34,0x52,0x2A,0x5B,0x7A,0x6E,0x49,0x2C,
        0x08,0x09,0x0A,(byte)0x9D,(byte)0x8D,0x2A,0x23,(byte)0xF8
    };

    public static byte[] encrypt(byte[] clear) throws Exception {
        if (clear.length != 16) throw new IllegalArgumentException("AES blocks must be 16 bytes");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, "AES"));
        return cipher.doFinal(clear);
    }

    public static byte[] lampCount(int count) throws Exception {
        if (count < 1 || count > 255) throw new IllegalArgumentException("count out of range");
        byte hi = (byte)(count >>> 8), lo = (byte)count;
        return encrypt(new byte[]{9,76,65,77,80,78,hi,lo,hi,lo,0,0,0,0,0,0});
    }

    public static List<byte[]> repeatingFourPattern(int ledCount) {
        if (ledCount < 4 || ledCount % 4 != 0) throw new IllegalArgumentException("LED count must be a multiple of four");
        List<byte[]> instructions = new ArrayList<>();
        for (int i=0; i<ledCount/4; i++) {
            // 3 warm-white steady, then 1 cool-white flashing.
            instructions.add(new byte[]{0,3,(byte)255,(byte)220,(byte)160,2,100});
            instructions.add(new byte[]{0,1,(byte)220,(byte)235,(byte)255,0,100});
        }
        List<byte[]> packets = new ArrayList<>();
        for (int start=0, seq=0; start<instructions.size(); start+=14, seq++) {
            int end=Math.min(start+14,instructions.size());
            byte[] packet=new byte[2+(end-start)*7];
            packet[0]=(byte)(packet.length-1);
            packet[1]=(byte)seq;
            int p=2;
            for(int i=start;i<end;i++) for(byte value:instructions.get(i)) packet[p++]=value;
            packets.add(packet);
        }
        return packets;
    }

    public static byte[] beginBulk(int packetCount) throws Exception {
        if (packetCount < 1 || packetCount > 255) throw new IllegalArgumentException("packet count out of range");
        return encrypt(new byte[]{4,68,79,79,84,(byte)packetCount,0,0,0,0,0,0,0,0,0,0});
    }
    public static byte[] endBulk() throws Exception {
        return encrypt(new byte[]{6,68,79,79,84,67,80,0,0,0,0,0,0,0,0,0});
    }
    public static byte[] startCycle() throws Exception {
        return encrypt(new byte[]{8,68,84,65,82,84,67,89,1,0,0,0,0,0,0,0});
    }
}