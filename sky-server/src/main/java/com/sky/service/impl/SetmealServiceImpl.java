package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
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
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增套餐和对应的菜品数据
     *
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void saveWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal  = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //向套餐表插入1条数据
        setmealMapper.insert(setmeal);
        Long id = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(id);
            }
            setmealDishMapper.insertBath(setmealDishes);
        }

    }

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        Page<SetmealVO> setmealPage = setmealMapper.pageQuery(setmealPageQueryDTO);
        long total = setmealPage.getTotal();
        List<SetmealVO> result = setmealPage.getResult();
        System.out.println(result);
        return new PageResult(total, result);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        // 起售套餐不能删除
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus().equals(StatusConstant.ENABLE)) {
                //起售中不能删除
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }

        }

//        for (Long id : ids) {
//            setmealMapper.deleteById(id);
//            dishFlavorMapper.deleteByDishId(id);
//        }
        setmealMapper.deleteBatch(ids);
        setmealDishMapper.deleteBatchBySetmealIds(ids);
    }

    /**
     * 根据id获取套餐
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal= setmealMapper.getById(id);
        List<SetmealDish> setmealDishes=setmealDishMapper.getBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;

    }

    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);

        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealDTO.getId());
            }
            setmealDishMapper.insertBath(setmealDishes);
        }
    }

    @Override
    public void status(Integer status, Long id) {
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        for (SetmealDish setmealDish : setmealDishes) {
            Dish d = dishMapper.getById(setmealDish.getDishId());
            if (d.getStatus().equals(StatusConstant.DISABLE)){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }
        Setmeal dish = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setmealMapper.update(dish);
    }
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
