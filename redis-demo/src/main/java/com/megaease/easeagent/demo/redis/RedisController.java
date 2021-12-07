/*
 * Copyright (c) 2017, MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.megaease.easeagent.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/age")
    public String redisTemplate() {
        try {
            if (!stringRedisTemplate.hasKey("age")) {
                stringRedisTemplate.opsForValue().set("age", "1");
            }
            long age = stringRedisTemplate.opsForValue().increment("age");
            return String.format("age : %s", age);
        } finally {
        }
    }

    @GetMapping("/age_jedis")
    public String jedis() {
        Jedis jedis = RedisUtil.getJedis();
        try {
            if (!jedis.exists("age")) {
                jedis.set("age", "1");
            }
            long age = jedis.incr("age");
            return String.format("age : %s", age);
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }
}
