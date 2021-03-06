package com.nb.user.controller;

import com.nb.user.activiti.StallService;
import com.nb.user.common.ErrorConstant;
import com.nb.user.request.CompleteTaskRequest;
import com.nb.user.service.StallApprovalService;
import com.nb.user.vo.ResponseVO;
import com.nb.user.vo.StallApprovalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author fly
 * @description 档口审批
 * @date 2019/4/15 16:53
 */
@RestController
@Api(value = "Approval Api", tags = "审批相关接口")
@RequestMapping("/approval")
public class StallController {

    @Autowired
    private StallApprovalService stallApprovalService;

    @Autowired
    private StallService stallService;

    @ApiOperation(value = "发起审批", notes = "发起审批")
    @PostMapping("/saveStallApproval")
    public ResponseVO saveStallApproval(String msg) {
        stallApprovalService.saveStallApproval(msg);
        return new ResponseVO(ErrorConstant.SUCCESS, ErrorConstant.SUCCESS_MSG);
    }

    @ApiOperation(value = "获取当前人审批任务", notes = "获取当前人审批任务")
    @GetMapping("/queryByUserId/{userId}")
    public ResponseVO<List<StallApprovalVO>> queryByUserId(@PathVariable String userId) {
        List<StallApprovalVO> stallApprovalList = stallApprovalService.queryByUserId(userId);
        return new ResponseVO<>(ErrorConstant.SUCCESS, ErrorConstant.SUCCESS_MSG, stallApprovalList);
    }

    @ApiOperation(value = "审批", notes = "审批")
    @PostMapping("/completeTask")
    public ResponseVO completeTask(@RequestBody @Valid CompleteTaskRequest request) {
        stallService.completeTaskByUser(request.getTaskId(), request.getUserId(), request.getAudit());
        return new ResponseVO(ErrorConstant.SUCCESS, ErrorConstant.SUCCESS_MSG);
    }

    @ApiOperation(value = "获取当前审批执行阶段", notes = "获取当前审批执行阶段")
    @GetMapping("queryProPlan/{processInstanceId}")
    public void queryProPlan(HttpServletResponse response, @PathVariable String processInstanceId) throws IOException {
        stallService.getCurrentProcessImg(response, processInstanceId);
    }
}
