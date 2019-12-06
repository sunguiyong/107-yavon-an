package com.zt.igreen.module.data;

public class BindTuyaHome {
    @Override
    public String toString() {
        return "BindTuyaHome{" +
                "data=" + data +
                '}';
    }

    /**
     * data : {"nick_name":"小雅","account":"1630","email":"","mobile":"18217088625","avatar":"http://47.99.88.127/storage/upload/avatar/201911/15733675518130.png","api_token":"e6daca7df7f74a8695b81e23c38ed6d6","open_auto_update":false,"open_system_message":true,"ty_family_id":"7818303"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nick_name : 小雅
         * account : 1630
         * email :
         * mobile : 18217088625
         * avatar : http://47.99.88.127/storage/upload/avatar/201911/15733675518130.png
         * api_token : e6daca7df7f74a8695b81e23c38ed6d6
         * open_auto_update : false
         * open_system_message : true
         * ty_family_id : 7818303
         */

        private String nick_name;
        private String account;
        private String email;
        private String mobile;
        private String avatar;
        private String api_token;
        private boolean open_auto_update;
        private boolean open_system_message;
        private String ty_family_id;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getApi_token() {
            return api_token;
        }

        public void setApi_token(String api_token) {
            this.api_token = api_token;
        }

        public boolean isOpen_auto_update() {
            return open_auto_update;
        }

        public void setOpen_auto_update(boolean open_auto_update) {
            this.open_auto_update = open_auto_update;
        }

        public boolean isOpen_system_message() {
            return open_system_message;
        }

        public void setOpen_system_message(boolean open_system_message) {
            this.open_system_message = open_system_message;
        }

        public String getTy_family_id() {
            return ty_family_id;
        }

        public void setTy_family_id(String ty_family_id) {
            this.ty_family_id = ty_family_id;
        }
    }
}
