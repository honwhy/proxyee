package cc.xpcas.nettysocks.handler;

//import cc.xpcas.nettysocks.utils.NettyUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xp
 */
public class ChannelHandlerContextForwardingHandler extends ChannelForwardingHandler {

    private final ChannelHandlerContext destinationChannelHandlerContext;

    public ChannelHandlerContextForwardingHandler(ChannelHandlerContext destinationChannelHandlerContext, boolean isReadLocalWriteRemote) {
        super(destinationChannelHandlerContext.channel(), isReadLocalWriteRemote);
        this.destinationChannelHandlerContext = destinationChannelHandlerContext;
    }

    @Override
    protected ChannelFuture doWriteAndFlush(Object msg) {
        return destinationChannelHandlerContext.writeAndFlush(msg);
    }

    @Override
    protected void closeAfterFlush() {
        //NettyUtils.closeAfterFlush(destinationChannelHandlerContext);
        if (destinationChannelHandlerContext.channel().isActive()) {
            destinationChannelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}