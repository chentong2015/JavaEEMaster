// Nginx 开源HTTP服务器
// 1.使用异步架构来处理请求，常用在可扩展性比较高的场景中
// 2.可以方便的使用反向代理和IMAP/POP3代理服务器
public class BaseNginx {

    // 如何把Nginx配置成反向代理
    // 修改/etc/nginx/sites-available/default JSON文件
    // server {
    //    listen: 80;
    //    location / {
    //           proxy_pass http://localhost:5000;  可以修改改属性的值来指定Kestrel服务器的位置
    //           proxy_http_version 1.1;
    //           proxy_set_header Upgrade $http_upgrade;
    //           proxy_set_header Connection heep-alive;
    //           proxy_set_header Host $host;
    //           proxy_cache_bypass $http_upgrade;
    //    }
    // }
}
