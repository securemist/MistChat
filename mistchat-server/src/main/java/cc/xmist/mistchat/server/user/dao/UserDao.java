package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.mapper.UserMapper;
import cc.xmist.mistchat.server.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024-01-11
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> implements IUserService {

}
