package com.zr.blog.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title;       // 标题
    private String content;     // 内容
    private String firstPicture;// 首图
    private String flag;        // 标记
    private Integer views;       // 访问量
    private boolean appreciation; // 是否开启赞赏
    private boolean shareStatement; // 转载声明是否开启
    private boolean commentabled;   // 是否允许评论
    private boolean published;      // 是否发布
    private boolean recommend;      // 是否推荐
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

}
