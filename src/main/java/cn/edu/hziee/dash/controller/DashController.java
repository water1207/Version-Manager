package cn.edu.hziee.dash.controller;

import cn.edu.hziee.dash.entity.Doc;
import cn.edu.hziee.dash.entity.Repos;
import cn.edu.hziee.dash.entity.Version;
import cn.edu.hziee.dash.service.DashService;
import cn.edu.hziee.dash.service.VersionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class DashController {
    @Autowired
    private DashService dashService;
    @Autowired
    private VersionService versionService;

    @RequestMapping("/doc")    /*到展示界面 将文件的最新版本展示为html */
    public String findDocById(@Param("did") Integer did, Model model) {
        Doc doc = dashService.findDocById(did);
        Version version = new Version();
        version = dashService.getLatestVersion(did);


//        model.addAttribute("version", version);
//        model.addAttribute("doc", doc);
        //return "redirect:/show?vid=" + version.getVid();
        return "redirect:/show?did=" + doc.getDid() + "&vid=" + version.getVid();
    }

    @RequestMapping("/doc_list")
    public String findAllDocs(@Param("rid") Integer rid, Model model) {
        List<Doc> docs = dashService.findAllDocs(rid);
        Repos repos = dashService.findReposById(rid);

        model.addAttribute("docs", docs);
        model.addAttribute("repos", repos);
        return "views/docs";
    }

    @RequestMapping("/create_doc")
    public String createDoc(@Param("rid") Integer rid, @Param("name") String name, Model model) {
        Date time = new Date(new java.util.Date().getTime());
        Doc doc = new Doc();
        doc.setName(name);
        doc.setTime(time);
        dashService.createDoc(rid, doc);

        return "redirect:/edit1?did=" + doc.getDid();
    }

    @RequestMapping("/repos_list")
    public String findAllRepos(@Param("uid") Integer uid, Model model) {
        List<Repos> reposList = dashService.findAllRepos(uid);
        model.addAttribute("reposList", reposList);

        return "views/repos";
    }

    @RequestMapping("/add_repos")
    public String addRepos(@Param("uid") Integer uid, @Param("name") String name) {
        Date time = new Date(new java.util.Date().getTime());
        Repos repos = new Repos();
        repos.setName(name);
        repos.setTime(time);
        repos.setUid(uid);
        dashService.addRepos(repos);
        System.out.println("添加仓库成功");

        return "redirect:/repos_list?uid=" + uid;
    }

    @RequestMapping("/del_repos")
    public String delRepos(@Param("rid") Integer rid, Model model) {
        dashService.delRepos(rid);
        System.out.println("删除仓库成功");
        return "redirect:/repos_list?uid=1";
    }

    @RequestMapping("/time")
    public void time() {
        java.util.Date date = new java.util.Date();
        date.getTime();
        Date time = new Date(date.getTime());
        System.out.println(time);
    }

    @RequestMapping("/compare")
    public String compare(@Param("did") Integer did, @Param("vid") Integer vid, Model model) {
//        Version v1 = versionService.findVersionById(vid);
//        Version v2 = versionService.findVersionById(vid - 1);
//        model.addAttribute("v1", v1);
//        model.addAttribute("v2", v2);
        Version v2 = dashService.getLatestVersion(did);
        Version v1 = versionService.findVersionById(vid);
        List<Version> vs = versionService.findVersionListByDoc(did);
        Doc doc = dashService.findDocById(did);

        model.addAttribute("v1", v1);
        model.addAttribute("v2", v2);
        model.addAttribute("vs", vs);
        model.addAttribute("did", did);
        model.addAttribute("doc", doc);
        return "views/compare";
    }
}
