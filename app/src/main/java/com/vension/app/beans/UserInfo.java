package com.vension.app.beans;

/**
 * @author 王元_Trump
 * @desc ${DESC}
 * @time 2016/10/7 11:11
 */
public class UserInfo {

    /**
     * uid	int	用户id
     * cid	int	公司id
     * did	int	部门id
     * pic	varchar	头像地址
     * realname	varchar	真实姓名
     * dname	varchar	部门
     * pname	varchar	岗位
     * sex	int	性别
     * birth	date	生日
     * mobile	varchar	手机号
     * landline	varchar	座机
     * email	varchar	邮箱
     */

    private String uid;
    private String cid;
    private String did;
    private String imtoken;
    private String realname;
    private String pic;
    private String sex;
    private String birth;
    private String mobile;
    private String landline;
    private String email;
    private String dname;
    private String pname;
    private String starttime;
    private String endtime;
    private String entrytime;
    private String xzcxpwd;
    private String fulidou;
    private int isAdmin;
    private int user_type;

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getFulidou() {
        return fulidou;
    }

    public void setFulidou(String fulidou) {
        this.fulidou = fulidou;
    }

    public String getXzcxpwd() {
        return xzcxpwd;
    }

    public void setXzcxpwd(String xzcxpwd) {
        this.xzcxpwd = xzcxpwd;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(String entrytime) {
        this.entrytime = entrytime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
