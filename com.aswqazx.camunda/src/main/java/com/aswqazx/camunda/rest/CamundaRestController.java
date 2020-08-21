package com.aswqazx.camunda.rest;

import com.aswqazx.camunda.entity.ResultInfo;
import com.aswqazx.camunda.entity.param.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.repository.*;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.camunda.bpm.engine.task.Task;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * camunda
 * @author OMNIS
 */
@RestController
@RequestMapping(value = "/lc")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CamundaRestController {

    public static final String FORM_DATA_KEY = "formDataKey";

    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final IdentityService identityService;
    private final FormService formService;


    /**
     * 发布流程
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/fblc")
    @CrossOrigin
    public ResultInfo fblc(@RequestBody FblcParam param) {
        log.info("fblc {}", param);
        String fileName = UUID.randomUUID().toString() + ".bpmn";
        try {
            InputStream is = new ByteArrayInputStream(param.getBpmnXml().getBytes(StandardCharsets.UTF_8));
            BpmnModelInstance modelInstance = Bpmn.readModelFromStream(is);
            Collection<UserTask> userTaskList = modelInstance.getModelElementsByType(UserTask.class);
            for (UserTask userTask : userTaskList) {
                // 判断camundaCandidateGroups是否存在，不存在设置表达式
                if (StringUtils.isEmpty(userTask.getCamundaCandidateGroups())) {
                    userTask.setCamundaCandidateGroups("${dept}");
                }
            }
            log.info(modelInstance);
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
            Deployment deploy = deploymentBuilder
                    .name(fileName)
                    .source("process application")
                    //.addString(fileName, param.getBpmnXml())
                    .addModelInstance(fileName, modelInstance)
                    .deploy();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultInfo.success("发布流程成功");
    }

    /**
     * 流程部署列表
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lcbslb")
    @CrossOrigin
    public ResultInfo lcbslb(@RequestBody LcbslbParam param) {
        log.info("lcbslb {}", param);
        // 查询多个部署对象
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> deploymentList = deploymentQuery.listPage((param.getPage() - 1) * param.getSize(),param.getSize());
        log.info(deploymentList);
        return ResultInfo.success("查询流程部署列表成功");
    }

    /**
     * 流程实例列表
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lcsllb")
    @CrossOrigin
    public ResultInfo lcsllb(@RequestBody LcsllbParam param) {
        log.info("lcsllb {}", param);
        // 查询多个流程实例
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.latestVersion();
        if (!StringUtils.isEmpty(param.getLcmc())) {
            processDefinitionQuery.processDefinitionNameLike("%" + param.getLcmc() + "%");
        }
        if (!StringUtils.isEmpty(param.getSfjh())) {
            processDefinitionQuery.active();
        }
        long count;
        List<ProcessDefinition> processDefinitionList;
        if (param.getPage() == 0 && param.getLimit() == 0) {
            count = processDefinitionQuery.count();
            processDefinitionList = processDefinitionQuery.list();
        } else {
            count = processDefinitionQuery.count();
            processDefinitionList = processDefinitionQuery.listPage((param.getPage() - 1) * param.getLimit(), param.getLimit());
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        for(ProcessDefinition processDefinition: processDefinitionList){
            Map<String, Object> map = new HashMap<>(16);
            map.put("name", processDefinition.getName());
            map.put("version", processDefinition.getVersion());
            map.put("key", processDefinition.getKey());
            map.put("resourcename", processDefinition.getResourceName());
            map.put("id", processDefinition.getId());
            map.put("status", !processDefinition.isSuspended());
            map.put("deploymentId", processDefinition.getDeploymentId());
            map.put("versionTag", processDefinition.getVersionTag());
            String formKey = formService.getStartFormKey(processDefinition.getId());
            if (!StringUtils.isEmpty(formKey)) {
                if (formKey.contains("(") && formKey.contains(")")) {
                    String forKeyTemp = formKey.substring(formKey.indexOf("(")+ 1, formKey.indexOf(")"));
                    map.put("formKey", forKeyTemp);
                }
            }
            resultList.add(map);
        }
        return ResultInfo.success("查询流程实例列表成功", resultList, count);
    }

    /**
     * 流程开关
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lckg")
    @CrossOrigin
    public ResultInfo lckg(@RequestBody LcslkgParam param) {
        log.info("lckg {}", param);
        if (param.isSfkq()) {
            repositoryService.activateProcessDefinitionById(param.getProcessDefinitionId());
        } else {
            repositoryService.suspendProcessDefinitionById(param.getProcessDefinitionId());
        }
        return ResultInfo.success("流程开关成功");
    }

    /**
     * 流程删除
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lcslsc")
    @CrossOrigin
    public ResultInfo lcslsc(@RequestBody LcslscParam param) {
        log.info("lcslsc {}", param);
        if (param.isSfqbsc()) {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                    .processDefinitionKey(param.getKey())
                    .list();
            if (processDefinitionList != null && processDefinitionList.size() > 0) {
                for(ProcessDefinition processDefinition : processDefinitionList) {
                    repositoryService.deleteDeployment(processDefinition.getDeploymentId(), param.isCascade());
                }
            }
        } else {
            repositoryService.deleteDeployment(param.getDeploymentId(), param.isCascade());
        }
        return ResultInfo.success("流程删除成功");
    }

    /**
     * 流程图
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lct")
    @CrossOrigin
    public ResultInfo lct(@RequestBody LctParam param) {
        log.info("lct {}", param);
        String str = "";
        try {
            InputStream inputStream = repositoryService.getProcessModel(param.getProcessDefinitionId());
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            str = result.toString(StandardCharsets.UTF_8.name());
            log.info(str);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultInfo.success("获取流程图成功", str);
    }

    /**
     * 流程开始
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lcks")
    @CrossOrigin
    public ResultInfo lcks(@RequestBody LcksParam param) {
        log.info("lcks {}", param);
        String id = UUID.randomUUID().toString();
        log.info(id);
        Map<String, Object> variables = new HashMap<>();
        if (!StringUtils.isEmpty(param.getFormDataKey())) {
            variables.put("formDataKey", param.getFormDataKey());
        }
        // camundaCandidateGroups是表达式的话 添加发起人的单位
        variables.put("dept", param.getFqrdw());
        //添加发起人
        identityService.setAuthenticatedUserId(param.getFqr());
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(param.getProjectId(), variables);
        if (instance != null) {
            Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).active().singleResult();
            if (!StringUtils.isEmpty(param.getShr())) {
                taskService.setAssignee(task.getId(), param.getShr());
            }
            if (!StringUtils.isEmpty(param.getYxj())) {
                taskService.setPriority(task.getId(), param.getYxj());
            }
            return ResultInfo.success("开始流程成功");
        }else {
            return ResultInfo.failure("开始流程失败");
        }
    }

    /**
     * 我的申请
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/wdsq")
    @CrossOrigin
    public ResultInfo wdsq(@RequestBody WdsqParam param) {
        log.info("wdsq {}", param);
        List<Map<String, Object>> resultList = new ArrayList<>();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        TaskQuery taskQuery = taskService.createTaskQuery();
        historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        if (!StringUtils.isEmpty(param.getSqr())) {
            historicProcessInstanceQuery.startedBy(param.getSqr());
        }
        if (!StringUtils.isEmpty(param.getLcmc())) {
            historicProcessInstanceQuery.processDefinitionNameLike("%" + param.getLcmc() + "%");
        }
        if (!StringUtils.isEmpty(param.getStartTime()) && !StringUtils.isEmpty(param.getEndTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date starttime = sdf.parse(param.getStartTime() + " 00:00:00");
                Date endtime = sdf.parse(param.getEndTime() + " 23:59:59");
                historicProcessInstanceQuery.startedAfter(starttime).startedBefore(endtime);
            }catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        long count = historicProcessInstanceQuery.count();
        List<HistoricProcessInstance> list = historicProcessInstanceQuery.listPage((param.getPage() - 1) * param.getLimit(), param.getLimit());
        for (HistoricProcessInstance historicProcessInstance : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("processDefinitionKey", historicProcessInstance.getProcessDefinitionKey());
            map.put("processDefinitionName", historicProcessInstance.getProcessDefinitionName());
            map.put("processInstanceId", historicProcessInstance.getRootProcessInstanceId());
            map.put("startUserId", historicProcessInstance.getStartUserId());
            map.put("state", historicProcessInstance.getState());
            map.put("startTime", historicProcessInstance.getStartTime());
            map.put("endTime", historicProcessInstance.getEndTime());
            map.put("deleteReason", historicProcessInstance.getDeleteReason());
            List<Task> taskList = taskQuery
                    .processInstanceId(historicProcessInstance.getRootProcessInstanceId())
                    .list();
            if (taskList.size() > 0) {
                List<String> nameList = new ArrayList<>();
                List<String> idList = new ArrayList<>();
                List<String> definitionKeyList = new ArrayList<>();
                List<String> formDataKeyList = new ArrayList<>();
                List<Integer> priorityList = new ArrayList<>();
                for (Task task :taskList) {

                    Map<String,Object> vars = taskService.getVariables(task.getId());
                    if (vars.size() > 0) {
                        if (!StringUtils.isEmpty(vars.get("formDataKey"))) {
                            formDataKeyList.add(vars.get("formDataKey").toString());
                        }
                    }


                    nameList.add(task.getName());
                    idList.add(task.getId());
                    definitionKeyList.add(task.getTaskDefinitionKey());
                    priorityList.add(task.getPriority());
                }
                map.put("taskName", nameList);
                map.put("taskId", idList);
                map.put("taskDefinitionKey", definitionKeyList);
                map.put("formDataKey", formDataKeyList);
                map.put("priority", priorityList);
            } else {
                List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery()
                        .processInstanceId(historicProcessInstance.getRootProcessInstanceId())
                        .list();
                List<String> formDataKeyList = new ArrayList<>();
                for (HistoricVariableInstance h : historicVariableInstanceList) {
                    if ("formDataKey".equals(h.getName())) {
                        formDataKeyList.add(h.getValue().toString());
                    }
                }
                map.put("formDataKey", formDataKeyList);
            }
            resultList.add(map);
        }
        return ResultInfo.success("查询我的申请成功", resultList, count);
    }

    /**
     * 获取当前节点
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/hqdqjd")
    @CrossOrigin
    public ResultInfo hqdqjd(@RequestBody HqdqjdParam param) {
        log.info("hqdqjd {}", param);
        List<String> result = new ArrayList<>();
        TaskQuery taskQuery =taskService.createTaskQuery();
        List<Task> taskList = taskQuery
                .processInstanceId(param.getProcessInstanceId())
                .list();
        if (taskList.size() > 0) {
            for (Task task : taskList) {
                result.add(task.getTaskDefinitionKey());
            }
        }
        return ResultInfo.success("获取当前节点成功", result);
    }

    /**
     * 获取节点
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/hqjd")
    @CrossOrigin
    public ResultInfo hqjd(@RequestBody HqjdParam param) {
        log.info("hqjd {}", param);
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        if (param.isSfwc()) {
            historicActivityInstanceQuery.finished();
        }
        if (!StringUtils.isEmpty(param.getActivityType())) {
            historicActivityInstanceQuery.activityType(param.getActivityType());
        }
        List<HistoricActivityInstance> hisActInstList = historicActivityInstanceQuery
                .processInstanceId(param.getProcessInstanceId())
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (HistoricActivityInstance historicActivityInstance : hisActInstList) {
            Map<String, Object> map = new HashMap<>();
            map.put("activityId", historicActivityInstance.getActivityId());
            map.put("activityName", historicActivityInstance.getActivityName());
            map.put("parentActivityInstanceId", historicActivityInstance.getParentActivityInstanceId());
            map.put("taskId", historicActivityInstance.getTaskId());
            map.put("assignee", historicActivityInstance.getAssignee());
            map.put("rootProcessInstanceId", historicActivityInstance.getRootProcessInstanceId());
            map.put("processInstanceId", historicActivityInstance.getProcessInstanceId());
            map.put("processDefinitionId", historicActivityInstance.getProcessDefinitionId());
            map.put("processDefinitionKey", historicActivityInstance.getProcessDefinitionKey());
            map.put("startTime", historicActivityInstance.getStartTime());
            map.put("endTime", historicActivityInstance.getEndTime());
            map.put("id", historicActivityInstance.getId());
            List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicActivityInstance.getProcessInstanceId())
                    .list();
            for (HistoricVariableInstance h : historicVariableInstanceList) {
                if ("sh".equals(h.getName())) {
                    map.put("sh", h.getValue().toString());
                }
            }
            list.add(map);
        }
        return ResultInfo.success("获取节点成功", list);
    }

    /**
     * 我的代办
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/wddb")
    @CrossOrigin
    public ResultInfo wddb(@RequestBody WddbParam param) {
        log.info("wddb {}", param);
        List<Map<String, Object>> resultList = new ArrayList<>();
        TaskQuery taskQuery = taskService.createTaskQuery();
        TaskQuery taskQuery2 = taskQuery.or();
        taskQuery.orderByTaskCreateTime().desc();
        if (!StringUtils.isEmpty(param.getDbr())||!StringUtils.isEmpty(param.getUserDeptId())||!StringUtils.isEmpty(param.getUserType())) {

            //办理人
            if(!StringUtils.isEmpty(param.getDbr())){
                taskQuery2.taskAssignee(param.getDbr());
            }
            //部门
            if(!StringUtils.isEmpty(param.getUserDeptId())){
                taskQuery2.taskCandidateGroup(param.getUserDeptId());
            }
            //职务
            if(!StringUtils.isEmpty(param.getUserType())){
                taskQuery2.taskCandidateUser(param.getUserType());
            }


        }
        if (!StringUtils.isEmpty(param.getDbmc())) {
            taskQuery.taskNameLike("%" + param.getDbmc() + "%");

        }
        if (!StringUtils.isEmpty(param.getLcmc())) {
            taskQuery.processDefinitionNameLike("%" + param.getLcmc() + "%");

        }
        if (!StringUtils.isEmpty(param.getStartTime()) && !StringUtils.isEmpty(param.getEndTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startTime = sdf.parse(param.getStartTime() + " 00:00:00");
                Date endTime = sdf.parse(param.getEndTime() + " 23:59:59");
                taskQuery.taskCreatedAfter(startTime).taskCreatedBefore(endTime);

            }catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        long count = taskQuery.count();
        List<Task> list = taskQuery.listPage((param.getPage() - 1) * param.getSize(),param.getSize());
        log.info(list);
        for (Task task : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("createTime", task.getCreateTime());
            map.put("priority", task.getPriority());
            TaskFormData formData = formService.getTaskFormData(task.getId());
            String formKey = formData.getFormKey();
            if (!StringUtils.isEmpty(formKey)) {
                if (formKey.contains("(") && formKey.contains(")")) {
                    String forKeyTemp = formKey.substring(formKey.indexOf("(")+ 1, formKey.indexOf(")"));
                    map.put("formKey", forKeyTemp);
                }
            }
            Map<String,Object> vars = taskService.getVariables(task.getId());
            if (vars.size() > 0) {
                if (!StringUtils.isEmpty(vars.get("formDataKey"))) {
                    map.put("formDataKey", vars.get("formDataKey"));
                }
            }
            HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
            HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            map.put("processDefinitionName", historicProcessInstance.getProcessDefinitionName());
            map.put("startUserId", historicProcessInstance.getStartUserId());
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("state", historicProcessInstance.getState());
            resultList.add(map);
        }
        return ResultInfo.success("查询我的代办成功", resultList, count);
    }

    /**
     * 审核
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/sh")
    @CrossOrigin
    public ResultInfo sh(@RequestBody ShParam param) {
        log.info("sh {}", param);
        TaskQuery taskQuery =taskService.createTaskQuery();
        Task task = taskQuery
                //.taskAssignee(param.getShr())
                .taskId(param.getTaskId())
                .singleResult();
        if (task == null) {
            return ResultInfo.failure("The task not found");
        }else {
            if (StringUtils.isEmpty(task.getAssignee())) {
                taskService.claim(task.getId(), param.getShr());
            }
            String formDataKey = "";
            Map<String,Object> vars = taskService.getVariables(task.getId());
            if (vars.size() > 0) {
                if (!StringUtils.isEmpty(vars.get(FORM_DATA_KEY))) {
                    if (!StringUtils.isEmpty(param.getFormDataKey())) {
                        formDataKey = vars.get(FORM_DATA_KEY).toString() + "," + param.getFormDataKey();
                    } else {
                        formDataKey = vars.get(FORM_DATA_KEY).toString();
                    }
                }
            }
            Map<String, Object> variables = new HashMap<>();
            variables.put("sh",  param.getSh());
            variables.put("formDataKey",  formDataKey);
            taskService.complete(task.getId(), variables);
            return ResultInfo.success("审核成功");
        }
    }

    /**
     * 我的已办
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/wdyb")
    @CrossOrigin
    public ResultInfo wddb(@RequestBody WdybParam param) {
        log.info("wdyb {}", param);
        List<Map<String, Object>> resultList = new ArrayList<>();
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
        if (!StringUtils.isEmpty(param.getYbr())) {
            historicTaskInstanceQuery.taskAssignee(param.getYbr());
        }
        if (!StringUtils.isEmpty(param.getDbmc())) {
            historicTaskInstanceQuery.taskNameLike("%" + param.getDbmc() + "%");
        }
        if (!StringUtils.isEmpty(param.getLcmc())) {
            historicTaskInstanceQuery.processDefinitionName(param.getLcmc());
        }
        if (!StringUtils.isEmpty(param.getStartTime()) && !StringUtils.isEmpty(param.getEndTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startTime = sdf.parse(param.getStartTime() + " 00:00:00");
                Date endTime = sdf.parse(param.getEndTime() + " 23:59:59");
                historicTaskInstanceQuery.startedAfter(startTime).startedBefore(endTime);
            }catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        long count = historicTaskInstanceQuery.finished().count();
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.finished().listPage((param.getPage() - 1) * param.getSize(),param.getSize());
        log.info(list);
        for (HistoricTaskInstance historicTaskInstance : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", historicTaskInstance.getId());
            map.put("taskName", historicTaskInstance.getName());
            map.put("taskDefinitionKey", historicTaskInstance.getTaskDefinitionKey());
            map.put("processInstanceId", historicTaskInstance.getProcessInstanceId());
            map.put("createTime", historicTaskInstance.getStartTime());
            map.put("endTime", historicTaskInstance.getEndTime());
            map.put("priority", historicTaskInstance.getPriority());
            HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
            HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery
                    .processInstanceId(historicTaskInstance.getProcessInstanceId())
                    .singleResult();
            map.put("processDefinitionName", historicProcessInstance.getProcessDefinitionName());
            map.put("startUserId", historicProcessInstance.getStartUserId());
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("state", historicProcessInstance.getState());
            List<String> formDataKeyList = new ArrayList<>();

            List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicTaskInstance.getProcessInstanceId())
                    .list();
            for (HistoricVariableInstance h : historicVariableInstanceList) {
                if ("formDataKey".equals(h.getName())) {
                    formDataKeyList.add(h.getValue().toString());
                }
            }
            map.put("formDataKey", formDataKeyList);
            resultList.add(map);
        }
        return ResultInfo.success("查询我的已办成功", resultList, count);
    }

    /**
     * 任务分配给人员
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/rwfpry")
    @CrossOrigin
    public ResultInfo rwfpry(@RequestBody RwfpryParam param) {
        log.info("rwfpry {}", param);
        taskService.setAssignee(param.getTaskId(), param.getUserId());
        return ResultInfo.success("任务改派成功");
    }

    /**
     * 流程撤回
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lcch")
    @CrossOrigin
    public ResultInfo lcch(@RequestBody LcchParam param) {
        log.info("lcch {}", param);
        ActivityInstance tree = runtimeService.getActivityInstance(param.getProcessInstanceId());
        runtimeService.createProcessInstanceModification(param.getProcessInstanceId())
                .cancelActivityInstance(getInstanceIdForActivity(tree, tree.getActivityId()))
                .startBeforeActivity(param.getTaskDefinitionKey())
                .execute();
        return ResultInfo.success("流程撤回成功");
    }

    /**
     * 流程终止
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lczz")
    @CrossOrigin
    public ResultInfo lczz(@RequestBody LczzParam param) {
        log.info("lczz {}", param);
        runtimeService.deleteProcessInstance(param.getProcessInstanceId(), param.getZzyy());
        return ResultInfo.success("流程终止成功");
    }

    /**
     * 流程重启 有问题
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lccq")
    @CrossOrigin
    public ResultInfo lccq(@RequestBody LccqParam param) {
        log.info("lccq {}", param);
        runtimeService.restartProcessInstances(param.getProcessDefinitionId())
                .startAfterActivity(param.getTaskDefinitionKey())
                .processInstanceIds(param.getProcessInstanceId())
                .execute();
        return ResultInfo.success("流程重启成功");
    }

    /**
     * 流程驳回
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lcbh")
    @CrossOrigin
    public ResultInfo lcbh(@RequestBody LcbhParam param) {
        log.info("lcbh {}", param);
        ActivityInstance tree = runtimeService.getActivityInstance(param.getProcessInstanceId());
        String actId = "";
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        historicActivityInstanceQuery.processInstanceId(param.getProcessInstanceId())
                .activityType("userTask")
                .finished()
                .orderByHistoricActivityInstanceEndTime();
        switch (param.getRejectType()) {
            case "REJECT_TO_START":
                List<HistoricActivityInstance> list1 = historicActivityInstanceQuery.asc().list();
                if (list1.size() > 0) {
                    actId = list1.get(0).getActivityId();
                }
                break;
            case "REJECT_TO_LAST":
                List<HistoricActivityInstance> list2 = historicActivityInstanceQuery.desc().list();
                if (list2.size() > 0) {
                    actId = list2.get(0).getActivityId();
                }
            case "REJECT_TO_TARGET":
                actId = param.getTaskDefinitionKey();
                break;
            default:
                break;
        }
        if (!StringUtils.isEmpty(actId)) {
            runtimeService
                    .createProcessInstanceModification(param.getProcessInstanceId())
                    .cancelActivityInstance(getInstanceIdForActivity(tree, param.getTaskDefinitionKey()))
                    .startBeforeActivity(actId)
                    .execute();
        }
        runtimeService.deleteProcessInstance(param.getProcessInstanceId(), param.getBhyy());
        return ResultInfo.success("流程驳回成功");
    }

    /**
     * 流程跳转
     * @param param 参数
     * @return ResultInfo
     */
    @PostMapping("/lctz")
    @CrossOrigin
    public ResultInfo lctz(@RequestBody LctzParam param) {
        log.info("lctz {}", param);
        ActivityInstance tree = runtimeService.getActivityInstance(param.getProcessInstanceId());
        switch (param.getJumpType()) {
            case "JUMP_BACK":
                runtimeService
                        .createProcessInstanceModification(param.getProcessInstanceId())
                        .cancelActivityInstance(getInstanceIdForActivity(tree, param.getTaskDefinitionKey()))
                        .startBeforeActivity(param.getToActId())
                        .execute();
                break;
            case "JUMP_FORWARD":
                taskService.complete(param.getTaskId(), null);
                ActivityInstance tree2 = runtimeService.getActivityInstance(param.getProcessInstanceId());
                runtimeService
                        .createProcessInstanceModification(param.getProcessInstanceId())
                        .cancelActivityInstance(getInstanceIdForActivity(tree2, tree2.getActivityId()))
                        .startBeforeActivity(param.getToActId())
                        .execute();
                break;
            default:
                break;
        }
        return ResultInfo.success("流程跳转成功");
    }

    private String getInstanceIdForActivity(ActivityInstance activityInstance, String activityId) {
        ActivityInstance instance = getChildInstanceForActivity(activityInstance, activityId);
        if (instance != null) {
            return instance.getId();
        }
        return null;
    }

    private ActivityInstance getChildInstanceForActivity(ActivityInstance activityInstance, String activityId) {
        if (activityId.equals(activityInstance.getActivityId())) {
            return activityInstance;
        }
        for (ActivityInstance childInstance : activityInstance.getChildActivityInstances()) {
            ActivityInstance instance = getChildInstanceForActivity(childInstance, activityId);
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

}
