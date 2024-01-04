package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface SetmealDishService {

    /**
     * 新增套餐和对应的口味数据
     * @param setmealDTO
     */
    void saveWithDishes(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 修改套餐
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    void status(Integer status, Long id);
}
