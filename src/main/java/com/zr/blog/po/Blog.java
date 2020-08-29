package com.zr.blog.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

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
    @Lob
    @Basic(fetch = LAZY)
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

    @NotBlank(message = "tagsId can not be null or whitespace only")
    @Transient // 被这个注解声明的字段，不会被当成数据库字段，也不会去更新数据表
    private String tagsId;          // 以字符串形式传回的Tag Id 比如： "1,2,3"

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

}
