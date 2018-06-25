package com.jesper.controller;
/**
 * 课程管理
 * @author KL-MZY
 *
 */

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jesper.mapper.CourseMapper;
import com.jesper.model.Course;
import com.jesper.util.DateUtil;
import com.jesper.util.ExcelUtil;
import com.jesper.util.PageUtil;
@Controller
public class CourseController {
	
	private static Logger logger =Logger.getLogger(CourseController.class);
	
	List<Course> courseList=null;
	
	@Autowired
	private CourseMapper courseMapper;
	/**
	 * 课程列表
	 * @param course
	 * @param pageCurrent
	 * @param pageSize
	 * @param pageCount
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/courseManage_{pageCurrent}_{pageSize}_{pageCount}")
	public String courseManage(Course course,@PathVariable Integer pageCurrent,
            @PathVariable Integer pageSize,
            @PathVariable Integer pageCount,Model model) {
		logger.info("课程管理接口: \t course {"+course.getTitle()+" } ");		
		
		if(pageSize==0) pageSize =5 ;
		if(pageCurrent==0) pageCurrent =1;
        // 所有记录数
		Integer rows=courseMapper.count(course);
		logger.info(" rows :"+rows);
		// 查询
		if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
		course.setStart((pageCurrent-1)* pageSize);
		course.setEnd(pageSize);
		logger.info("start: " +course.getStart()+" end "+course.getEnd());
		courseList=courseMapper.list(course);
		logger.info(" courseList :"+courseList.toArray());
		for(Course c: courseList ) {
			logger.info("course adddate: "+c.getAddDate());
			c.setUpdateStr(DateUtil.getDateStrYmdHms(c.getAddDate()));
		}
		// 将课程列表加入
		model.addAttribute("courseList",courseList);
		String pageHTML=PageUtil.getPageContent("courseManage_{pageCurrent}_{pageSize}_{pageCount}?title="+course.getTitle(), pageCurrent, pageSize, pageCount);
		model.addAttribute("pageHTML", pageHTML);
		model.addAttribute("course", course);
		logger.info("model : "+model.toString());
		
		logger.info(" rows :"+rows);
		// 返回页面
		return "course/courseManage";
		
	}
	/**
	 * 导出 课程列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping("/user/course/download1")
    public void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("id", "课程id");
        fieldMap.put("title", "课程标题");
        fieldMap.put("url", "地址");
        fieldMap.put("author", "作者");
        fieldMap.put("updateStr", "创建时间");
        String sheetName = "课程管理报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=ItemManage.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
            ExcelUtil.listToExcel(courseList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * <a> 标签指定到新增修改页面
     * @param model
     * @param course
     * @return
     */
    @GetMapping("user/courseEdit")
    public String courseEditGet(Model model,Course course) {
    	logger.info("\n\t user/courseEdit: >>{ "+course.getId()+"}");
		if(course.getId()!=null) {
			Course course1=courseMapper.selectByPrimaryKey(course.getId());
			model.addAttribute("course", course1);
		}else {
			model.addAttribute("course", course);
		}
    	return "course/courseEdit";
    	
    }
    
    
    /**
     * 
     * @param model
     * @param request
     * @param course
     * @param repose
     * @return
     */   
    @PostMapping("/user/courseEdit")
    public String  courseEditPost(Model model,HttpServletRequest  request,Course course,HttpServletResponse repose) {
    	logger.info(" @PostMapping(\"/user/courseEdit\") 新增修改 >> \n\t id:"+course.getId());
    	String id=UUID.randomUUID().toString().toUpperCase();
    	if(course.getId()==null) {
    		course.setId(id);
    		course.setAddDate(new Date());
    		courseMapper.insert(course);
    	}else{
    		logger.info("/user/courseEdit 修改 >> \n\t id:"+course.getId());
    		course.setAddDate(new Date());
    		courseMapper.updateByPrimaryKey(course);
    	}
    	return "redirect:courseManage_0_0_0";
    	
    }
    
	

}
