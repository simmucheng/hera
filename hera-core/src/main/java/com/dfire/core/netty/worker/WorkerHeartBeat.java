package com.dfire.core.netty.worker;

import com.dfire.core.lock.DistributeLock;
import com.dfire.core.message.Protocol.HeartBeatMessage;
import com.dfire.core.message.Protocol.Operate;
import com.dfire.core.message.Protocol.Request;
import com.dfire.core.message.Protocol.SocketMessage;
import com.dfire.core.netty.util.AtomicIncrease;
import io.netty.channel.ChannelFuture;

/**
 * @author xiaosuda
 * @date 2018/4/12
 */
public class WorkerHeartBeat {


    public ChannelFuture send(WorkContext context) {
        HeartBeatMessage hbm = HeartBeatMessage.newBuilder().
                setHost(DistributeLock.host).
                build();
        Request request = Request.newBuilder().
                        setRid(AtomicIncrease.getAndIncrement()).
                        setOperate(Operate.HeartBeat).
                        setBody(hbm.toByteString()).
                        build();
        SocketMessage message = SocketMessage.newBuilder().
                setKind(SocketMessage.Kind.REQUEST).
                setBody(request.toByteString()).
                build();
        return context.getServerChannel().write(message);
    }
}
