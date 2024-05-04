package kopo.poly.service;

import kopo.poly.dto.RedisDTO;

public interface IMyRedisService {

    /**
     * String 타입 저장 및 가져오기
     */
    RedisDTO saveString(RedisDTO pDTO) throws Exception;
}
