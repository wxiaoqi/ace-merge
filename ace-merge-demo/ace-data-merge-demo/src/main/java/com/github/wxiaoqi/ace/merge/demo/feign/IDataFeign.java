package com.github.wxiaoqi.ace.merge.demo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ace
 * @create 2017/11/20.
 */
@FeignClient("ace-data-provider")
public interface IDataFeign {
    @RequestMapping("data/sex")
    public Map<String, String> getGenders(@RequestParam("key") String key);

    @RequestMapping("data/city")
    public Map<String, String> getCities(@RequestParam("ids") String ids);
}
