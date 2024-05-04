package kopo.poly.persistance.redis;

import kopo.poly.dto.RedisDTO;

public interface IMyRedisMapper {

    /**
     * String 타입 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pDTO     저장할 정보
     * @return 저장 성공 여부
     */
    int saveString(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     * String 타입 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 결과 값
     */
    RedisDTO getString(String redisKey) throws Exception;
}
