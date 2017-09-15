package org.clever.common.utils.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Tree节点数据
 *
 * @author LiZhiWei
 * @version 2015年6月4日 下午9:51:39
 */
@JsonInclude(Include.NON_NULL)
@Data
public class SimpleTreeNode implements ITreeNode {
    private static final long serialVersionUID = 1L;
    /**
     * 节点标识
     */
    private Long id;
    /**
     * 父级编号
     */
    private Long parentId;
    /**
     * 树节点全路径
     */
    private String fullPath = "-";
    /**
     * 是否被添加到父节点下
     */
    private boolean isBuild = false;
    /**
     * 显示节点文本
     */
    private String text;
    /**
     * 节点图标
     */
    private String iconCls;
    /**
     * 是否勾选状态
     */
    private boolean checked;
    /**
     * 节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点
     */
    private String state = "open";
    /**
     * 子节点
     */
    private List<ITreeNode> children;

    /**
     * 绑定到节点的对象
     */
    private Serializable attributes;

    public SimpleTreeNode() {

    }

    /**
     * @param parentId 父级编号
     * @param id       节点标识
     * @param text     显示节点文本
     */
    public SimpleTreeNode(Long parentId, Long id, String fullPath, String text) {
        this.parentId = parentId;
        this.id = id;
        this.fullPath = fullPath;
        this.text = text;
    }

    /**
     * @param parentId 父级编号
     * @param id       节点标识
     * @param fullPath 节点路径
     * @param text     显示节点文本
     * @param iconCls  节点图标
     * @param checked  是否勾选状态
     * @param state    节点状态 'open' 或 'closed'
     */
    public SimpleTreeNode(Long parentId, Long id, String fullPath, String text, String iconCls, boolean checked, String state) {
        this.parentId = parentId;
        this.id = id;
        this.fullPath = fullPath;
        this.text = text;
        this.iconCls = iconCls;
        this.checked = checked;
        this.state = state;
    }

    /**
     * @param parentId   父级编号
     * @param id         节点标识
     * @param fullPath   节点路径
     * @param text       显示节点文本
     * @param iconCls    节点图标
     * @param checked    是否勾选状态
     * @param state      节点状态 'open' 或 'closed'
     * @param children   子节点
     * @param attributes 被添加到节点的自定义属性
     */
    public SimpleTreeNode(Long parentId, Long id, String fullPath, String text, String iconCls, boolean checked, String state, Set<SimpleTreeNode> children, Serializable attributes) {
        this.parentId = parentId;
        this.id = id;
        this.fullPath = fullPath;
        this.text = text;
        this.iconCls = iconCls;
        this.checked = checked;
        this.state = state;
        this.attributes = attributes;
        this.addChildren(children);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public boolean isBuild() {
        return isBuild;
    }

    @Override
    public void setBuild(boolean isBuild) {
        this.isBuild = isBuild;
    }

    @Override
    public List<ITreeNode> getChildren() {
        return children;
    }

    @Override
    @Deprecated
    public void addChildren(ITreeNode node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }

    /**
     * 增加子节点<br>
     */
    public SimpleTreeNode addChildren(SimpleTreeNode node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
        return this;
    }

    /**
     * 增加子节点集合<br>
     */
    public SimpleTreeNode addChildren(Collection<SimpleTreeNode> nodes) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.addAll(nodes);
        return this;
    }
}