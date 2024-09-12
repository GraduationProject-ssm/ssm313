package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.KechengbiaoEntity;
import com.entity.view.KechengbiaoView;

import com.service.KechengbiaoService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 课程表
 * 后端接口
 * @author 
 * @email 
 * @date 2021-04-27 17:44:59
 */
@RestController
@RequestMapping("/kechengbiao")
public class KechengbiaoController {
    @Autowired
    private KechengbiaoService kechengbiaoService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,KechengbiaoEntity kechengbiao, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeriqiend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			kechengbiao.setJiaoshizhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			kechengbiao.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<KechengbiaoEntity> ew = new EntityWrapper<KechengbiaoEntity>();
                if(shangkeriqistart!=null) ew.ge("shangkeriqi", shangkeriqistart);
                if(shangkeriqiend!=null) ew.le("shangkeriqi", shangkeriqiend);
		PageUtils page = kechengbiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengbiao), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,KechengbiaoEntity kechengbiao, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeriqiend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			kechengbiao.setJiaoshizhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			kechengbiao.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<KechengbiaoEntity> ew = new EntityWrapper<KechengbiaoEntity>();
                if(shangkeriqistart!=null) ew.ge("shangkeriqi", shangkeriqistart);
                if(shangkeriqiend!=null) ew.le("shangkeriqi", shangkeriqiend);
		PageUtils page = kechengbiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengbiao), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( KechengbiaoEntity kechengbiao){
       	EntityWrapper<KechengbiaoEntity> ew = new EntityWrapper<KechengbiaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( kechengbiao, "kechengbiao")); 
        return R.ok().put("data", kechengbiaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(KechengbiaoEntity kechengbiao){
        EntityWrapper< KechengbiaoEntity> ew = new EntityWrapper< KechengbiaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( kechengbiao, "kechengbiao")); 
		KechengbiaoView kechengbiaoView =  kechengbiaoService.selectView(ew);
		return R.ok("查询课程表成功").put("data", kechengbiaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        KechengbiaoEntity kechengbiao = kechengbiaoService.selectById(id);
        return R.ok().put("data", kechengbiao);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        KechengbiaoEntity kechengbiao = kechengbiaoService.selectById(id);
        return R.ok().put("data", kechengbiao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody KechengbiaoEntity kechengbiao, HttpServletRequest request){
    	kechengbiao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(kechengbiao);

        kechengbiaoService.insert(kechengbiao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody KechengbiaoEntity kechengbiao, HttpServletRequest request){
    	kechengbiao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(kechengbiao);
    	kechengbiao.setUserid((Long)request.getSession().getAttribute("userId"));

        kechengbiaoService.insert(kechengbiao);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody KechengbiaoEntity kechengbiao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(kechengbiao);
        kechengbiaoService.updateById(kechengbiao);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        kechengbiaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<KechengbiaoEntity> wrapper = new EntityWrapper<KechengbiaoEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			wrapper.eq("jiaoshizhanghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			wrapper.eq("zhanghao", (String)request.getSession().getAttribute("username"));
		}

		int count = kechengbiaoService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
