package netty.basic;

// Netty源码解析: 构建在NIO程序之上，底层完全对应NIO的基本操作
public class NettySourceCode {

    // 1. 一主多从(Reactor模式)，里面包含NIO中的Selector
    //    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //    EventLoopGroup workerGroup = new NioEventLoopGroup(8);
    //
    //    NioEventLoop.java
    //    private NioEventLoop.SelectorTuple openSelector() {
    //       final AbstractSelector unwrappedSelector;
    //       try {
    //           TODO: 相当于NIO中的Selector.open();方法，创建多路复用器
    //           unwrappedSelector = this.provider.openSelector();
    //       } catch (IOException var7) {
    //           throw new ChannelException("failed to open a new selector", var7);
    //       }
    //    }

    // 2. bootstrap.bind()
    //    AbstractBootstrap.java
    //    private ChannelFuture doBind(final SocketAddress localAddress) {
    //        TODO: 建立起ServerSocketChannel并配置成非阻塞
    //        final ChannelFuture regFuture = this.initAndRegister();
    //        final Channel channel = regFuture.channel();
    //        if (regFuture.cause() != null) {
    //            return regFuture;
    //        } else if (regFuture.isDone()) {
    //            ChannelPromise promise = channel.newPromise();
    //            doBind0(regFuture, channel, localAddress, promise);
    //            return promise;
    //        } else {
    //            final AbstractBootstrap.PendingRegistrationPromise promise =
    //               new AbstractBootstrap.PendingRegistrationPromise(channel);
    //            regFuture.addListener(new ChannelFutureListener() {
    //                public void operationComplete(ChannelFuture future) throws Exception {
    //                    Throwable cause = future.cause();
    //                    if (cause != null) {
    //                        promise.setFailure(cause);
    //                    } else {
    //                        promise.registered();
    //                        AbstractBootstrap.doBind0(regFuture, channel, localAddress, promise);
    //                    }
    //
    //                }
    //            });
    //            return promise;
    //        }
    //    }

    //   final ChannelFuture initAndRegister() {
    //      Channel channel = null;
    //      try {
    //          channel = this.channelFactory.newChannel();
    //          this.init(channel);
    //      } catch (Throwable var3) {
    //          if (channel != null) {
    //              channel.unsafe().closeForcibly();
    //              return (new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE)).setFailure(var3);
    //          }
    //          return (new DefaultChannelPromise(new FailedChannel(), GlobalEventExecutor.INSTANCE)).setFailure(var3);
    //      }
    //      ChannelFuture regFuture = this.config().group().register(channel); 调用到下面的doRegister()方法
    //      if (regFuture.cause() != null) {
    //          if (channel.isRegistered()) {
    //              channel.close();
    //          } else {
    //              channel.unsafe().closeForcibly();
    //          }
    //      }
    //      return regFuture;
    //  }

    //   AbstractChannel.java
    //   public final void register(EventLoop eventLoop, final ChannelPromise promise) {
    //        ObjectUtil.checkNotNull(eventLoop, "eventLoop");
    //        if (AbstractChannel.this.isRegistered()) {
    //            promise.setFailure(new IllegalStateException("registered to an event loop already"));
    //        } else if (!AbstractChannel.this.isCompatible(eventLoop)) {
    //            promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
    //        } else {
    //            AbstractChannel.this.eventLoop = eventLoop;
    //            if (eventLoop.inEventLoop()) {
    //                this.register0(promise);
    //            } else {
    //                try {
    //                    ==> SingleThreadEventExecutor.java ==> startThread() ==> doStartThread()
    //                    ==> SingleThreadEventExecutor.this.run(); ==> NioEventLoop.java ==> run()
    //                    eventLoop.execute(new Runnable() {
    //                        public void run() {
    //                            AbstractUnsafe.this.register0(promise);
    //                        }
    //                    });
    //                } catch (Throwable var4) {
    //                }
    //            }
    //        }
    //    }

    //    NioEventLoop.java ==> run() {
    //       strategy = this.select(curDeadlineNanos);
    //       ...
    //       this.processSelectedKeys();
    //    }
    //    TODO: 类似于NIO selectedKeys = this.selector.selectedKeys();操作
    //    private void processSelectedKeys() {
    //        if (this.selectedKeys != null) {
    //            this.processSelectedKeysOptimized();
    //        } else {
    //            this.processSelectedKeysPlain(this.selector.selectedKeys());
    //        }
    //    }

    //    AbstractNioMessageChannel.java > NioMessageUnsafe.read() > ChannelPipeline.fireChannelRead(Object msg)
    //    for(int i = 0; i < localRead; ++i) {
    //        AbstractNioMessageChannel.this.readPending = false;
    //        pipeline.fireChannelRead(this.readBuf.get(i));
    //    }

    //    AbstractChannelHandlerContext.java
    //    static void invokeChannelRead(final AbstractChannelHandlerContext next, Object msg) {
    //        final Object m = next.pipeline.touch(ObjectUtil.checkNotNull(msg, "msg"), next);
    //        EventExecutor executor = next.executor();
    //        if (executor.inEventLoop()) {
    //            next.invokeChannelRead(m);
    //        } else {
    //            executor.execute(new Runnable() {
    //                public void run() {
    //                    next.invokeChannelRead(m);
    //                }
    //            });
    //        }
    //    }
    //    private void invokeChannelRead(Object msg) {
    //        if (this.invokeHandler()) {
    //            try {
    //                TODO: 源码中将封装好的数据信息传递到自定义实现的方法参数中
    //                      自定义的handler需要继承ChannelInboundHandler接口或者实现它的子类型 !!
    //                ((ChannelInboundHandler)this.handler()).channelRead(this, msg);
    //            } catch (Throwable var3) {
    //                this.invokeExceptionCaught(var3);
    //            }
    //        } else {
    //            this.fireChannelRead(msg);
    //        }
    //    }

    //   上面的run()方法真正调用到下面的select方法
    //   NioEventLoop.java
    //   private int select(long deadlineNanos) throws IOException {
    //        TODO: 等效于调用NIO selector.select();操作
    //        if (deadlineNanos == 9223372036854775807L) {
    //            return this.selector.select();
    //        } else {
    //            long timeoutMillis = deadlineToDelayNanos(deadlineNanos + 995000L) / 1000000L;
    //            return timeoutMillis <= 0L ? this.selector.selectNow() : this.selector.select(timeoutMillis);
    //        }
    //    }

    //    AbstractNioChannel.java
    //    protected void doRegister() throws Exception {
    //        boolean selected = false;
    //        while(true) {
    //            try {
    //                TODO: 类似于serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); 注册SocketChannel
    //                this.selectionKey = this.javaChannel().register(this.eventLoop().unwrappedSelector(), 0, this);
    //                return;
    //            } catch (CancelledKeyException var3) {
    //                if (selected) {
    //                    throw var3;
    //                }
    //                this.eventLoop().selectNow();
    //                selected = true;
    //            }
    //        }
    //    }
}
