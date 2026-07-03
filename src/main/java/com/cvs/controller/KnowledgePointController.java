package com.cvs.controller;

import com.cvs.dto.ApiResponse;
import com.cvs.dto.KnowledgePointVO;
import com.cvs.model.User;
import com.cvs.service.KnowledgePointService;
import com.cvs.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class KnowledgePointController {

    private final KnowledgePointService kpService;
    private final UserService userService;

    public KnowledgePointController(KnowledgePointService kpService, UserService userService) {
        this.kpService = kpService;
        this.userService = userService;
    }

    /**
     * 获取课程下的知识点列表
     */
    @GetMapping("/courses/{courseId}/knowledge-points")
    public ApiResponse<List<KnowledgePointVO>> listKnowledgePoints(
            @PathVariable Long courseId) {
        try {
            List<KnowledgePointVO> kps = kpService.getKnowledgePointsByCourse(courseId);
            return ApiResponse.success(kps);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 添加知识点（教师）
     */
    @PostMapping("/courses/{courseId}/knowledge-points")
    public ApiResponse<KnowledgePointVO> addKnowledgePoint(
            @PathVariable Long courseId,
            @RequestBody Map<String, String> body) {
        try {
            Long teacherId = Long.parseLong(body.get("teacherId"));
            String name = body.get("name");
            String description = body.getOrDefault("description", "");

            User teacher = userService.findById(teacherId);
            KnowledgePointVO kp = kpService.addKnowledgePoint(courseId, name, description, teacher);
            return ApiResponse.success("知识点添加成功", kp);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 删除知识点（教师）
     */
    @DeleteMapping("/knowledge-points/{kpId}")
    public ApiResponse<Void> deleteKnowledgePoint(
            @PathVariable Long kpId,
            @RequestParam Long teacherId) {
        try {
            User teacher = userService.findById(teacherId);
            kpService.deleteKnowledgePoint(kpId, teacher);
            return ApiResponse.success("知识点已删除", null);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
