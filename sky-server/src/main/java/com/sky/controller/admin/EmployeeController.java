package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeAddVO;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping()
    @ApiOperation(value  = "新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}", employeeDTO);
        System.out.println("当先线程id"+Thread.currentThread().getId());
        employeeService.save(employeeDTO);

        return Result.success();
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value  = "员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){

        return Result.success(employeeService.pageQuery(employeePageQueryDTO));
    }

    /**
     * 启用或禁用员工
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value  = "启用或禁用员工")
    public Result status(@PathVariable Integer status,Long id){
        employeeService.status(status,id);
        return Result.success();

    }

    @GetMapping("/{id}")
    @ApiOperation(value  = "根据ID获取员工")
    public Result<Employee> getById(@PathVariable Long id){
        return Result.success(employeeService.getById(id));
    }
    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result update(@RequestBody  EmployeeDTO employeeDTO){
        employeeService.update(employeeDTO);
        return Result.success();
    }

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value  = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(), //秘钥
                jwtProperties.getAdminTtl(), //过期时间
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value  = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }




}
