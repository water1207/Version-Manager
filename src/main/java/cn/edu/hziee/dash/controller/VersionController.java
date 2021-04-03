package cn.edu.hziee.dash.controller;

import cn.edu.hziee.dash.entity.Doc;
import cn.edu.hziee.dash.entity.Repos;
import cn.edu.hziee.dash.entity.Version;
import cn.edu.hziee.dash.service.DashService;
import cn.edu.hziee.dash.service.VersionService;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Controller
public class VersionController {
    @Autowired
    private VersionService versionService;
    @Autowired
    private DashService dashService;
/*前端跳转*/
    @RequestMapping("/login")
    public String toLogin() {
        return "views/UserLogin";
    }
    @RequestMapping("/SignUp")
    public String toSignUp() {
        return "views/UserSignUp";
    }
    @RequestMapping("/retrieve")
    public String toRetrieve() {
        return "views/UserRetrieve";
    }
//    @RequestMapping("/repos_list")
//    public String toProjectSelection() {
//        return "views/repos";
//    }
//    @RequestMapping("/docs_list")
//    public String toFolderSelection() {
//        return "views/docs";
//    }
    @RequestMapping("/homepage")
    public String toHomepage() {
        return "views/edit";
    }


    @RequestMapping("/edit")
    public String toEdit(@Param("did") Integer did, Model model) {
        model.addAttribute("did", did);
        //return "editor/edit";
        List<Version> vs = versionService.findVersionListByDoc(did);
        Version version = dashService.getLatestVersion(did);

        model.addAttribute("version", version);
        model.addAttribute("vs", vs);
        return "views/edit";
    }

    @RequestMapping("/edit1")
    public String toEdit1(@Param("did") Integer did, Model model) {
        model.addAttribute("did", did);
        //return "editor/edit";
        List<Version> vs = new ArrayList<>();
        Version version = new Version();

        model.addAttribute("version", version);
        model.addAttribute("vs", vs);
        return "views/edit";
    }

    @RequestMapping("/add")
    public String addVersionInfo(@Param("did") Integer did, @Param("title") String title, @Param("author") String author, @Param("content") String content, Model model) {
        Version version = new Version();
        version.setTitle(title);
        version.setAuthor(author);
        version.setContent(content);

        Date ctime = new Date(new java.util.Date().getTime());
        version.setTime(ctime);

        versionService.addVersionInfo(version);
        versionService.linkDoc(did, version.getVid());

        System.out.println("新建版本号vid为： "+version.getVid());
        //return "redirect:/show?vid=" + version.getVid();
        return "redirect:/show?did=" + did + "&vid=" + version.getVid();
    }


    @RequestMapping("/show")
    public String show(@Param("did") Integer did,@Param("vid") Integer vid, Model model) {
        Version version = versionService.findVersionById(vid);
        Doc doc = dashService.findDocById(did);
        Repos repos = dashService.getReposByDid(did);
        List<Version> vs = versionService.findVersionListByDoc(doc.getDid());

        model.addAttribute("vs", vs);
        model.addAttribute("version", version);
        model.addAttribute("doc", doc);
        model.addAttribute("repos", repos);
        //return "editor/show";
        return "views/show";
    }

    @RequestMapping("/compare2")
    public String compare(@Param("vid") Integer vid, Model model) {
        Version v1 = versionService.findVersionById(vid);
        Version v2 = versionService.findVersionById(vid - 1);
        model.addAttribute("v1", v1);
        model.addAttribute("v2", v2);
        return "views/compare";
    }




    //md图片上传问题
    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException, IOException {
        //上传路径保存设置

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/upload/";

        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"月";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdirs();
        }

        //上传文件地址
        System.out.println("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "pg-"+ UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        File newfile = new File(realPath, filename);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newfile);

        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url","/upload/"+month+"/"+ filename);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;
    }


    @RequestMapping("export_md")
    public void exportLog(@Param("vid") Integer vid, HttpServletResponse response){
        //获取md内容
        Version version = versionService.findVersionById(vid);
        //拼接字符串
        StringBuffer text = new StringBuffer();
        text.append(version.getContent());
        String filename = version.getVid() + "_" + version.getTitle() + "_" + version.getAuthor();

        exportTxt(response, filename, text.toString());
    }


    /* 导出txt文件
     * @author
     * @param    response
     * @param    text 导出的字符串
     * @return
     */
    public void exportTxt(HttpServletResponse response, String filename, String text){
        response.setCharacterEncoding("utf-8");

 //设置响应的内容类型
        response.setContentType("text/plain");
 //设置文件的名称和格式
        response.addHeader("Content-Disposition", "attachment;filename="
                + genAttachmentFileName(filename, "JSON_FOR_UCC_")//设置名称格式，没有这个中文名称无法显示
                + ".md");
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(text.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
         //LOGGER.error("导出文件文件出错:{}",e);
        } finally {
            try {
            buff.close();
            outStr.close();
            } catch (Exception e) {
         //LOGGER.error("关闭流对象出错 e:{}",e);
            }
        }
    }


    //防止中文文件名显示出错
    public String genAttachmentFileName(String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }
}
