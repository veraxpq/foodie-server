package foodie.controller;

import com.alibaba.fastjson.JSONArray;
import foodie.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/foodie")
@Service
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @GetMapping(value="/userInfo")
    public JSONArray getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONArray user;

        try {
            user = userService.getUserInfo();
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("This email is registered, please log in.");
        }
        return user;
    }

    @PutMapping(value = "/updateUserInfo")
    public void updateUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject user) {
        userService.updateUserInfo(user);
    }

    @PostMapping(value = "/createUser")
    public void createUser(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject obj) {
        userService.createUser(obj);
    }

    @DeleteMapping(value = "/deleteUser")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("id") int id) {
        userService.deleteUser(id);
    }
//    /**
//     * Delete the project in the databases, at the same time delete all the tasks in this project in the databases.
//     *
//     * @param request
//     * @param response
//     * @param job
//     * @throws IOException
//     */
//    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
//    @Transactional
//    public void deleteProject(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject job) throws IOException {
//        int proID = job.getInteger("proID");
//        GraphExample example = new GraphExample();
//        GraphExample.Criteria criteria = example.createCriteria();
//        criteria.andProidEqualTo(proID);
//        List<GraphWithBLOBs> graphList = graphMapper.selectByExampleWithBLOBs(example);
//        for (GraphWithBLOBs g : graphList) {
//            graphMapper.deleteByPrimaryKey(g.getTaskid());
//        }
//        projectMapper.deleteByPrimaryKey(proID);
//    }
//
//    /**
//     * Create a new task with the given task name, and save the task with the given project id into the database.
//     *
//     * @param request
//     * @param response
//     * @param job
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(value="/newTask", method = RequestMethod.POST)
//    public JSONObject NewTask(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject job) throws IOException {
//        String taskName = job.getString("taskName");
//        int proID = job.getInteger("proID");
//        GraphWithBLOBs blob = new GraphWithBLOBs();
//        blob.setProid(proID);
//        blob.setTaskname(taskName);
//
//        try {
//            graphMapper.insert(blob);
//        } catch (DuplicateKeyException e) {
//            throw new IllegalArgumentException("任务名已存在，请重新输入");
//        }
//        GraphWithBLOBs blobInfo = graphMapper.selectByPrimaryKey(blob.getTaskid());
//
//        JSONObject taskInf = (JSONObject) JSONObject.toJSON(blobInfo);
//        return taskInf;
//    }
//
//    /**
//     * Delete the task with the given task id in the database.
//     *
//     * @param request
//     * @param response
//     * @param job
//     * @throws IOException
//     */
//    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
//        public void deleteTask(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject job) throws IOException {
//        int taskID = job.getInteger("taskID");
//        graphMapper.deleteByPrimaryKey(taskID);
//    }
//
//    /**
//     * Save or update the task with the given task id into the database. the information of the task includes Node, Edge and task id.
//     *
//     * @param request
//     * @param response
//     * @param job
//     * @return
//     */
//    @RequestMapping(value="/saveTask", method = RequestMethod.POST)
//    public JSONObject saveTask(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject job){
//
//        int taskID = job.getInteger("taskID");
//        GraphWithBLOBs blob = graphMapper.selectByPrimaryKey(taskID);
//        //blob.setProid(job.getInteger("proID"));
//        //blob.setTaskid(job.getInteger("taskID"));
//        //blob.setProname(job.getString("proName"));
//        //blob.setTaskname(job.getString("taskName"));//唯一字段，不更新
//        //blob.setProdesc(job.getString("proDesc"));
//        JSONArray Node=job.getJSONArray("Node");
//        JSONArray Edge=job.getJSONArray("Edge");
//        blob.setNode(Node.toJSONString());
//        blob.setEdge(Edge.toJSONString());
//        graphMapper.updateByPrimaryKeySelective(blob);
//
////        ProList prolist=new ProList(blob.getProid(),blob.getProname(),blob.getProdesc(),blob.getTaskid(),blob.getTaskname());
//
//        JSONObject taskInf = (JSONObject) JSONObject.toJSON(blob);
//        JSONArray node=JSONArray.parseArray(blob.getNode());
//        JSONArray edge=JSONArray.parseArray(blob.getEdge());
//        taskInf.put("Node",node);
//        taskInf.put("Edge",edge);
//
//        return taskInf;
//    }
////    public void saveProcessInfo(String processName, Integer taskID) {
////        ProcessInfoDetail detail = new ProcessInfoDetail("data_source", "文件已导入");
////        LogInfos info = new LogInfos();
////        info.setLogInfo(JSONObject.toJSON(detail).toString());
////        info.setTaskId(taskID);
////        info.setNodeName();
////        processInfoMapper.insertSelective(info);
////    }
//
//    /**
//     * Create or update the process information with the given instance id, and save it into the database.
//     *
//     * @param request
//     * @param response
//     * @param job
//     * @return
//     */
//    @RequestMapping(value = "/runTask/{taskID}/processInfoUpdate", method = RequestMethod.POST)
//    public JSONObject processInfoUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject job) {
//        JSONObject processInf = new JSONObject();
//
//        Integer logId = job.getInteger("log_id");
//        if (logId == null) {
//            Integer instanceId = job.getIntValue("instance_id");
//            String nodeName = job.getString("node_name");
//            if (instanceId == null || nodeName == null) {
//                throw new IllegalStateException("实例ID或节点名为空");
//            }
//            LogInfosExample example = new LogInfosExample();
//            LogInfosExample.Criteria criteria = example.createCriteria();
//            criteria.andInstanceIdEqualTo(instanceId);
//            criteria.andNodeNameEqualTo(nodeName);
//            List<LogInfos> infos = logInfosMapper.selectByExample(example);
//            if (infos != null && infos.size() != 0) {
//                LogInfos info = infos.get(0);
//                processInf.put("log_id", info.getLogId());
//                processInf.put("log_info", info.getLogInfo());
//                if (info.getIsFinished() == 1) {
//                    processInf.put("is_finished", 1);
//                }
//            }
//        } else {
//            LogInfos info = logInfosMapper.selectByPrimaryKey(logId);
//            processInf.put("log_info", info.getLogInfo());
//            if (info.getIsFinished() == 1) {
//                processInf.put("is_finished", 1);
//            }
//        }
//
//        return processInf;
//    }
//
//    /**
//     * This request searches the is_finished flag.
//     */
//    @RequestMapping(value="/runTask/{taskID}/getStatus", method = RequestMethod.POST)
//    public JSONObject getStatus(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject job) throws IllegalStateException {
//        Integer instanceId = job.getInteger("instance_id");
//        String nodeName = job.getString("node_name");
//        JSONObject processInf = new JSONObject();
//        if (instanceId == null || nodeName == null) {
//            return processInf;
//        }
//        Integer logId = job.getInteger("log_id");
//        if (logId != null) {
//            LogInfos info = logInfosMapper.selectByPrimaryKey(logId);
//            processInf.put("is_finished", info.getIsFinished());
//        } else {
//            if (instanceId == null || nodeName == null) {
//                throw new IllegalStateException("实例ID或节点名为空");
//            }
//            LogInfosExample example = new LogInfosExample();
//            LogInfosExample.Criteria criteria = example.createCriteria();
//            criteria.andNodeNameEqualTo(nodeName);
//            criteria.andInstanceIdEqualTo(instanceId);
//            List<LogInfos> infos = logInfosMapper.selectByExample(example);
//            if (infos == null || infos.size() == 0) {
//                throw new IllegalStateException("没有此类节点信息");
//            }
//            LogInfos info = infos.get(0);
//            processInf.put("log_id", info.getLogId());
//            if (infos.get(0) != null) {
//                processInf.put("is_finished", info.getIsFinished());
//            }
//        }
//        return processInf;
//    }
//
//    /**
//     * Create a instance object and save it into the database, then run the task.
//     *
//     * @param request
//     * @param response
//     * @param taskID
//     * @return
//     */
//    @RequestMapping(value="/runTask/{taskID}", method = RequestMethod.GET)
//    public JSONObject runTask(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer taskID) {
//        //save the instance id into the database, and the the instance id.
//
//        JSONObject processInf = new JSONObject();
//
//        GraphWithBLOBs blob=graphMapper.selectByPrimaryKey(taskID);
//        if (blob.getNode() == null) {
//            throw new IllegalArgumentException("请先拖动模块至运行区并保存");
//        }
//        JSONArray Node=JSONArray.parseArray(blob.getNode());
//        JSONArray Edge=JSONArray.parseArray(blob.getEdge());
//
//        Instance instance = new Instance();
//        instance.setTaskId(taskID);
//        instanceIdMapper.insert(instance);
//        int instanceId = instance.getInstanceId();
//        processInf.put("instance_id", instanceId);
//
//        RunTaskUtil run = new RunTaskUtil(Node, Edge, instanceId, taskID, logInfosMapper, fileDir);
//        Thread runThread = new Thread(run);
//        runThread.start();
//
//        return processInf;
//    }

//    @RequestMapping(value = "/downloadModel/{taskID}", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response,
//                                               @PathVariable Integer taskID) throws Exception {
//
//        String outpath = "/home/deploy/ml_train/training_platform/instance" + taskID + ".rar";
//        File modelFile = new File(outpath);
//        ZipUtil zipUtil = new ZipUtil();
//        File targetFile;
//        try {
//            String targetDir = zipUtil.zipFile(modelFile, "rar");
//            targetFile = new File(targetDir);
//        } catch (Exception e) {
//            throw new IllegalStateException("创建压缩文件失败");
//        }
//
//        byte[] filebyte = FileUtils.readFileToByteArray(targetFile);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        ResponseEntity<byte[]> responseBody = null;
//        try {
//            responseBody = new ResponseEntity<byte[]>(filebyte, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//        return responseBody;
//    }

}
