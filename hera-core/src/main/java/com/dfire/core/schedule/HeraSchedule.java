package com.dfire.core.schedule;

import com.dfire.core.netty.master.MasterContext;
import com.dfire.logs.HeraLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 14:04 2018/1/12
 * @desc
 */
@Component
public class HeraSchedule {

    private AtomicBoolean running = new AtomicBoolean(false);

    @Autowired
    private MasterContext masterContext;


    public void startup() {
        //通过原子操作来初始化mastercontext
        if (!running.compareAndSet(false, true)) {
            return;
        }
        HeraLog.info("begin to start master context");
        masterContext.init();
    }
    //如果该节点不是master，那么就销毁对应的master资源
    public void shutdown() {
        if (running.compareAndSet(true, false)) {
            masterContext.destroy();
        }
    }

}
