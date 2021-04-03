package cn.edu.hziee.dash.service;

import cn.edu.hziee.dash.entity.Doc;
import cn.edu.hziee.dash.entity.Repos;
import cn.edu.hziee.dash.entity.Version;
import cn.edu.hziee.dash.mapper.DocMapper;
import cn.edu.hziee.dash.mapper.ReposMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class DashService {
    @Autowired
    private DocMapper docMapper;
    @Autowired
    private ReposMapper reposMapper;

//文档相关操作
    //创建新文档，并链接到仓库
    public void createDoc(Integer rid, Doc doc) {
        docMapper.createDoc(doc);
        Integer did = doc.getDid();
        docMapper.linkRepos(rid, did);
        System.out.println("创建文档成功");
    }

    //删除文档
    public void delDoc(Integer did) {
        docMapper.delDoc(did);
        System.out.println("删除文档成功");
    }

    //查找单个文档
    public Doc findDocById(Integer did) {
        return docMapper.findById(did);
    }

    //查找某仓库下所有文档
    public List<Doc> findAllDocs(Integer rid) {
        return docMapper.findAllByRepos(rid);
    }

    //更新 文档最新修改的时间
    public void editDocTime(Integer did, Date date) {
        docMapper.editTime(did, date);
    }

    //得到 该文档最新版本
    public Version getLatestVersion(Integer did) {
        return docMapper.getLatestVersion(did);
    }

    //仓库相关操作
    public List<Repos> findAllRepos(Integer uid) {
        return reposMapper.findAllByUser(uid);
    }

    public Repos findReposById(Integer rid) {
        return reposMapper.findById(rid);
    }

    public void addRepos(Repos repos) {
        reposMapper.addRepos(repos);
    }

    public void delRepos(Integer rid) {
        reposMapper.delRepos(rid);
    }

    public void editReposTime(Integer did, Date date) {
        reposMapper.editTime(did, date);
    }

    public Repos getReposByDid(Integer did) {
        Integer rid = docMapper.getRidByDid(did);
        return reposMapper.findById(rid);
    }

}
