package com.zt.yavon.module.data;

/**
 * Created by lifujun on 2018/7/27.
 */

public class MsgBean {
    private boolean isSelect;
    private String id;
    private String title;
    private String content;
    private boolean is_read;
    private String time;
    private String flag;
    private String status;
    private String new_at;
    private String new_count;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNew_at() {
        return new_at;
    }

    public void setNew_at(String new_at) {
        this.new_at = new_at;
    }

    public String getNew_count() {
        return new_count;
    }

    public void setNew_count(String new_count) {
        this.new_count = new_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MsgBean bean = (MsgBean) o;

        if (isSelect != bean.isSelect) return false;
        if (is_read != bean.is_read) return false;
        if (!id.equals(bean.id)) return false;
        if (!title.equals(bean.title)) return false;
        if (!content.equals(bean.content)) return false;
        if (!time.equals(bean.time)) return false;
        if (!flag.equals(bean.flag)) return false;
        if (!status.equals(bean.status)) return false;
        if (!new_at.equals(bean.new_at)) return false;
        if (!new_count.equals(bean.new_count)) return false;
        return type.equals(bean.type);
    }

    @Override
    public int hashCode() {
        int result = (isSelect ? 1 : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + (is_read ? 1 : 0);
        result = 31 * result + time.hashCode();
        result = 31 * result + flag.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + new_at.hashCode();
        result = 31 * result + new_count.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
