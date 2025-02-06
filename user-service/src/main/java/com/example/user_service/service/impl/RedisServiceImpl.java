package com.example.user_service.service.impl;

import com.example.user_service.exception.CacheException;
import com.example.user_service.service.RedisService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;
    private ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    @Override
    public <T> T getData(String key, Class<T> responseClass) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            if (object != null) {
                if (responseClass.equals(List.class)) {
                    return (T) objectMapper.readValue(object.toString(), new TypeReference<List<T>>() {
                    });
                } else {
                    return getObjectMapper().readValue(object.toString(), responseClass);
                }
            }
        } catch (Exception exception) {
            throw new CacheException(exception.getMessage());
        }
        return null;
    }

    @Override
    public void setData(String key, Object o, Long ttl) {

        try {
            String jsonValue = getObjectMapper().writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception exception) {
            throw new CacheException(exception.getMessage());
        }
    }

    @Override
    public void deleteData(String key) {
        try {
            redisTemplate.opsForValue().getAndDelete(key);
        } catch (Exception exception) {
            throw new CacheException(exception.getMessage());
        }
    }
}
