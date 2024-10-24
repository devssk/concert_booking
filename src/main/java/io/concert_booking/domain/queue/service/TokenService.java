package io.concert_booking.domain.queue.service;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenService {

    private final String SECRET_KEY = "TEMP_SECRET_KEY_JDV123CJZXVB1121";

    public String issueToken(Map<String, Long> values) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(SECRET_KEY.substring(0, 16).getBytes());

        byte[] result = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            result = cipher.doFinal(mapToString(values).getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {

        }

        return byteArrayToHexString(result);
    }

    public Map<String, Long> decodeToken(String token) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(SECRET_KEY.substring(0, 16).getBytes());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] bytes = hexStringToByteArray(token);

        String data = new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);

        Map<String, Long> result = stringToMap(data);

        return result;
    }

    private Map<String, Long> stringToMap(String data) {
        Map<String, Long> map = new HashMap<>();

        String[] splitString = data.split(",");
        for (String s : splitString) {
            String[] keyValue = s.split(":");
            map.put(keyValue[0], Long.parseLong(keyValue[1]));
        }
        return map;
    }

    private String mapToString(Map<String, Long> values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long> entry : values.entrySet()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(",");
            }
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return stringBuilder.toString();
    }

    private String byteArrayToHexString(byte[] byteArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : byteArray) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    private byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }


}