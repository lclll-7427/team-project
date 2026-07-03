package com.cvs.controller;

import com.cvs.dto.ApiResponse;
import com.cvs.dto.StatisticsVO;
import com.cvs.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * 课程掌握度概览
     */
    @GetMapping("/courses/{courseId}/overview")
    public ApiResponse<StatisticsVO.CourseOverview> getCourseOverview(
            @PathVariable Long courseId) {
        try {
            StatisticsVO.CourseOverview overview = statisticsService.getCourseOverview(courseId);
            return ApiResponse.success(overview);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 各知识点掌握度统计
     */
    @GetMapping("/courses/{courseId}/knowledge-points")
    public ApiResponse<List<StatisticsVO.KnowledgePointMastery>> getKnowledgePointMastery(
            @PathVariable Long courseId) {
        try {
            List<StatisticsVO.KnowledgePointMastery> data =
                    statisticsService.getKnowledgePointMastery(courseId);
            return ApiResponse.success(data);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 各学生掌握度统计
     */
    @GetMapping("/courses/{courseId}/students")
    public ApiResponse<List<StatisticsVO.StudentMastery>> getStudentMastery(
            @PathVariable Long courseId) {
        try {
            List<StatisticsVO.StudentMastery> data =
                    statisticsService.getStudentMastery(courseId);
            return ApiResponse.success(data);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
