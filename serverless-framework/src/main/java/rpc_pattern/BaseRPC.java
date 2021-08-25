package rpc_pattern;

// RPC是一种编程模型而非一种规范或协议
// 作为架构微服务化的基础组件，降低了架构微服务化的成本，提高调用方与服务提供方的研发效率，
// 屏蔽跨进程调用函数(服务)的复杂细节，让调用方感觉就像调用本地函数一样调用远端函数

// 成熟RPC框架：
// 1. gRPC：Google开源，具有平台无关性，基于http/2协议，支持服务追踪、负载均衡、健康检查等功能；
//          框架是基于HTTP协议实现的，底层使用到了Netty框架的支持
// 2. dubbo：阿里开源的一款高性能RPC框架
public class BaseRPC {
}