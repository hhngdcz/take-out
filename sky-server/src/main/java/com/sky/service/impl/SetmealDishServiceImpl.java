package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐业务层
 */
@Service
@Slf4j
public class SetmealDishServiceImpl implements SetmealDishService {


    @Override
    public void saveWithDishes(SetmealDTO setmealDTO) {

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        return null;
    }

    @Override
    public void deleteBatch(List<Long> ids) {

    }

    @Override
    public DishVO getById(Long id) {
        return null;
    }

    @Override
    public void update(DishDTO dishDTO) {

    }

    @Override
    public void status(Integer status, Long id) {

    }
}
