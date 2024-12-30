package io.concert_booking.common.other;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomRedisTemplate<K, V> extends RedisTemplate<String, Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> void opsForValueSetAsJson(String key, T data) {
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new ConcertBookingException(ErrorCode.JSON_ERROR);
        }
        this.opsForValue().set(key, jsonData);
    }

    public <T> void opsForValueSetAsJson(String key, T data, long timeOut, TimeUnit timeUnit) {
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new ConcertBookingException(ErrorCode.JSON_ERROR);
        }
        this.opsForValue().set(key, jsonData, timeOut, timeUnit);
    }

    public <T> T opsForValueGetAsJson(String key, Class<T> clazz) {
        String jsonData = (String) this.opsForValue().get(key);
        T t;
        try {
            t = objectMapper.readValue(jsonData, clazz);
        } catch (JsonProcessingException e) {
            throw new ConcertBookingException(ErrorCode.JSON_ERROR);
        }
        return t;
    }

    public <T> T opsForValueGetAsJsonList(String key, TypeReference<T> typeRef) {
        String jsonData = (String) this.opsForValue().get(key);
        T t;
        try {
            t = objectMapper.readValue(jsonData, typeRef);
        } catch (JsonProcessingException e) {
            throw new ConcertBookingException(ErrorCode.JSON_ERROR);
        }
        return t;
    }

}
