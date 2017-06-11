package com.example.admin.prova;

/**
 * Created by admin on 04/05/2017.
 */
    public class signUp {
        private String username;
        private String password;
        private String password2;
        private String email;
        private String email2;


        public signUp(String username, String password, String password2, String email, String email2)
        {
            this.username   = username;
            this.password   = password;
            this.password2  = password2;
            this.email      = email;
            this.email2     = email2;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword(){
            return password;
        }
        public String getPassword2(){

            return password2;
        }
        public String getEmail (){

            return email;
        }
        public String getEmail2() {

            return email2;
        }
    }

