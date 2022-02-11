package com.github.monkeywie.proxyee.codec;

import cc.xpcas.nettysocks.handler.Socks5CommandRequestHandler;
import cc.xpcas.nettysocks.handler.Socks5InitialRequestHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5ServerEncoder;

import java.util.List;
import java.util.Objects;

public class StrategyCodecHandler extends ByteToMessageDecoder {
    byte socks5 = 0x05;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.retain();
        byte b = byteBuf.getByte(0);
        if (!Objects.equals(socks5, b)) {
            channelHandlerContext.channel().pipeline().remove(Socks5ServerEncoder.DEFAULT);
            channelHandlerContext.channel().pipeline().remove(Socks5InitialRequestDecoder.class.getName());
            channelHandlerContext.channel().pipeline().remove(Socks5InitialRequestHandler.class.getName());
            channelHandlerContext.channel().pipeline().remove(Socks5CommandRequestDecoder.class.getName());
            channelHandlerContext.channel().pipeline().remove(Socks5CommandRequestHandler.class.getName());
        } else {
            channelHandlerContext.channel().pipeline().remove("httpCodec");
            channelHandlerContext.channel().pipeline().remove("serverHandle");
        }
        channelHandlerContext.channel().pipeline().remove(this);
        list.add(byteBuf);
    }
}
