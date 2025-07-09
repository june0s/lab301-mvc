package egovframework.lab.util;

import io.lettuce.core.protocol.ProtocolKeyword;

import java.nio.charset.StandardCharsets;

public enum CommandTypeEx implements ProtocolKeyword {
    JSON_GET("JSON.GET"),
    JSON_SET("JSON.SET")
    ;

    public final byte[] bytes;
    public String code;

    private CommandTypeEx(String code) {

        this.code = code;
        this.bytes = code.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
