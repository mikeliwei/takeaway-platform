package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    private StringRedisTemplate template;

    private final String KEY = "SHOP_STATUS";

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺状态为：{}", status == 1 ? "营业" : "休息");
        template.opsForValue().set(KEY, status.toString());
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus() {
        String res = template.opsForValue().get(KEY);
        log.info("店铺状态为：{}", "1".equals(res) ? "营业" : "休息");
        return Result.success(Integer.parseInt(res));
    }
}
