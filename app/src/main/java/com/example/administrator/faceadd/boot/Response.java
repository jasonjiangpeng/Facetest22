package com.example.administrator.faceadd.boot;

/**
 * Created by Administrator on 2017/10/17.
 */

public class Response {
        private byte[] content;
        private int status;

        public Response() {
        }
        public Response(byte[] content, int status) {
            this.content = content;
            this.status = status;
        }
        public byte[] getContent() {
            return this.content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

}
