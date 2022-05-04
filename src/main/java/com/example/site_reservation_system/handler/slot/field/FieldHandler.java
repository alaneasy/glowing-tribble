package com.example.site_reservation_system.handler.slot.field;

import com.example.site_reservation_system.handler.StaticHandler;
import com.example.site_reservation_system.model.field.Field;
import com.example.site_reservation_system.service.field.FieldService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

/*
 * Created By Alan on 2022/1/11
 * */
@Api("场地")
@RestController
@RequestMapping("/field")
public class FieldHandler {

    @Autowired
    private FieldService fieldService;
    @Autowired
    private StaticHandler staticHandler;

    /*
    *   private Integer id : null;
        private String value : 名称;
        private String phone ： 联系电话;
        private String position : 地址;
        private String uuid : null;
        private Integer status : 场地状态 1:可用 0:不可用;
        private String description : 其他描述;
    * */
    /*
     *   fileList : 文件数组
     * */
    @ApiOperation("场地添加")
    @PostMapping
    @Transactional
    public Result add(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        Field item = mapper.convertValue(params.get("item"), new TypeReference<>() {
        });
        fieldService.insert(item);
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   id : 主键
     * */
    @ApiOperation("单个查询")
    @GetMapping
    public Result query(Integer id) {
        var item = fieldService.queryById(id);
        return ResultFactory.buildSuccessResult().put("field", item).put("fileList", staticHandler.queryList(item.getStaticId()).stream().map(e -> staticHandler.query(e)).collect(Collectors.toList()));
    }

    @ApiOperation("列表查询")
    @GetMapping("/list")
    public Result queryAll(Integer page, Integer size) {
        return ResultFactory.buildSuccessResult().put("fieldList", fieldService.queryAll(page, size));
    }

    @ApiOperation("列表查询")
    @GetMapping("/list_t")
    public Result queryListByValid(Integer page, Integer size, String key) {
        return ResultFactory.buildSuccessResult().put("fieldList", fieldService.queryListByValid(page, size, key));
    }

    /*
     *   需要传入所有信息 包括id
     * */
    @ApiOperation("更新")
    @PutMapping
    public Result update(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        Field item = mapper.convertValue(params.get("item"), new TypeReference<>() {
        });
        fieldService.updateById(item);
        return ResultFactory.buildSuccessResult();
    }

    @PutMapping("/switch")
    public Result switcher(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        Integer id = mapper.convertValue(params.get("id"), new TypeReference<>() {
        });
        Field item = fieldService.queryById(id);
        item.setStatus(item.getStatus() ^ 1);
        fieldService.updateById(item);
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   id : 主键
     * */
    @ApiOperation("删除")
    @DeleteMapping
    public Result delete(Integer id) {
        fieldService.deleteById(id);
        return ResultFactory.buildSuccessResult();
    }
}