package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.util.JsonUtil;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.IpDetail;
import cc.xmist.mistchat.server.user.entity.IpInfo;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class IpService {
    @Resource
    private UserDao userDao;

    private ExecutorService executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("ip-update", false));

    @SneakyThrows
    public void updateIpInfo(Long uid, String clientIp) {
        executor.execute(() -> {
            IpInfo old = userDao.getUser(uid).getIpInfo();
            String ip = clientIp;
            if (ip.equals("localhost")) ip = "127.0.0.1";

            IpDetail ipDetail = null;
            // 当前ip与数据库中最新ip一致，不需要更新信息
            if (old != null && ip.equals(old.getLastIp())) {
                return;
            }

            ipDetail = getIpDetailOrNull(ip);
            if (ipDetail == null) {
                log.info("用户{} 获取ip归属地失败, ip:{}", uid, ip);
                return;
            }

            IpInfo ipInfo;
            if (old == null) {
                ipInfo = new IpInfo();
                ipInfo.setInitialIp(ip);
                ipInfo.setInitialIpDetail(ipDetail);
            } else {
                ipInfo = old;
            }
            ipInfo.setLastIp(ip);
            ipInfo.setLastIpDetail(ipDetail);

            userDao.updateIpInfo(uid, ipInfo);
        });
    }

    @SneakyThrows
    public IpDetail getIpDetailOrNull(String ip) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", ip);
        paramMap.put("accessKey", "alibaba-inc");
        IpDetail ipDetail = null;
        for (int i = 0; i < 3; i++) {
            String data = HttpUtil.get("https://ip.taobao.com/outGetIpInfo", paramMap);
            Result result = JsonUtil.toObj(data, Result.class);
            if (result.code.equals(CodeType.SUCCESS.key)) {
                ipDetail = result.getIpDetail();
                break;
            }
            Thread.sleep(2000);
        }
        return ipDetail;
    }

    @Data
    @Getter
    static class Result {
        private Integer code;
        private String msg;
        @SerializedName("data")
        private IpDetail ipDetail;
    }

    @AllArgsConstructor
    static enum CodeType {
        SUCCESS(0),
        ERROR(2);
        public Integer key;
    }

}
