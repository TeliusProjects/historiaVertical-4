package com.example.admin.prova;

import java.io.Serializable;

/**
 * Created by admin on 04/05/2017.
 */
class LogIn implements Serializable {
        private transient String username;
        private transient String Password;
        private transient String  encoded_profileImageBitmap;
        private transient String profileImageName;
        public LogIn(String username, String Password)
        {
            this.username = username;
            this.Password=Password;
        }

        public String getPassword() {
            return Password;
        }

        public String getUsername() {
            return username;
        }

        public String getEncoded_profileImageBitmap() {
            return encoded_profileImageBitmap;
        }

        public void setEncoded_profileImageBitmap(String encoded_profileImageBitmap) {
            this.encoded_profileImageBitmap = encoded_profileImageBitmap;
        }

        public String getProfileImageName() {
            return profileImageName;
        }

        public void setProfileImageName(String profileImageName) {
            this.profileImageName = profileImageName;
        }
    }

