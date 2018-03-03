package com.crawl.videosite.parser.bilibili;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import com.crawl.videosite.entity.VideoSitePersistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class VideoListJsonParser extends AbstractVideoListParser {

    /**
     * 视频类型id
     */
    private Long rid = 1l;
    /**
     * 页面大小
     */
    private Integer ps = 50;
    /**
     * 页码
     */
    private Integer pn = 1;

    /**
     * 分析json数据
     *
     * @param jsonObject
     */
    @Override
    public void parseJson(JSONObject jsonObject, Long rid, Long original) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        System.out.println(jsonObject);
        Map<String, Object> data = (Map<String, Object>) jsonObject.get("data");
        Map<String, Object> page = (Map<String, Object>) data.get("page");
        List<Map<String, Object>> archives = (List<Map<String, Object>>) data.get("archives");
        VideoSitePersistence persistence = new VideoSitePersistence();
        persistence.setBiliBili_pn(Integer.valueOf(page.get("num").toString()));
        persistence.setBiliBili_rid(rid);
        persistence.setBiliBili_original(original);
        List<Video> videos=new ArrayList<>();
        List<Style> styles=new ArrayList<>();
        List<VideoAuthor> videoAuthors=new ArrayList<>();
        for (Map<String, Object> archive : archives) {
            if (archive == null || archive.isEmpty())
                continue;
            parseDataToPersistence(archive);
        }
        System.out.println(jsonObject);
    }

    /**
     * 将json数据分析成为持久化数据
     *
     * @param archive
     */
    private void parseDataToPersistence(Map<String,Object> archive){
        if (archive==null||archive.isEmpty())
            return;
        Video video = new Video();
        Style type = new Style();
        VideoAuthor author = new VideoAuthor();
        video.setBiliBili_aid(Long.valueOf(archive.get("aid").toString())); //视频id
        video.setBiliBili_videos(Integer.valueOf(archive.get("videos").toString()));
        video.setBiliBili_rid(Long.valueOf(archive.get("tid").toString())); //视频类型id
        type.setBiliBili_rid(Long.valueOf(archive.get("tid").toString()));
        type.setStyleName(archive.get("tname").toString());     //视频类型名称
        video.setBiliBili_copyright(Integer.valueOf(archive.get("copyright").toString()));
        video.setLogo(archive.get("pic").toString());
        video.setTitle(archive.get("title").toString());
        video.setPubdate(new Date(Long.parseLong(archive.get("pubdate").toString())));
        video.setCtime(new Date(Long.parseLong(archive.get("ctime").toString())));
        video.setDesc(archive.get("desc").toString());
        video.setState(Integer.valueOf(archive.get("state").toString()));
        video.setAttribute(Long.valueOf(archive.get("attribute").toString()));
        video.setDuration(Integer.valueOf(archive.get("duration").toString()));
        video.setRights(archive.get("rights").toString());
        Map<String, Object> owner = (Map<String, Object>) archive.get("owner");
        video.setBiliBili_mid(Long.valueOf(owner.get("mid").toString()));
        author.setBiliBili_mid(Long.valueOf(owner.get("mid").toString()));
        author.setName(owner.get("name").toString());
        author.setLogo(owner.get("face").toString());
        Map<String, Object> stat = (Map<String, Object>) archive.get("stat");
        video.setViews(Long.valueOf(stat.get("view").toString()));
        video.setMasks(Long.valueOf(stat.get("danmaku").toString()));
        video.setReplay(Long.valueOf(stat.get("reply").toString()));
        video.setFavorite(Long.valueOf(stat.get("favorite").toString()));
        video.setCoin(Long.valueOf(stat.get("coin").toString()));
        video.setShare(Long.valueOf(stat.get("share").toString()));
        video.setNow_rank(Integer.valueOf(stat.get("now_rank").toString()));
        video.setHis_rank(Integer.valueOf(stat.get("his_rank").toString()));
        video.setLike(Long.valueOf(stat.get("like").toString()));
        video.setDynamic(archive.get("dynamic").toString());
        boolean typeExsist=dao.isExistVideoType(type.getBiliBili_rid());
        if(!typeExsist){
            dao.insertVideoType(type);
        }
        boolean authorExsist=dao.isExistAuthor(author.getBiliBili_mid());
        if(!authorExsist){
            dao.insertAuthor(author);
        }else {
            dao.updateAuthor(author);
        }
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

}
