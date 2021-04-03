package cn.edu.hziee.dash.service;

import cn.edu.hziee.dash.entity.Version;
import cn.edu.hziee.dash.mapper.VersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Service
public class VersionService {
    @Autowired
    private VersionMapper versionMapper;

    public Version findVersionById(Integer vid) {
        return versionMapper.findById(vid);
    }

    public void addVersionInfo(Version version) {
        versionMapper.addVersion(version);
    }

    public void linkDoc(Integer did, Integer vid) {
        versionMapper.linkDoc(did, vid);
    }

    public List<Version> findVersionListByDoc(Integer did) {
        return versionMapper.findAllByDoc(did);
    }
}
