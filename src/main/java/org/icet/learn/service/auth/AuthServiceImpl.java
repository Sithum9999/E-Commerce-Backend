package org.icet.learn.service.auth;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.SignupRequest;
import org.icet.learn.dto.User;
import org.icet.learn.entity.OrderEntity;
import org.icet.learn.entity.UserEntity;
import org.icet.learn.enums.OrderStatus;
import org.icet.learn.enums.UserRole;
import org.icet.learn.repository.OrderDao;
import org.icet.learn.repository.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{


    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderDao orderDao;

    @Override
    public User createUser(SignupRequest signupRequest) {
        UserEntity user = new UserEntity();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        UserEntity createdUser = userDao.save(user);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAmount(0L);
        orderEntity.setTotalAmount(0L);
        orderEntity.setDiscount(0L);
        orderEntity.setUser(createdUser);
        orderEntity.setOrderStatus(OrderStatus.Pending);
        orderDao.save(orderEntity);


        User userDto = new User();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getRole());

        return userDto;
    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userDao.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    @Override
    public void createAdminAccount(){
        UserEntity adminAccount = userDao.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            UserEntity user = new UserEntity();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userDao.save(user);
        }
    }

}
