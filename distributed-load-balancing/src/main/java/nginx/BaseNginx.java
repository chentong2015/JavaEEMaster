package nginx;

// Nginx开源HTTP服务器
// 1.使用异步架构来处理请求，常用在可扩展性比较高的场景中
// 2.可以方便的使用反向代理和IMAP/POP3代理服务器
public class BaseNginx {

    // TODO: 传统负载均衡策略的弊端: 需要手动配置负载均衡中的服务，添加和删除
    // 1. Nginx作为负载均衡设备
    //    使用虚拟机上nginx的ip地址来进行访问 192.168.0.60
    //    > vim conf/nginx.conf 配置配置文件, 配置负载均衡的策略
    //      http {
    //         ...
    //         upstream redislock {
    //            basic 192.168.0.126:8080 weight=1; 配置每个server(机器IP地址+端口号)的负载权重
    //            basic 192.168.0.126:8090 weight=1; 动态转发到指定的地址
    //         }
    //         basic {
    //            listen      80;
    //            server_name localhost;
    //            location / {
    //               root   html;
    //               index  index.html index.htm
    //               proxy_pass http://redislock; 配置这里的反向代理，代理到上面upstream redislock
    //            }
    //        }
    //      }

    // 2. 如何把Nginx配置成反向代理
    //    修改/etc/nginx/sites-available/default JSON文件
    //    basic {
    //       listen: 80;
    //       location / {
    //              proxy_pass http://localhost:5000;  可以修改改属性的值来指定Kestrel服务器的位置
    //              proxy_http_version 1.1;
    //              proxy_set_header Upgrade $http_upgrade;
    //              proxy_set_header Connection heep-alive;
    //              proxy_set_header Host $host;
    //              proxy_cache_bypass $http_upgrade;
    //       }
    //    }
}
